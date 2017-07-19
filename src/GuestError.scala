package tacit

case class GuestError(index: Int, length: Int, message: String)

object GuestError {
  def output(errors: Seq[GuestError]): OutputBlock = {
    outputMulti(
      outputOneHighlightNew(errors.head),
      errors.tail.map(outputOneHighlightPrevious))
  }

  def outputMulti(head: OutputBlock, tail: Seq[OutputBlock]): OutputBlock = {
    OutputBlock.Multi(Seq(head) ++ tail)
  }

  def outputOneHighlightNew(error: GuestError): OutputBlock = {
    outputOne(error, OutputBlock.ErrorHighlightPrevious)
  }

  def outputOneHighlightPrevious(error: GuestError): OutputBlock = {
    outputOne(error, OutputBlock.ErrorHighlightNew)
  }

  def outputOne(error: GuestError, highlight: (Int, Int) => OutputBlock): OutputBlock = {
    OutputBlock.Multi(
      highlight(error.index, error.length),
      OutputBlock.ErrorMessage(error.message))
  }
}
