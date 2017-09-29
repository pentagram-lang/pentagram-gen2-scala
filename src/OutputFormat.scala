package tacit

import org.jline.utils.AttributedStyle

final case class OutputFormat(
  style: AttributedStyle)

object OutputFormat {
  val default = AttributedStyle.DEFAULT
  def foreground = default.foreground(_)

  val Normal = OutputFormat(
    default)

  val Error = OutputFormat(
    foreground(203))

  val ErrorAccent = OutputFormat(
    foreground(167))

  val Value = OutputFormat(
    foreground(159))

  val ValueAccent = OutputFormat(
    foreground(51))

  val Prompt = OutputFormat(
    foreground(140))

  val Border = OutputFormat(
    foreground(103))
}
