package tacit.webShell

import org.scalajs.dom
import org.scalajs.dom.html
import scala.language.postfixOps
import scalacss.ScalatagsCss._
import scalatags.JsDom.all.{
  Double2CssNumber => _,
  Int2CssNumber => _,
  _
}

import CssSettings.Defaults._

object ReplInput extends Render {
  def render: RenderComponent = {
    val textInput = TextInput(Style.input)
    val root = form(
      Style.root,
      label(Style.label, "(repl)>", textInput.root)
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

    def autofocus(): Unit = textInput.focus()
  }

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      display.flex
    )

    val label: StyleA = style(
      display.flex,
      flexGrow(1)
    )

    val input: StyleA = style(
      marginLeft(1 ex)
    )
  }
}
