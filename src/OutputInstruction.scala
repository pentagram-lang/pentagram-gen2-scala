package tacit

sealed trait OutputInstruction {
  def length: Int
}

object OutputInstruction {
  case class Text(format: OutputFormat, text: String) extends OutputInstruction {
    val length = text.length
  }
  case class NewLine() extends OutputInstruction {
    val length = 0
  }
  case class CursorUp() extends OutputInstruction {
    val length = 0
  }
  case class Multi(instructions: Seq[OutputInstruction]) extends OutputInstruction {
    val length = instructions.map(_.length).sum
  }
  def Multi(instructions: OutputInstruction*): OutputInstruction = Multi(instructions: Seq[OutputInstruction])

  def formatText(format: OutputFormat): String => Text =
    text => Text(format, text)
  val NormalText = formatText(OutputFormat.Normal)
  val ErrorText = formatText(OutputFormat.Error)
  val ErrorAccentText = formatText(OutputFormat.ErrorAccent)
  val BorderText = formatText(OutputFormat.Border)

}
