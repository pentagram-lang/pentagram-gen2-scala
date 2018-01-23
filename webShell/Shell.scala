package tacit.webShell

import org.scalajs.dom.html
import scala.language.postfixOps
import scalacss.ScalatagsCss._
import scalatags.JsDom.all.{
  Double2CssNumber => _,
  Int2CssNumber => _,
  _
}

import CssSettings.Defaults._

object Shell extends Render {
  def render: RenderComponent = {
    val repl = Repl()

    val root = div(
      Style.root,
      AllStyles.renderTags,
      repl.root
    )
    (root, new Component(_))
  }

  type Root = html.Div

  final class Component(
    val root: Root
  )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      display.flex,
      flexDirection.column,
      alignItems.center,
      minHeight(100 vh),
      backgroundColor(black)
    )
  }
}
