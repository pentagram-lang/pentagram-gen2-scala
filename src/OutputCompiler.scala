package tacit

final case class OutputCompiler(prompt: String, line: String, terminalWidth: Int) {
  import OutputInstruction._

  val gutterLength = prompt.length

  def compileBlock(block: OutputBlock): OutputInstruction = {
    block match {
      case OutputBlock.ErrorHighlightPrevious(sourceLocation) => Multi(
        CursorUp(),
        highlightPrevious(sourceLocation),
        highlightUnder(sourceLocation))
      case OutputBlock.ErrorHighlightNew(sourceLocation) => Multi(
        header(),
        highlightNew(sourceLocation),
        highlightUnder(sourceLocation))
      case OutputBlock.ErrorMessage(sourceLocation, message) => Multi(
        errorMessage(sourceLocation, message),
        footer())
      case OutputBlock.NormalText(text) => Multi(
        NormalText(text),
        NewLine())
      case OutputBlock.ValueText(text) => Multi(
        ValueAccentText(s" ▹ "),
        ValueText(text),
        NewLine())
      case OutputBlock.Multi(blocks) => Multi(
        blocks.map(compileBlock))
    }
  }

  def highlightPrevious(sourceLocation: SourceLocation) = line(
    gutter = ErrorText(prompt),
    left = BorderText("╭ "),
    content = Multi(
      highlightError(sourceLocation),
      NormalText(" ")),
    fill = BorderText("─"),
    right = BorderText("╮"))

  def highlightNew(sourceLocation: SourceLocation) = line(
    content = highlightError(sourceLocation))

  def highlightUnder(sourceLocation: SourceLocation) = line(
    alignIndex = sourceLocation.begin,
    content = ErrorAccentText(
      "▀" * sourceLocation.length))

  def errorMessage(sourceLocation: SourceLocation, message: String) = line(
    alignIndex = sourceLocation.begin - 2,
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

  def highlightError(sourceLocation: SourceLocation) =
    highlightText(
      line,
      sourceLocation.begin,
      sourceLocation.end,
      OutputFormat.Error)

  def highlightText(originalText: String, highlightBegin: Int, highlightEnd: Int, highlightFormat: OutputFormat) = {
    val start = originalText.substring(0, highlightBegin)
    val mid = originalText.substring(highlightBegin, highlightEnd)
    val end = originalText.substring(highlightEnd)
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
