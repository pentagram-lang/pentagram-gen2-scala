package tacit

sealed trait OutputBlock

object OutputBlock {
  final case class NormalText(text: String) extends OutputBlock
  final case class ErrorHighlightPrevious(index: Int, length: Int) extends OutputBlock
  final case class ErrorHighlightNew(index: Int, length: Int) extends OutputBlock
  final case class ErrorMessage(index: Int, text: String) extends OutputBlock
  final case class Multi(blocks: Seq[OutputBlock]) extends OutputBlock
  def Multi(blocks: OutputBlock*): Multi = Multi(blocks: Seq[OutputBlock])
  def Nothing(): Multi = Multi(Seq())
}
