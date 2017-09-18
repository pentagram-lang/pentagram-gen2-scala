package tacit

import org.jline.reader.LineReaderBuilder
import org.jline.reader.UserInterruptException
import org.jline.reader.EndOfFileException
import org.jline.utils.AttributedStyle
import org.jline.utils.AttributedString
import org.jline.utils.InfoCmp.Capability
import org.jline.utils.Curses

object SimpleTerminal {
  def readLine(): InputLine = {
    try {
      InputLine.Value(reader.readLine(prompt))
    }
    catch {
      case _: UserInterruptException =>
        InputLine.UserInterrupt()
      case _: EndOfFileException =>
        InputLine.EndOfStream()
    }
  }

  def writeBlock(block: OutputBlock, line: String): Unit = {
    val instruction = OutputCompiler.compileBlock(block, plainPrompt, line, terminal.getWidth())
    SimpleTerminal.writeInstruction(instruction)
  }

  val reader = LineReaderBuilder.builder().build()
  val terminal = reader.getTerminal

  val plainPrompt = "(repl)"
  val prompt = getAnsiText(OutputFormat.Prompt.style, s"$plainPrompt> ")

  def writeInstruction(instruction: OutputInstruction): Unit =
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

  def writeStringCapabilityN(capability: Capability): Seq[Object] => Unit = {
    val value = terminal.getStringCapability(capability)
    params => Curses.tputs(terminal.writer, value, params: _*)
  }

  def writeStringCapability0(capability: Capability): () => Unit = {
    val result = writeStringCapabilityN(capability)
    () => result(Seq())
  }

  val writeCarriageReturn = writeStringCapability0(Capability.carriage_return)

  val writeCursorUp = writeStringCapability0(Capability.cursor_up)

  val writeCursorDown = writeStringCapability0(Capability.cursor_down)

  def writeNewLine(): Unit = {
    writeCarriageReturn()
    writeCursorDown()
  }

  def writeText(style: AttributedStyle, text: String): Unit = {
    terminal.writer.write(
      getAnsiText(style, text))
  }

  def getAnsiText(style: AttributedStyle, text: String): String =
    new AttributedString(text, style).toAnsi(terminal)
}
