package tacit.jsShell

import scalacss.internal.Macros.Color

import CssSettings.Defaults._

object Colors extends StyleSheet.Inline {
  import Colors.dsl._

  val background: Color = c"#fff"
  val caret: Color = c"#000"
  val normal: Color = c"#000"
  val selection: Color = c"#ff5efa"
  val shadow: Color = c"#ebebeb"

  object special0 {
    val pale: Color = c"#ffdffe"
    val light: Color = c"#ffaffd"
    val normal: Color = c"#ff5efa"
    val dark: Color = c"#b730b2"
  }

  object special1 {
    val light: Color = c"#a2f190"
    val normal: Color = c"#37e80e"
    val dark: Color = c"#21af00"
  }

  object special2 {
    val normal: Color = c"#865eff"
    val dark: Color = c"#2e099f"
  }

  object Repl {
    val error: Color = normal
    val highlight: Color = normal
    val highlightGlow: Color = normal
    val link: Color = normal
    val value: Color = normal
  }
}
