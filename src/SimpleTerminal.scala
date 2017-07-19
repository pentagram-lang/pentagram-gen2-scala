package tacit

import org.jline.reader.LineReaderBuilder
import org.jline.reader.UserInterruptException
import org.jline.reader.EndOfFileException
import org.jline.utils.AttributedStyle
import org.jline.utils.AttributedString
import org.jline.utils.InfoCmp.Capability
import org.jline.utils.Curses
import java.io.Writer

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

  def writeBlock(block: OutputBlock): Unit = {
    block match {
      case OutputBlock.Nothing() =>
        ()
      case OutputBlock.ErrorHighlightPrevious(index, length) =>
        writeCursorUp(terminal.writer)
        writeCursorRightN(index + plainPrompt.length, terminal.writer)
        terminal.writer.write(errorText("*" * length))
        writeNewLine(terminal.writer)
      case OutputBlock.ErrorHighlightNew(index, length) =>
        writeCursorRightN(plainPrompt.length - 2, terminal.writer)
        terminal.writer.write("| ")
        writeCursorRightN(index, terminal.writer)
        terminal.writer.write(errorText("*" * length))
        writeNewLine(terminal.writer)
      case OutputBlock.ErrorMessage(message) =>
        writeCursorRightN(plainPrompt.length - 2, terminal.writer)
        terminal.writer.write("| ")
        terminal.writer.write(errorText(message))
        writeNewLine(terminal.writer)
      case OutputBlock.NormalText(text) =>
        terminal.writer.write(text)
        writeNewLine(terminal.writer)
      case OutputBlock.Multi(blocks) =>
        blocks.foreach(writeBlock)
    }
  }

  val reader = LineReaderBuilder.builder().build()
  var terminal = reader.getTerminal

  def styledText(style: AttributedStyle): String => String = {
    text => new AttributedString(text, style).toAnsi(terminal)
  }

  val errorText = styledText(AttributedStyle.BOLD.foreground(AttributedStyle.RED))

  val promptText = styledText(AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE))

  var plainPrompt = "tacit> "
  val prompt = promptText(plainPrompt)

  def writeStringCapabilityN(capability: Capability): (Seq[Object], Writer) => Unit = {
    var value = terminal.getStringCapability(capability)
    (params, writer) =>
      Curses.tputs(writer, value, params: _*)
  }

  def writeStringCapability0(capability: Capability): Writer => Unit = {
    val result = writeStringCapabilityN(capability)
    writer =>
      result(Seq(), writer)
  }

  def writeStringCapability1N(capability: Capability): (Int, Writer) => Unit = {
    val result = writeStringCapabilityN(capability)
    (value, writer) =>
      result(Seq(value.asInstanceOf[Object]), writer)
  }

  val writeCarriageReturn = writeStringCapability0(Capability.carriage_return)

  val writeCursorUp = writeStringCapability0(Capability.cursor_up)

  val writeCursorDown = writeStringCapability0(Capability.cursor_down)

  val writeCursorRightN = writeStringCapability1N(Capability.parm_right_cursor)

  def writeNewLine(writer: Writer): Unit = {
    writeCarriageReturn(writer)
    writeCursorDown(writer)
  }
}
