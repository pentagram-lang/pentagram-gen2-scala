package tacit.jvmShell

sealed trait OutputInstruction {
  def length: Int
}

object OutputInstruction {
  final case class Text(
    format: OutputFormat,
    text: String
  ) extends OutputInstruction {
    val length = text.length
  }

  final case class NewLine() extends OutputInstruction {
    val length = 0
  }

  final case class CursorUp() extends OutputInstruction {
    val length = 0
  }

  final case class Multi(
    instructions: Seq[OutputInstruction]
  ) extends OutputInstruction {
    val length = instructions.map(_.length).sum
  }

  def Multi(
    instructions: OutputInstruction*
  ): OutputInstruction =
    Multi(instructions: Seq[OutputInstruction])
}

object OutputInstructionExtensions {
  implicit final class OutputFormatHelper(
    val outputFormat: OutputFormat
  ) extends AnyVal {
    def apply(text: String): OutputInstruction.Text =
      OutputInstruction.Text(outputFormat, text)
  }
}
