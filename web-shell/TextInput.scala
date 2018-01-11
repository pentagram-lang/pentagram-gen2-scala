package tacit.webShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalatags.JsDom.all._

import CssSettings.Defaults._

final class TextInput(val root: html.Input) {
  def value: String = root.value

  def value_=(text: String): Unit = root.value = text
}

object TextInput {
  def apply(xs: Modifier*): TextInput = {
    val root = input(
      Style.root,
      `type` := "text",
      xs
    )
    new TextInput(root.render)
  }

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      backgroundColor(black),
      color(white)
    )
  }
}
