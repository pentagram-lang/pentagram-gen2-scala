package tacit.webShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalatags.JsDom.TypedTag

import CssSettings.Defaults._

object CssExtensions {
  implicit final class StyleSheetInlineHelper(
    val styleSheet: StyleSheet.Inline
  ) extends AnyVal {
    def renderTag: TypedTag[html.Style] =
      styleSheet.render[TypedTag[html.Style]]
  }
}
