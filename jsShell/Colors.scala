package tacit.jsShell

import scalacss.internal.Macros.Color

import CssSettings.Defaults._

object Colors extends StyleSheet.Inline {
  import Colors.dsl._

  val background: Color = c"#fff"
  val caret: Color = c"#000"
  val normal: Color = c"#000"
  val selection: Color = c"#ff5efa"

  object special0 {
    val pale: Color = c"#ffdffe"
    val light: Color = c"#ffaffd"
  }

  object special1 {
    val light: Color = c"#adf29e"
  }

  object Repl {
    val error: Color = normal
    val highlight: Color = normal
    val highlightGlow: Color = normal
    val link: Color = normal
    val value: Color = normal
  }
}
