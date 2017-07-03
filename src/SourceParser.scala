package tacit

import scala.util.parsing.combinator.{RegexParsers, PackratParsers}

sealed trait SourceTerm
case class SourceTermLiteral(value: Int) extends SourceTerm
case class SourceTermPlus() extends SourceTerm

object SourceParser extends RegexParsers with PackratParsers {
  override def skipWhitespace = false

  lazy val whitespace: PackratParser[Unit] =
    raw"[ ]+".r ^^^ { () }

  lazy val number: PackratParser[SourceTermLiteral] =
    raw"\d+".r ^^ { text => SourceTermLiteral(text.toInt) }

  lazy val operator: PackratParser[SourceTermPlus] =
    literal("+") ^^^ SourceTermPlus()

  lazy val term: PackratParser[SourceTerm] =
    number | operator

  lazy val expression: PackratParser[List[SourceTerm]] =
    rep1sep(term, whitespace)

  def parse(in: String) = parseAll(expression, in)
}
