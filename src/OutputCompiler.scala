package tacit

final case class OutputCompiler(prompt: String, line: String, terminalWidth: Int) {
  import OutputInstruction._

  val gutterLength = prompt.length

  def compileBlock(block: OutputBlock): OutputInstruction = {
    block match {
      case OutputBlock.ErrorHighlightPrevious(index, length) => Multi(
        CursorUp(),
        highlightPrevious(index, length),
        highlightUnder(index, length))
      case OutputBlock.ErrorHighlightNew(index, length) => Multi(
        header(),
        highlightNew(index, length),
        highlightUnder(index, length))
      case OutputBlock.ErrorMessage(index, message) => Multi(
        errorMessage(index, message),
        footer())
      case OutputBlock.NormalText(text) => Multi(
        NormalText(text),
        NewLine())
      case OutputBlock.Multi(blocks) => Multi(
        blocks.map(compileBlock))
    }
  }

  def highlightPrevious(index: Int, length: Int) = line(
    gutter = ErrorText(prompt),
    left = BorderText("╭ "),
    content = Multi(
      highlightError(line, index, length),
      NormalText(" ")),
    fill = BorderText("─"),
    right = BorderText("╮"))

  def highlightNew(index: Int, length: Int) = line(
    content = highlightError(line, index, length))

  def highlightUnder(index: Int, length: Int) = line(
    alignIndex = index,
    content = ErrorAccentText("▀" * length))

  def errorMessage(index: Int, message: String) = line(
    alignIndex = index - 2,
    content = ErrorText("✗ " + message))

  def header() = Multi(
    line(
      left = BorderText("┏━┯━ @"),
      content = ErrorText(" (repl) "),
      fill = BorderText("━"),
      right = BorderText("┯━┓")),
    line(
      left = BorderText("┃ ╰"),
      fill = BorderText("─"),
      right = BorderText("╯ ┃")))

  def footer() = line(
    left = BorderText("┗"),
    fill = BorderText("━"),
    right = BorderText("┛"))

  def line(
    gutter: Text = padding(NormalText(" "), gutterLength),
    left: OutputInstruction = BorderText("┃ "),
    alignIndex: Int = 0,
    content: OutputInstruction = NormalText(""),
    fill: Text = NormalText(" "),
    right: OutputInstruction = BorderText(" ┃"))
  = {
    val leftBasic = Multi(
      gutter,
      left,
      alignLeft(fill, alignIndex, content))
    val leftPadded = padRight(
      leftBasic,
      fill,
      terminalWidth - right.length)
    Multi(
      leftPadded,
      right,
      NewLine())
  }

  def highlightError = highlightText(
    _: String,
    _: Int,
    _: Int,
    OutputFormat.Error)

  def highlightText(originalText: String, highlightIndex: Int, highlightLength: Int, highlightFormat: OutputFormat) = {
    val start = originalText.substring(0, highlightIndex)
    val mid = originalText.substring(highlightIndex, highlightIndex + highlightLength)
    val end = originalText.substring(highlightIndex + highlightLength)
    Multi(
      NormalText(start),
      Text(highlightFormat, mid),
      NormalText(end))
  }

  def alignLeft(fill: Text, index: Int, content: OutputInstruction) = Multi(
    padding(fill, index),
    content)

  def padRight(original: OutputInstruction, fill: Text, length: Int) = Multi(
    original,
    padding(fill, length - original.length))

  def padding(fill: Text, length: Int) = Text(
    fill.format,
    fill.text.substring(0, 1) * length)
}

object OutputCompiler {
  def compileBlock(block: OutputBlock, prompt: String, line: String, terminalWidth: Int): OutputInstruction = {
    val compiler = OutputCompiler(prompt, line, terminalWidth)
    compiler.compileBlock(block)
  }
}
