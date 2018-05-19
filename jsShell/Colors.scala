package tacit.jsShell

import scalacss.internal.Macros.Color

import CssSettings.Defaults._

object Colors extends StyleSheet.Inline {
  import Colors.dsl._

  val background: Color = c"#000"
  val edgeHighlight: Color = c"rgba(229, 255, 229, 0.08)"
  val error: Color = c"#e82d2d"
  val highlight: Color = c"#d3a7ff"
  val highlightGlow: Color = c"rgba(229, 255, 229, 0.41)"
  val link: Color = c"#9069b3"
  val normal: Color = c"#fff"
  val selection: Color = c"rgba(167, 140, 151, 0.99)"
  val value: Color = c"#46f6f6"
}
