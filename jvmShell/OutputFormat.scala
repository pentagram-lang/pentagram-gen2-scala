package tacit.jvmShell

import org.jline.utils.AttributedStyle

final case class OutputFormat(style: AttributedStyle)

object OutputFormat {
  val default: AttributedStyle =
    AttributedStyle.DEFAULT

  def foreground: Int => AttributedStyle =
    default.foreground(_)

  def background: Int => AttributedStyle =
    default.background(_)

  val Normal: OutputFormat =
    OutputFormat(default)

  val Error: OutputFormat =
    OutputFormat(foreground(203))

  val ErrorAccent: OutputFormat =
    OutputFormat(foreground(167))

  val ErrorBackground: OutputFormat =
    OutputFormat(Error.style.background(1))

  val Info: OutputFormat =
    OutputFormat(foreground(140))

  val InfoBackground: OutputFormat =
    OutputFormat(background(97))

  val Value: OutputFormat =
    OutputFormat(foreground(159))

  val ValueAccent: OutputFormat =
    OutputFormat(foreground(51))

  val Prompt: OutputFormat =
    OutputFormat(foreground(140))

  val Border: OutputFormat =
    OutputFormat(foreground(103))
}
