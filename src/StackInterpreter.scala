package tacit

object StackInterpreter {
  def interpret(
    terms: Seq[SyntaxTerm]
  ): Either[Seq[GuestError], Seq[Expression]] =
    interpretMany(terms.toList, List()) match {
      case Expression.Valid(expressions) =>
        Right(expressions.reverse)
      case stackUnderflow: Expression.StackUnderflow =>
        Left(Seq(convertToError(stackUnderflow)))
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
      case SyntaxTerm.Operator(arithmetic, sourceLocation) =>
        useStack(
          Expression.Apply,
          arithmetic,
          ("initial-value",
            secondParameterName(arithmetic)),
          sourceLocation,
          stack)
    }

  def pushStack(
    expression: Expression,
    stack: List[Expression]
  ): Expression.Valid =
    Expression.Valid(expression :: stack)

  def useStack(
    expression: (Arithmetic, Expression, Expression, SourceLocation) => Expression,
    arithmetic: Arithmetic,
    parameterNames: (String, String),
    sourceLocation: SourceLocation,
    stack: List[Expression]
  ): Expression.Unknown =
    stack match {
      case first :: second :: rest =>
        pushStack(
          expression(arithmetic, second, first, sourceLocation),
          rest)
      case _ =>
        Expression.StackUnderflow(
          stack,
          Seq(parameterNames._1, parameterNames._2),
          sourceLocation)
    }

  def secondParameterName(arithmetic: Arithmetic) =
    arithmetic match {
      case Arithmetic.+ => "addition"
      case Arithmetic.- => "subtraction"
      case Arithmetic.* => "multiplier"
      case Arithmetic./ => "divisor"
    }

  def convertToError(
    stackUnderflow: Expression.StackUnderflow
  ): GuestError = {
    val missingCount =
      (stackUnderflow.parameterNames.length
      - stackUnderflow.stack.length)
    val missingAnnotations =
      stackUnderflow.parameterNames
        .take(missingCount)
        .map(name => GuestError.Annotation(
          sourceLocation = None,
          message = name,
          isError = true))
    val infoAnnotations =
      stackUnderflow.parameterNames
        .drop(missingCount)
        .zip(stackUnderflow.stack.reverse)
        .map({ case (name, expression) => GuestError.Annotation(
          sourceLocation = Some(expression.fullSourceLocation),
          message = name,
          isError = false) })
    GuestError(
      stackUnderflow.sourceLocation,
      "Not enough values to call this method",
      missingAnnotations ++ infoAnnotations)
  }
}
