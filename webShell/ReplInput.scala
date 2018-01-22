package tacit.webShell

import org.scalajs.dom
import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalatags.JsDom.all._

import CssSettings.Defaults._

object ReplInput extends Render {
  def render: RenderComponent = {
    val textInput = TextInput()
    val root = form(
      Style.root,
      textInput.root
    )
    (root, new Component(_, textInput))
  }

  type Root = html.Form

  final class Component(
    val root: Root,
    val textInput: TextInput.Component
  ) {
    def onSubmit(handler: dom.Event => Unit): Unit =
      root.onsubmit = handler

    def value: String = textInput.value

    def reset(): Unit = textInput.value = ""
  }

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      backgroundColor(black),
      color(white)
    )
  }
}
