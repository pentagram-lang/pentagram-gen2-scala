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
      PromptSpan().root
    )

  object Style extends StyleSheet.Inline {
    val root: StyleA = style()
  }
}
