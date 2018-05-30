package tacit.jsShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._

import CssSettings.Defaults._
import HtmlSettings.Defaults._

object HighlightGlow extends RenderSimple[html.Span] {
  def renderSimple: RenderTag =
    span(
      Style.root
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      color(Colors.Repl.highlight),
      textShadow :=
        Seq(
          s"0 0 2px ${Colors.background.value}",
          s"0 0 0.6em ${Colors.Repl.highlightGlow.value}"
        ).mkString(",")
    )
  }
}
