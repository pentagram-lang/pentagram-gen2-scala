package tacit

sealed trait OutputInstruction {
  def length: Int
}

object OutputInstruction {
  final case class Text(format: OutputFormat, text: String) extends OutputInstruction {
    val length = text.length
  }
  final case class NewLine() extends OutputInstruction {
    val length = 0
  }
  final case class CursorUp() extends OutputInstruction {
    val length = 0
  }
  final case class Multi(instructions: Seq[OutputInstruction]) extends OutputInstruction {
    val length = instructions.map(_.length).sum
  }
  def Multi(instructions: OutputInstruction*): OutputInstruction = Multi(instructions: Seq[OutputInstruction])

  def formatText(format: OutputFormat): String => Text =
    text => Text(format, text)
  val NormalText = formatText(OutputFormat.Normal)
  val ValueText = formatText(OutputFormat.Value)
  val ValueAccentText = formatText(OutputFormat.ValueAccent)
  val ErrorText = formatText(OutputFormat.Error)
  val ErrorAccentText = formatText(OutputFormat.ErrorAccent)
  val BorderText = formatText(OutputFormat.Border)

}
