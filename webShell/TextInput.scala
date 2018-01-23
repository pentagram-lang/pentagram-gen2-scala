package tacit.webShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalatags.JsDom.all.{
  Double2CssNumber => _,
  Int2CssNumber => _,
  _
}

import CssSettings.Defaults._

object TextInput extends Render {
  def render: RenderComponent = {
    val root = input(
      Style.root,
      `type` := "text"
    )
    (root, new Component(_))
  }

  type Root = html.Input

  final class Component(
    val root: Root
  ) {
    def value: String = root.value

    def value_=(text: String): Unit = root.value = text
  }

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      flexGrow(1),
      backgroundColor.inherit,
      color.inherit,
      fontFamily.inherit,
      fontSize.inherit,
      lineHeight.inherit,
      borderWidth.`0`,
      &.focus - (
        outline.none
      )
    )
  }
}
