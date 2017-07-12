package tacit

import fastparse.all._

object SourceParser {
  case class Error(index: Int, length: Int, message: String)

  sealed trait SourceTerm
  case class SourceTermLiteral(index: Int, text: String) extends SourceTerm {
    val value = text.toInt
  }
  case class SourceTermPlus(index: Int, text: String) extends SourceTerm {
    def operation(x: Int, y: Int) = x + y
  }

  sealed trait UnknownSourceTerm
  case class ValidSourceTerm(term: SourceTerm) extends UnknownSourceTerm
  case class InvalidSourceTermLiteralWithSuffix(index: Int, text: String) extends UnknownSourceTerm
  case class InvalidSourceTermOther(index: Int, text: String) extends UnknownSourceTerm

  val whitespace: P[Unit] = P(
    CharIn(" ").rep(1)
      .map(_ => ()))

  def text[T](p: P[Unit], f: (Int, String) => T) =
    (Index ~ p.!) map { case (index, text) => f(index, text) }

  val termEnd: P[Unit] = P(
    CharIn(" ") | End)

  val notTermEnd: P[Unit] = P(
    !(termEnd) ~ AnyChar)

  def validTerm(p: P[SourceTerm]): P[ValidSourceTerm] =
    (p ~ &(termEnd)).map(ValidSourceTerm)

  val number = P(text(
    CharIn('0' to '9').rep(1),
    SourceTermLiteral))

  val invalidNumber = P(text(
    CharIn('0' to '9').rep(1) ~ notTermEnd.rep(1),
    InvalidSourceTermLiteralWithSuffix))

  val invalidOther = P(text(
    notTermEnd.rep(1),
    InvalidSourceTermOther))

  val operator = P(text(
    CharIn("+"),
    SourceTermPlus))

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

  def checkExpression(result: Parsed[Seq[UnknownSourceTerm]]): Either[Seq[Error], Seq[SourceTerm]] =
    result.fold(
      (failedParser, index, _) => Left(Seq(
        Error(index, 1, s"Failed to parse $failedParser"))),
      (unknownExpression, _) => {
        def errors = unknownExpression collect {
          case InvalidSourceTermLiteralWithSuffix(index, text) =>
            Error(index, text.length, s"Invalid suffix on number")
          case InvalidSourceTermOther(index, text) =>
            Error(index, text.length, s"Invalid term")
        }

        if (errors.nonEmpty) {
          Left(errors)
        } else {
          Right(unknownExpression collect {
            case ValidSourceTerm(term) => term
          })
        }
      })

  def parse(in: String) = checkExpression(expression.parse(in))
}
