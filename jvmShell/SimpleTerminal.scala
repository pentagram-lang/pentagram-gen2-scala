package tacit.jvmShell

import org.jline.reader.EndOfFileException
import org.jline.reader.LineReaderBuilder
import org.jline.reader.UserInterruptException
import org.jline.utils.AttributedString
import org.jline.utils.AttributedStyle
import org.jline.utils.Curses
import org.jline.utils.InfoCmp.Capability
import scala.util.Failure
import scala.util.Success
import scala.util.Try

import tacit.core.InputLine
import tacit.core.OutputBlock

object SimpleTerminal {
  def readLine(): Try[InputLine] =
    Try(reader.readLine(prompt)) match {
      case Success(line) =>
        Success(InputLine.Value(line))
      case Failure(_: UserInterruptException) =>
        Success(InputLine.UserInterrupt())
      case Failure(_: EndOfFileException) =>
        Success(InputLine.EndOfStream())
      case Failure(exception: Any) =>
        Failure(exception)
    }

  def writeBlock(block: OutputBlock, line: String): Unit = {
    val instruction = OutputCompiler.compileBlock(
      block,
      plainPrompt,
      line,
      terminal.getWidth())
    writeInstruction(instruction)
  }

  private val reader = LineReaderBuilder.builder().build()
  private val terminal = reader.getTerminal

  private val plainPrompt = "(repl)"
  private val prompt =
    toAnsiText(OutputFormat.Prompt.style, s"$plainPrompt> ")

  @SuppressWarnings(Array("org.wartremover.warts.Recursion"))
  private def writeInstruction(
    instruction: OutputInstruction): Unit =
    instruction match {
      case OutputInstruction.Text(_, "") =>
        ()
      case OutputInstruction.Text(format, text) =>
        writeText(format.style, text)
      case OutputInstruction.NewLine() =>
        writeNewLine()
      case OutputInstruction.CursorUp() =>
        writeCursorUp()
      case OutputInstruction.Multi(instructions) =>
        instructions foreach {
          writeInstruction(_)
        }
    }

  private def writeStringCapabilityN(
    capability: Capability
  ): Seq[Object] => Unit = {
    val value = terminal.getStringCapability(capability)
    params =>
      Curses.tputs(terminal.writer, value, params: _*)
  }

  private def writeStringCapability0(
    capability: Capability
  ): () => Unit = {
    val result = writeStringCapabilityN(capability)
    () =>
      result(Seq())
  }

  private val writeCarriageReturn =
    writeStringCapability0(Capability.carriage_return)

  private val writeCursorUp =
    writeStringCapability0(Capability.cursor_up)

  private val writeCursorDown =
    writeStringCapability0(Capability.cursor_down)

  private def writeNewLine(): Unit = {
    writeCarriageReturn()
    writeCursorDown()
  }

  private def writeText(
    style: AttributedStyle,
    text: String
  ): Unit =
    terminal.writer.write(toAnsiText(style, text))

  private def toAnsiText(
    style: AttributedStyle,
    text: String
  ): String =
    new AttributedString(text, style).toAnsi(terminal)
}
