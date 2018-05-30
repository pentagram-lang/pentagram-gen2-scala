package tacit.jsShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._

import CssSettings.Defaults._
import HtmlSettings.Defaults._

object ErrorLine extends RenderSimple[html.Div] {
  def renderSimple: RenderTag =
    div(
      Style.root
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      color(Colors.Repl.error)
    )
  }
}
