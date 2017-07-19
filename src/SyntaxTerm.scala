package tacit

sealed trait SyntaxTerm

object SyntaxTerm {
  case class Literal(index: Int, text: String) extends SyntaxTerm {
    val value = text.toInt
  }
  case class Plus(index: Int, text: String) extends SyntaxTerm {
    def operation(x: Int, y: Int) = x + y
  }

  sealed trait Unknown
  case class Valid(term: SyntaxTerm) extends Unknown
  case class InvalidLiteralWithSuffix(index: Int, text: String) extends Unknown
  case class InvalidOther(index: Int, text: String) extends Unknown
}
