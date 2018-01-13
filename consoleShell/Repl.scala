package tacit.consoleShell

import scala.annotation.tailrec
import scala.util.Failure
import scala.util.Success

import tacit.core.InputLine
import tacit.core.ReadEvalPrintChain

object Repl {
  @tailrec
  def loop(): Unit =
    SimpleTerminal.readLine() match {
      case Success(InputLine.Value(line)) => {
        val outputBlock =
          ReadEvalPrintChain.readEvalPrint(line)
        SimpleTerminal.writeBlock(outputBlock, line)
        loop()
      }
      case Success(InputLine.UserInterrupt()) =>
        loop()
      case Success(InputLine.EndOfStream()) =>
        ()
      case Failure(_) =>
        ()
    }
}
