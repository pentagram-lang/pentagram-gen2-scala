package tacit

import scala.annotation.tailrec

object Repl {
  def readEvalPrint(line: String): OutputBlock = {
    read(line) match {
      case Left(errors) =>
        printErrors(errors)
      case Right(Nil) =>
        OutputBlock.Nothing()
      case Right(result) =>
        printResult(result)
    }
  }

  def read = LineParser.parse(_)

  def printErrors(errors: Seq[GuestError]): OutputBlock = {
    GuestError.output(errors)
  }

  def printResult(result: Seq[SyntaxTerm]): OutputBlock = {
    OutputBlock.NormalText(s"$result")
  }

  @tailrec
  def loop(): Unit = {
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
}
