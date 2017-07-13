package tacit

import fastparse.all._

object LineParser {
  case class Error(index: Int, length: Int, message: String)

  sealed trait Term
  case class TermLiteral(index: Int, text: String) extends Term {
    val value = text.toInt
  }
  case class TermPlus(index: Int, text: String) extends Term {
    def operation(x: Int, y: Int) = x + y
  }

  sealed trait UnknownTerm
  case class ValidTerm(term: Term) extends UnknownTerm
  case class InvalidTermLiteralWithSuffix(index: Int, text: String) extends UnknownTerm
  case class InvalidTermOther(index: Int, text: String) extends UnknownTerm

  val whitespace: P[Unit] = P(
    CharIn(" ").rep(1)
      .map(_ => ()))

  def text[T](p: P[Unit], f: (Int, String) => T) =
    (Index ~ p.!) map { case (index, text) => f(index, text) }

  val termEnd: P[Unit] = P(
    CharIn(" ") | End)

  val notTermEnd: P[Unit] = P(
    !(termEnd) ~ AnyChar)

  def validTerm(p: P[Term]): P[ValidTerm] =
    (p ~ &(termEnd)).map(ValidTerm)

  val number = P(text(
    CharIn('0' to '9').rep(1),
    TermLiteral))

  val invalidNumber = P(text(
    CharIn('0' to '9').rep(1) ~ notTermEnd.rep(1),
    InvalidTermLiteralWithSuffix))

  val invalidOther = P(text(
    notTermEnd.rep(1),
    InvalidTermOther))

  val operator = P(text(
    CharIn("+"),
    TermPlus))

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

  def checkExpression(result: Parsed[Seq[UnknownTerm]]): Either[Seq[Error], Seq[Term]] =
    result.fold(
      (failedParser, index, _) => Left(Seq(
        Error(index, 1, s"Failed to parse $failedParser"))),
      (unknownExpression, _) => {
        def errors = unknownExpression collect {
          case InvalidTermLiteralWithSuffix(index, text) =>
            Error(index, text.length, s"Invalid suffix on number")
          case InvalidTermOther(index, text) =>
            Error(index, text.length, s"Invalid term")
        }

        if (errors.nonEmpty) {
          Left(errors)
        } else {
          Right(unknownExpression collect {
            case ValidTerm(term) => term
          })
        }
      })

  def parse(in: String) = checkExpression(expression.parse(in))
}
