package tacit.webShell

import org.scalajs.dom.html.Input
import scalacss.ScalatagsCss._
import scalatags.JsDom.all._

import CssSettings.Defaults._

final class TextInput(val element: Input) {
  def value: String = element.value
}

object TextInput {
  def apply(xs: Modifier*): TextInput = {
    val tag = input(
      Style.root,
      `type` := "text",
      xs
    )
    new TextInput(tag.render)
  }

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root = style(
      backgroundColor(black),
      color(white)
    )
  }
}
