package tacit.consoleShell

import tacit.core.GuestError
import tacit.core.OutputBlock
import tacit.core.SourceLocation

import OutputFormat._
import OutputInstruction._
import OutputInstructionExtensions._

final case class OutputCompiler(
  prompt: String,
  lineText: String,
  terminalWidth: Int
) {
  val gutterLength = prompt.length
  val indent = lineText.indexWhere(_ != ' ') max 0

  @SuppressWarnings(Array("org.wartremover.warts.Recursion"))
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
      alignIndex = defaultLineAlignIndex,
      content =
        Multi(highlightError(sourceLocation), Normal(" ")),
      fill = Border("─"),
      right = Border("╮")
    )

  def highlightNew(sourceLocation: SourceLocation) =
    simpleLine(
      alignIndex = defaultLineAlignIndex,
      content = highlightError(sourceLocation)
    )

  def highlightUnder(sourceLocation: SourceLocation) =
    simpleLine(
      alignIndex = sourceLocation.begin,
      content = ErrorAccent("▀" * sourceLocation.length)
    )

  def errorMessage(
    sourceLocation: SourceLocation,
    message: String
  ) =
    simpleLine(
      alignIndex = sourceLocation.begin - 2,
      content = Error("✗ " + message)
    )

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
      simpleLine(
        alignIndex = defaultLineAlignIndex,
        content =
          Multi(padRight(left, Normal(" "), leftWidth), right)
      )
    Multi(
      blankLine(),
      Multi(table.map(Function.tupled(buildRow(_, _))))
    )
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
        gutter = defaultLineGutter,
        left = Border("┏━┯━ @"),
        alignIndex = defaultLineAlignIndex,
        content = Error(" (repl) "),
        fill = Border("━"),
        right = Border("┯━┓")),
      line(
        gutter = defaultLineGutter,
        left = Border("┃ ╰"),
        alignIndex = defaultLineAlignIndex,
        content = defaultLineContent,
        fill = Border("─"),
        right = Border("╯ ┃"))
    )

  def footer() =
    line(
      gutter = defaultLineGutter,
      left = Border("┗"),
      alignIndex = defaultLineAlignIndex,
      content = defaultLineContent,
      fill = Border("━"),
      right = Border("┛"))

  def line(
    gutter: Text,
    left: OutputInstruction,
    alignIndex: Int,
    content: OutputInstruction,
    fill: Text,
    right: OutputInstruction
  ) = {
    val leftBasic = Multi(
      gutter,
      left,
      alignLeft(fill, alignIndex, content))
    val leftPadded =
      padRight(leftBasic, fill, terminalWidth - right.length)
    Multi(leftPadded, right, NewLine())
  }

  def simpleLine(
    alignIndex: Int,
    content: OutputInstruction
  ) =
    line(
      gutter = defaultLineGutter,
      left = defaultLineLeft,
      alignIndex = alignIndex,
      content = content,
      fill = defaultLineFill,
      right = defaultLineRight
    )

  def blankLine() =
    simpleLine(
      alignIndex = defaultLineAlignIndex,
      content = defaultLineContent
    )

  val defaultLineGutter = padding(Normal(" "), gutterLength)
  val defaultLineLeft = Border("┃ ")
  val defaultLineAlignIndex = 0
  val defaultLineContent = Normal("")
  val defaultLineFill = Normal(" ")
  val defaultLineRight = Border(" ┃")

  def highlightError(sourceLocation: SourceLocation) =
    highlightLocation(sourceLocation, Error)

  def highlightLocation(
    sourceLocation: SourceLocation,
    outputFormat: OutputFormat
  ) =
    highlightText(
      lineText,
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
    lineText: String,
    terminalWidth: Int
  ): OutputInstruction = {
    val compiler =
      OutputCompiler(prompt, lineText, terminalWidth)
    compiler.compileBlock(block)
  }
}
