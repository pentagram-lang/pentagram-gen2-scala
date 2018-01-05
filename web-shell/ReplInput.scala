package tacit.webShell

import org.scalajs.dom
import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalatags.JsDom.all._

import CssSettings.Defaults._

final class ReplInput(
  val root: html.Form,
  val textInput: TextInput
) {
  def onSubmit(handler: dom.Event => Unit): Unit =
    root.onsubmit = handler

  def value = textInput.value
}

object ReplInput {
  def apply(xs: Modifier*): ReplInput = {
    val textInput = TextInput()
    val root = form(
      Style.root,
      textInput.root
    )
    new ReplInput(root.render, textInput)
  }

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root = style(
      backgroundColor(black),
      color(white)
    )
  }
}
