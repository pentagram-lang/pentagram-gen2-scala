package tacit

final case class GuestError(sourceLocation: SourceLocation, message: String)

object GuestError {
  def output(errors: Seq[GuestError]): OutputBlock =
    outputMulti(
      outputOneHighlightPrevious(errors.head),
      errors.tail.map(outputOneHighlightNew))

  def outputMulti(head: OutputBlock, tail: Seq[OutputBlock]): OutputBlock =
    OutputBlock.Multi(Seq(head) ++ tail)

  def outputOneHighlightPrevious(error: GuestError): OutputBlock =
    outputOne(error, OutputBlock.ErrorHighlightPrevious)

  def outputOneHighlightNew(error: GuestError): OutputBlock =
    outputOne(error, OutputBlock.ErrorHighlightNew)

  def outputOne(error: GuestError, highlight: SourceLocation => OutputBlock): OutputBlock =
    OutputBlock.Multi(
      highlight(error.sourceLocation),
      OutputBlock.ErrorMessage(error.sourceLocation, error.message))
}
