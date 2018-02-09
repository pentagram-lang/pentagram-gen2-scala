package tacit.jsShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalatags.JsDom.all.{
  Double2CssNumber => _,
  Int2CssNumber => _,
  _
}

import CssSettings.Defaults._

object EchoLine extends RenderSimple[html.Div] {
  def renderSimple: RenderTag =
    div(
      Style.root,
      PromptSpan(Style.prompt).root
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style()

    val prompt: StyleA = style(
      color(Colors.highlight)
    )
  }
}
