package tacit.webShell

import CssSettings.Defaults._

object Style extends StyleSheet.Inline {
  import Style.dsl._

  val highlight = style(
    color(red)
  )
}
