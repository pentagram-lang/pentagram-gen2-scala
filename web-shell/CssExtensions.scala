package tacit.webShell

import scalacss.ScalatagsCss._
import org.scalajs.dom.raw.HTMLStyleElement
import scalatags.JsDom.TypedTag

import CssSettings.Defaults._

object CssExtensions {
  implicit final class StyleSheetInlineHelper(val styleSheet: StyleSheet.Inline) extends AnyVal {
    def renderTag =
      styleSheet.render[TypedTag[HTMLStyleElement]]
  }
}
