package tacit.consoleShell

import tacit.core.GuestError
import tacit.core.OutputBlock
import tacit.core.SourceLocation

final case class OutputCompiler(
  prompt: String,
  line: String,
  terminalWidth: Int
) {
  import OutputInstruction._
  import OutputInstructionExtensions._
  import OutputFormat._

  val gutterLength = prompt.length
  val indent = line.indexWhere(_ != ' ') max 0

  def compileBlock(block: OutputBlock): OutputInstruction =
    block match {
      case OutputBlock.ErrorHighlightPrevious(error) =>
        Multi(
          CursorUp(),
          highlightPrevious(error.sourceLocation),
          highlightUnder(error.sourceLocation))
      case OutputBlock.ErrorHighlightNew(error) =>
        Multi(
          header(),
          highlightNew(error.sourceLocation),
          highlightUnder(error.sourceLocation))
      case OutputBlock.ErrorMessage(error) =>
        Multi(
          errorMessage(error.sourceLocation, error.message),
          errorAnnotationsOption(error.annotations),
          footer())
      case OutputBlock.NormalText(text) =>
        Multi(Normal(text), NewLine())
      case OutputBlock.ValueText(text) =>
        Multi(ValueAccent(" ▹ "), Value(text), NewLine())
      case OutputBlock.Multi(blocks) =>
        Multi(blocks.map(compileBlock))
    }

  def highlightPrevious(sourceLocation: SourceLocation) =
    line(
      gutter = Error(prompt),
      left = Border("╭ "),
      content =
        Multi(highlightError(sourceLocation), Normal(" ")),
      fill = Border("─"),
      right = Border("╮"))

  def highlightNew(sourceLocation: SourceLocation) =
    line(content = highlightError(sourceLocation))

  def highlightUnder(sourceLocation: SourceLocation) =
    line(
      alignIndex = sourceLocation.begin,
      content = ErrorAccent("▀" * sourceLocation.length))

  def errorMessage(
    sourceLocation: SourceLocation,
    message: String
  ) =
    line(
      alignIndex = sourceLocation.begin - 2,
      content = Error("✗ " + message))

  def errorAnnotationsOption(
    annotations: Seq[GuestError.Annotation]
  ) =
    if (annotations.nonEmpty) {
      errorAnnotations(annotations)
    } else {
      Multi()
    }

  def errorAnnotations(
    annotations: Seq[GuestError.Annotation]
  ) = {
    val table = annotations.map(errorAnnotation)
    val leftWidth = table.map(_._1.length).max
    def buildRow(
      left: OutputInstruction,
      right: OutputInstruction
    ) =
      line(content =
        Multi(padRight(left, Normal(" "), leftWidth), right))
    Multi(
      line(),
      Multi(table.map(Function.tupled(buildRow(_, _)))))
  }

  def errorAnnotation(annotation: GuestError.Annotation) = {
    def source(outputFormat: OutputFormat) =
      annotation.sourceLocation match {
        case Some(sourceLocation) =>
          highlightLocation(sourceLocation, outputFormat)
        case None =>
          Multi(
            Normal(" " * indent),
            outputFormat("<missing>"))
      }
    val background = if (annotation.isError) {
      ErrorBackground
    } else {
      InfoBackground
    }
    val arrow = if (annotation.isError) {
      Error
    } else {
      Info
    }
    (
      source(background),
      Multi(arrow(" ⇐ "), background(annotation.message)))
  }

  def header() =
    Multi(
      line(
        left = Border("┏━┯━ @"),
        content = Error(" (repl) "),
        fill = Border("━"),
        right = Border("┯━┓")),
      line(
        left = Border("┃ ╰"),
        fill = Border("─"),
        right = Border("╯ ┃"))
    )

  def footer() =
    line(
      left = Border("┗"),
      fill = Border("━"),
      right = Border("┛"))

  def line(
    gutter: Text = padding(Normal(" "), gutterLength),
    left: OutputInstruction = Border("┃ "),
    alignIndex: Int = 0,
    content: OutputInstruction = Normal(""),
    fill: Text = Normal(" "),
    right: OutputInstruction = Border(" ┃")
  ) = {
    val leftBasic = Multi(
      gutter,
      left,
      alignLeft(fill, alignIndex, content))
    val leftPadded =
      padRight(leftBasic, fill, terminalWidth - right.length)
    Multi(leftPadded, right, NewLine())
  }

  def highlightError(sourceLocation: SourceLocation) =
    highlightLocation(sourceLocation, Error)

  def highlightLocation(
    sourceLocation: SourceLocation,
    outputFormat: OutputFormat
  ) =
    highlightText(
      line,
      sourceLocation.begin,
      sourceLocation.end,
      outputFormat)

  def highlightText(
    originalText: String,
    highlightBegin: Int,
    highlightEnd: Int,
    highlightFormat: OutputFormat
  ) = {
    val start = originalText.substring(0, highlightBegin)
    val mid =
      originalText.substring(highlightBegin, highlightEnd)
    val end = originalText.substring(highlightEnd)
    Multi(Normal(start), highlightFormat(mid), Normal(end))
  }

  def alignLeft(
    fill: Text,
    index: Int,
    content: OutputInstruction
  ) = Multi(padding(fill, index), content)

  def padRight(
    original: OutputInstruction,
    fill: Text,
    length: Int
  ) = Multi(original, padding(fill, length - original.length))

  def padding(fill: Text, length: Int) =
    fill.format(fill.text.substring(0, 1) * length)
}

object OutputCompiler {
  def compileBlock(
    block: OutputBlock,
    prompt: String,
    line: String,
    terminalWidth: Int
  ): OutputInstruction = {
    val compiler = OutputCompiler(prompt, line, terminalWidth)
    compiler.compileBlock(block)
  }
}
