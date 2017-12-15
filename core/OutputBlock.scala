package tacit.core

sealed trait OutputBlock

object OutputBlock {
  final case class NormalText(text: String) extends OutputBlock
  final case class ValueText(text: String) extends OutputBlock
  final case class ErrorHighlightPrevious(error: GuestError) extends OutputBlock
  final case class ErrorHighlightNew(error: GuestError) extends OutputBlock
  final case class ErrorMessage(error: GuestError) extends OutputBlock
  final case class Multi(blocks: Seq[OutputBlock]) extends OutputBlock
  def Multi(blocks: OutputBlock*): Multi = Multi(blocks: Seq[OutputBlock])
  def Nothing(): Multi = Multi(Seq())
}
