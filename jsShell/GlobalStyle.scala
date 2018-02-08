package tacit.jsShell

import CssSettings.Defaults._

object GlobalStyle extends StyleSheet.Standalone {
  import GlobalStyle.dsl._

  "*".selection - (
    backgroundColor(Colors.selection)
  )
}
