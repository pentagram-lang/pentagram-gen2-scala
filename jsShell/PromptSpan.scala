package tacit.jsShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalatags.JsDom.all.{
  Double2CssNumber => _,
  Int2CssNumber => _,
  _
}

import CssSettings.Defaults._

object PromptSpan extends Render {
  def render: RenderComponent = {
    val root = span(
      Style.root,
      "(repl)> "
    )
    (root, new Component(_))
  }

  type Root = html.Span

  final class Component(
    val root: Root
  )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      color(Colors.highlight),
      whiteSpace.pre
    )
  }
}
