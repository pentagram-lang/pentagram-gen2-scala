package tacit

import org.jline.utils.AttributedStyle

sealed trait OutputFormat {
  def style: AttributedStyle
}

object OutputFormat {
  object Normal extends OutputFormat {
    val style = default
  }
  object Error extends OutputFormat {
    val style = foreground(203)
  }
  object ErrorAccent extends OutputFormat {
    val style = foreground(167)
  }
  object Prompt extends OutputFormat {
    val style = foreground(140)
  }
  object Border extends OutputFormat {
    val style = foreground(103)
  }

  val default = AttributedStyle.DEFAULT
  def foreground = default.foreground(_)
}
