package tacit.jsShell

import org.scalajs.dom.html
import scalacss.internal.mutable.StyleSheet

import CssSettings.Defaults._

object CssExtensions {
  implicit final class StyleSheetInlineHelper(
    val styleSheet: StyleSheet.Base
  ) extends AnyVal {
    def renderTag: html.Style =
      styleSheet.render[html.Style]
  }
}
