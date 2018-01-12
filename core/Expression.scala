package tacit.core

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

  final case class Apply(
    arithmetic: Arithmetic,
    firstOperand: Expression,
    secondOperand: Expression,
    sourceLocation: SourceLocation
  ) extends Expression {
    def fullSourceLocation =
      SourceLocation(
        firstOperand.fullSourceLocation.begin,
        sourceLocation.end)

    def operands: (Expression, Expression) =
      (firstOperand, secondOperand)
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
