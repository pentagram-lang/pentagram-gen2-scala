package tacit.consoleShell

import org.jline.utils.AttributedStyle

final case class OutputFormat(style: AttributedStyle)

object OutputFormat {
  val default = AttributedStyle.DEFAULT

  def foreground = default.foreground(_)

  def background = default.background(_)

  val Normal =
    OutputFormat(default)

  val Error =
    OutputFormat(foreground(203))

  val ErrorAccent =
    OutputFormat(foreground(167))

  val ErrorBackground =
    OutputFormat(Error.style.background(1))

  val Info =
    OutputFormat(foreground(140))

  val InfoBackground =
    OutputFormat(background(97))

  val Value =
    OutputFormat(foreground(159))

  val ValueAccent =
    OutputFormat(foreground(51))

  val Prompt =
    OutputFormat(foreground(140))

  val Border =
    OutputFormat(foreground(103))
}
