package tacit.consoleShell

import org.jline.reader.EndOfFileException
import org.jline.reader.LineReaderBuilder
import org.jline.reader.UserInterruptException
import org.jline.utils.AttributedString
import org.jline.utils.AttributedStyle
import org.jline.utils.Curses
import org.jline.utils.InfoCmp.Capability

import tacit.core.InputLine
import tacit.core.OutputBlock

object SimpleTerminal {
  def readLine(): InputLine =
    try {
      InputLine.Value(reader.readLine(prompt))
    } catch {
      case _: UserInterruptException =>
        InputLine.UserInterrupt()
      case _: EndOfFileException =>
        InputLine.EndOfStream()
    }

  def writeBlock(block: OutputBlock, line: String): Unit = {
    val instruction = OutputCompiler.compileBlock(
      block,
      plainPrompt,
      line,
      terminal.getWidth())
    SimpleTerminal.writeInstruction(instruction)
  }

  private val reader = LineReaderBuilder.builder().build()
  private val terminal = reader.getTerminal

  private val plainPrompt = "(repl)"
  private val prompt =
    getAnsiText(OutputFormat.Prompt.style, s"$plainPrompt> ")

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
    terminal.writer.write(getAnsiText(style, text))

  private def getAnsiText(
    style: AttributedStyle,
    text: String
  ): String =
    new AttributedString(text, style).toAnsi(terminal)
}
