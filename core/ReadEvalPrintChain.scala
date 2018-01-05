package tacit.core

object ReadEvalPrintChain {
  def readEvalPrint(line: String): OutputBlock =
    readEval(line) match {
      case Left(errors) =>
        printErrors(errors)
      case Right(Nil) =>
        OutputBlock.Nothing()
      case Right(results) =>
        printResults(results)
    }

  def readEval(
    line: String
  ): Either[Seq[GuestError], Seq[Int]] =
    read(line).flatMap(Evaluator.eval)

  def read(
    line: String
  ): Either[Seq[GuestError], Seq[Expression]] =
    LineParser.parse(line)
      .flatMap(StackInterpreter.interpret)

  def printErrors(
    errors: Seq[GuestError]
  ): OutputBlock =
    printErrorsMulti(
      printErrorHighlightPrevious(errors.head),
      errors.tail.map(printErrorHighlightNew))

  def printErrorsMulti(
    head: OutputBlock,
    tail: Seq[OutputBlock]
  ): OutputBlock =
    OutputBlock.Multi(Seq(head) ++ tail)

  def printErrorHighlightPrevious(
    error: GuestError
  ): OutputBlock =
    printError(error, OutputBlock.ErrorHighlightPrevious)

  def printErrorHighlightNew(
    error: GuestError
  ): OutputBlock =
    printError(error, OutputBlock.ErrorHighlightNew)

  def printError(
    error: GuestError,
    highlight: GuestError => OutputBlock
  ): OutputBlock =
    OutputBlock.Multi(
      highlight(error),
      OutputBlock.ErrorMessage(error))

  def printResults(
    results: Seq[Int]
  ): OutputBlock =
    OutputBlock.Multi(results.map(result =>
      OutputBlock.ValueText(result.toString)))
}
