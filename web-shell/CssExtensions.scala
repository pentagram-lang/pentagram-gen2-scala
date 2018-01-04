package tacit.webShell

import scalacss.ScalatagsCss._
import org.scalajs.dom.html.Style
import scalatags.JsDom.TypedTag

import CssSettings.Defaults._

object CssExtensions {
  implicit final class StyleSheetInlineHelper(val styleSheet: StyleSheet.Inline) extends AnyVal {
    def renderTag =
      styleSheet.render[TypedTag[Style]]
  }
}
