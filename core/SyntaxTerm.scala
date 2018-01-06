package tacit.core

sealed trait SyntaxTerm {
  def sourceLocation: SourceLocation
}

object SyntaxTerm {
  final case class Literal(
    value: Int,
    sourceLocation: SourceLocation
  ) extends SyntaxTerm

  final case class Operator(
    arithmetic: Arithmetic,
    sourceLocation: SourceLocation
  ) extends SyntaxTerm

  sealed trait Unknown {}

  final case class Valid(
    term: SyntaxTerm
  ) extends Unknown

  sealed trait Invalid extends Unknown {
    def sourceLocation: SourceLocation
  }

  final case class InvalidLiteralSuffix(
    sourceLocation: SourceLocation
  ) extends Invalid

  final case class InvalidOther(
    sourceLocation: SourceLocation
  ) extends Invalid

  final case class ParseError(
    message: String,
    sourceLocation: SourceLocation
  ) extends Invalid
}
