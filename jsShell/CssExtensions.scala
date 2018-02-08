package tacit.jsShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalacss.internal.mutable.StyleSheet
import scalatags.JsDom.TypedTag

import CssSettings.Defaults._

object CssExtensions {
  implicit final class StyleSheetInlineHelper(
    val styleSheet: StyleSheet.Base
  ) extends AnyVal {
    def renderTag: TypedTag[html.Style] =
      styleSheet.render[TypedTag[html.Style]]
  }
}
