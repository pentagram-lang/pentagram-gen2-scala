package tacit.jsShell

import CssSettings.Defaults._

object BodyStyle extends StyleSheet.Inline {
  import BodyStyle.dsl._

  val root: StyleA = style(
    margin.`0`,
    overflowY.scroll
  )
}
