package tacit

import fastparse.all._

object LineParser {
  val whitespace: P[Unit] = P(
    CharIn(" ").rep(1)
      .map(_ => ()))

  def text[T](p: P[Unit], f: (Int, String) => T) =
    (Index ~ p.!) map { case (index, text) => f(index, text) }

  val termEnd: P[Unit] = P(
    CharIn(" ") | End)

  val notTermEnd: P[Unit] = P(
    !(termEnd) ~ AnyChar)

  def validTerm(p: P[SyntaxTerm]): P[SyntaxTerm.Valid] =
    (p ~ &(termEnd)).map(SyntaxTerm.Valid)

  val number = P(text(
    CharIn('0' to '9').rep(1),
    SyntaxTerm.Literal))

  val invalidNumber = P(
    CharIn('0' to '9').rep(1)
    ~ text(
      notTermEnd.rep(1),
      SyntaxTerm.InvalidLiteralSuffix))

  val invalidOther = P(text(
    notTermEnd.rep(1),
    SyntaxTerm.InvalidOther))

  val operator = P(text(
    CharIn("+"),
    SyntaxTerm.Plus))

  val unknownTerm = P(
    validTerm(number)
    | validTerm(operator)
    | invalidNumber
    | invalidOther)

  val expression = P(
    Start
    ~ whitespace.?
    ~ unknownTerm.rep(0, whitespace)
    ~ whitespace.?
    ~ End)

  def checkExpression(result: Parsed[Seq[SyntaxTerm.Unknown]]): Either[Seq[GuestError], Seq[SyntaxTerm]] =
    result.fold(
      (failedParser, index, _) => Left(Seq(
        GuestError(index, 1, s"Failed to parse $failedParser"))),
      (unknownExpression, _) => {
        def errors = unknownExpression collect {
          case SyntaxTerm.InvalidLiteralSuffix(index, text) =>
            GuestError(index, text.length, "This number suffix is not valid")
          case SyntaxTerm.InvalidOther(index, text) =>
            GuestError(index, text.length, "This name is not in scope")
        }

        if (errors.nonEmpty) {
          Left(errors)
        } else {
          Right(unknownExpression collect {
            case SyntaxTerm.Valid(term) => term
          })
        }
      })

  def parse(in: String) = checkExpression(expression.parse(in))
}
