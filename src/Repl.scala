package tacit

import scala.annotation.tailrec

object Repl {
  def readEvalPrint(line: String): OutputBlock =
    readEval(line) match {
      case Left(errors) =>
        printErrors(errors)
      case Right(Nil) =>
        OutputBlock.Nothing()
      case Right(results) =>
        printResults(results)
    }

  def readEval(line: String) =
    read(line).map(eval)

  def read(line: String) =
    LineParser.parse(line)
      .flatMap(StackInterpreter.interpret)

  def eval(
    expressions: Seq[Expression]
  ): Seq[Int] =
    expressions.map(evalOne)

  def evalOne(expression: Expression): Int =
    expression match {
      case Expression.Value(value, _) =>
        value
      case Expression.Add(initialValue, addition, _) =>
        evalOne(initialValue) + evalOne(addition)
    }

  def printErrors(
    errors: Seq[GuestError]
  ): OutputBlock =
    GuestError.output(errors)

  def printResults(
    results: Seq[Int]
  ): OutputBlock =
    OutputBlock.Multi(results.map(result =>
      OutputBlock.ValueText(result.toString)))

  @tailrec
  def loop(): Unit =
    SimpleTerminal readLine() match {
      case InputLine.Value(line) => {
        val outputBlock = readEvalPrint(line)
        SimpleTerminal.writeBlock(outputBlock, line)
        loop()
      }
      case InputLine.UserInterrupt() =>
        loop()
      case InputLine.EndOfStream() =>
        ()
    }
}
