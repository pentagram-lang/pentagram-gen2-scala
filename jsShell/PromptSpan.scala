package tacit.jsShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._

import CssSettings.Defaults._
import HtmlSettings.Defaults._

object PromptSpan extends RenderSimple[html.Span] {
  def renderSimple: RenderTag =
    span(
      Style.root,
      "(repl)> "
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      whiteSpace.pre
    )
  }
}
