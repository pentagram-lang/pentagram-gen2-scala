package tacit

import org.jline.utils.AttributedStyle

sealed trait OutputFormat {
  def style: AttributedStyle
}

object OutputFormat {
  object Normal extends OutputFormat {
    val style = AttributedStyle.DEFAULT
  }
  object Error extends OutputFormat {
    val style = AttributedStyle.DEFAULT.foreground(203)
  }
  object ErrorAccent extends OutputFormat {
    val style = AttributedStyle.DEFAULT.foreground(167)
  }
  object Prompt extends OutputFormat {
    val style = AttributedStyle.DEFAULT.foreground(140)
  }
  object Border extends OutputFormat {
    val style = AttributedStyle.DEFAULT.foreground(103)
  }
}
