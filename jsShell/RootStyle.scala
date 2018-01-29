package tacit.jsShell

import scala.language.postfixOps

import CssSettings.Defaults._

object RootStyle extends StyleSheet.Inline {
  import RootStyle.dsl._

  val root: StyleA = style(
    backgroundColor(c"#1a1a1a"),
    fontFamily.attr := "'Source Code Pro', monospace",
    fontWeight._500,
    fontSize.large,
    color(c"#d1ffd1"),
    lineHeight(2.0 em)
  )
}
