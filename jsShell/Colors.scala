package tacit.jsShell

import scalacss.internal.Macros.Color

import CssSettings.Defaults._

object Colors extends StyleSheet.Inline {
  import Colors.dsl._

  val background: Color = c"#fff"
  val caret: Color = c"#000"
  val normal: Color = c"#000"
  val selection: Color = c"#ff5efa"
  val special0: Color = c"#ffdeef"

  object Repl {
    val error: Color = normal
    val highlight: Color = normal
    val highlightGlow: Color = normal
    val link: Color = normal
    val value: Color = normal
  }
}
