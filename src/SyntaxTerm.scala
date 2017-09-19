package tacit

sealed trait SyntaxTerm

object SyntaxTerm {
  final case class Literal(index: Int, text: String) extends SyntaxTerm {
    val value = text.toInt
  }
  final case class Plus(index: Int, text: String) extends SyntaxTerm {
    def operation(x: Int, y: Int) = x + y
  }

  sealed trait Unknown
  final case class Valid(term: SyntaxTerm) extends Unknown
  final case class InvalidLiteralSuffix(index: Int, text: String) extends Unknown
  final case class InvalidOther(index: Int, text: String) extends Unknown
}
