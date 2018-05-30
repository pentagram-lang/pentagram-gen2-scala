package tacit.jsShell

import org.scalajs.dom.html
import scala.language.postfixOps
import scalacss.ScalatagsCss._

import CssSettings.Defaults._
import HtmlSettings.Defaults._

object ValueLine extends RenderSimple[html.Div] {
  def renderSimple: RenderTag =
    div(
      Style.root
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      marginLeft(2 ch),
      color(Colors.Repl.value),
      fontWeight._700,
      &.before(
        content := "\"▶ \"",
        opacity(0.95),
        position.relative,
        bottom(-0.05 em)
      )
    )
  }
}
