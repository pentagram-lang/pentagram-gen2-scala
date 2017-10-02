package tacit

sealed trait Expression {
  def fullSourceLocation: SourceLocation
}

object Expression {
  final case class Value(
    value: Int,
    sourceLocation: SourceLocation
  ) extends Expression {
    def fullSourceLocation = sourceLocation
  }

  final case class Add(
    initialValue: Expression,
    addition: Expression,
    sourceLocation: SourceLocation
  ) extends Expression {
    def fullSourceLocation = SourceLocation(
      initialValue.fullSourceLocation.begin,
      sourceLocation.end)
  }

  sealed trait Unknown

  final case class Valid(
    expressions: List[Expression]
  ) extends Unknown

  final case class StackUnderflow(
    stack: List[Expression],
    parameterNames: Seq[String],
    sourceLocation: SourceLocation
  ) extends Unknown
}
