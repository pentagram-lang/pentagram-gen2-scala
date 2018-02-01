package tacit.jsShell

import scalacss.internal.Macros.Color

import CssSettings.Defaults._

object Colors extends StyleSheet.Inline {
  import Colors.dsl._

  val background: Color = c"#1a1a1a"
  val normal: Color = c"#d1ffd1"
  val error: Color = c"#e82d2d"
  val highlight: Color = c"#d3a7ff"
  val link: Color = c"#9069b3"
}
