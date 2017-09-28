package tacit

object StackInterpreter {
  def interpret(
    terms: Seq[SyntaxTerm]
  ): Either[Seq[GuestError], Seq[Expression]] =
    interpretMany(terms.toList, List()) match {
      case Expression.Valid(expressions) =>
        Right(expressions.reverse)
      case Expression.StackUnderflow(stack, parameterNames, sourceLocation) =>
        Left(Seq(GuestError(
          sourceLocation,
          "Not enough values to call this method")))
    }

  def interpretMany(
    terms: List[SyntaxTerm],
    stack: List[Expression]
  ): Expression.Unknown =
    terms match {
      case headTerm :: restTerms =>
        interpretOne(headTerm, stack) match {
          case Expression.Valid(stack) =>
            interpretMany(restTerms, stack)
          case invalid =>
            invalid
        }
      case Nil =>
        Expression.Valid(stack)
    }

  def interpretOne(
    term: SyntaxTerm,
    stack: List[Expression]
  ): Expression.Unknown =
    term match {
      case SyntaxTerm.Literal(value, sourceLocation) =>
        pushStack(
          Expression.Value(value, sourceLocation),
          stack)
      case SyntaxTerm.Plus(sourceLocation) =>
        useStack(
          Expression.Add,
          ("initial-value", "addition"),
          sourceLocation,
          stack)
    }

  def pushStack(
    expression: Expression,
    stack: List[Expression]
  ): Expression.Valid =
    Expression.Valid(expression :: stack)

  def useStack(
    expression: (Expression, Expression, SourceLocation) => Expression,
    parameterNames: (String, String),
    sourceLocation: SourceLocation,
    stack: List[Expression]
  ): Expression.Unknown =
    stack match {
      case first :: second :: rest =>
        pushStack(
          expression(second, first, sourceLocation),
          rest)
      case _ =>
        Expression.StackUnderflow(
          stack,
          Seq(parameterNames._1, parameterNames._2),
          sourceLocation)
    }
}
