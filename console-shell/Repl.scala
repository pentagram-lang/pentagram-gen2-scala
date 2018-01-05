package tacit.consoleShell

import scala.annotation.tailrec

import tacit.core.InputLine
import tacit.core.ReadEvalPrintChain


object Repl {
  @tailrec
  def loop(): Unit =
    SimpleTerminal readLine() match {
      case InputLine.Value(line) => {
        val outputBlock = ReadEvalPrintChain.readEvalPrint(line)
        SimpleTerminal.writeBlock(outputBlock, line)
        loop()
      }
      case InputLine.UserInterrupt() =>
        loop()
      case InputLine.EndOfStream() =>
        ()
    }
}
