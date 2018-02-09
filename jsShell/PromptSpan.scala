package tacit.jsShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalatags.JsDom.all.{
  Double2CssNumber => _,
  Int2CssNumber => _,
  _
}

import CssSettings.Defaults._

object PromptSpan extends RenderSimple[html.Span] {
  def renderSimple: RenderTag =
    span(
      Style.root,
      "(repl)> "
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      color(Colors.highlight),
      whiteSpace.pre
    )
  }
}
