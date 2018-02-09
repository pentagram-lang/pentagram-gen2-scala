package tacit.jsShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalatags.JsDom.all.{
  Double2CssNumber => _,
  Int2CssNumber => _,
  _
}

import CssSettings.Defaults._

object TextInput extends Render[html.Input] {
  def render: RenderComponent = {
    val root = input(
      Style.root,
      `type` := "text",
      autocomplete := "off",
      attr("autocorrect") := "off",
      attr("autocapitalize") := "off",
      spellcheck := "false"
    )
    (root, new Component(_))
  }

  final class Component(
    val root: Root
  ) {
    def value: String = root.value

    def value_=(text: String): Unit = root.value = text

    def focus(): Unit = root.focus()
  }

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      flexGrow(1),
      borderWidth.`0`,
      padding.`0`,
      backgroundColor.inherit,
      color.inherit,
      fontFamily.inherit,
      fontSize.inherit,
      fontWeight.inherit,
      lineHeight.inherit,
      &.focus(
        outline.none
      )
    )
  }
}
