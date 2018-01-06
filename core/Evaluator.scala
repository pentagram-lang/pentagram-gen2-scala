package tacit.core

object Evaluator {
  def eval(
    expressions: Seq[Expression]
  ): Either[Seq[GuestError], Seq[Int]] = {
    val results = expressions.toStream
      .map(evalOne)
    val firstError = results
      .flatMap(_.swap.toSeq)
      .take(1)
      .toList
    if (firstError.nonEmpty) {
      Left(firstError)
    } else {
      Right(
        results
          .flatMap(_.toSeq)
          .toList)
    }
  }

  def evalOne(
    expression: Expression
  ): Either[GuestError, Int] =
    expression match {
      case Expression.Value(value, _) =>
        Right(value)
      case apply: Expression.Apply =>
        for {
          firstValue <- evalOne(apply.firstOperand)
          secondValue <- evalOne(apply.secondOperand)
          applyValue <- evalArithmetic(
            apply,
            firstValue,
            secondValue)
        } yield applyValue
    }

  def evalArithmetic(
    apply: Expression.Apply,
    firstValue: Int,
    secondValue: Int
  ): Either[GuestError, Int] =
    apply.arithmetic match {
      case Arithmetic.A_+ =>
        Right(firstValue + secondValue)
      case Arithmetic.A_- =>
        Right(firstValue - secondValue)
      case Arithmetic.A_* =>
        Right(firstValue * secondValue)
      case Arithmetic.A_/ =>
        if (secondValue == 0) {
          Left(divideByZero(apply, firstValue))
        } else {
          Right(firstValue / secondValue)
        }
    }

  def divideByZero(
    apply: Expression.Apply,
    firstValue: Int
  ): GuestError =
    GuestError(
      apply.sourceLocation,
      s"Cannot divide integer $firstValue by zero",
      Seq(
        GuestError.InfoAnnotation(
          Some(apply.firstOperand.fullSourceLocation),
          s"${apply.arithmetic.parameterNames._1} ($firstValue)"),
        GuestError.ErrorAnnotation(
          Some(apply.secondOperand.fullSourceLocation),
          s"${apply.arithmetic.parameterNames._2} (0)")
      )
    )
}
