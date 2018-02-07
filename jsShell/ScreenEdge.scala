package tacit.jsShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalatags.JsDom.all.{
  Double2CssNumber => _,
  Int2CssNumber => _,
  _
}

import CssSettings.Defaults._

object ScreenEdge extends Render {
  def render: RenderComponent = {
    val root = div(
      Style.root
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
      zIndex(-1),
      position.fixed,
      top.`0`,
      right.`0`,
      bottom.`0`,
      left.`0`,
      backgroundImage := allGradients,
      willChange := "transform"
    )

    def allGradients: String =
      Seq("top", "right", "bottom", "left")
        .flatMap(oneSideGradients(_))
        .mkString(",")

    def oneSideGradients(to: String): Seq[String] =
      Seq("2em", "4em").map(singleGradient(to, _))

    def singleGradient(to: String, size: String): String =
      s"linear-gradient(to $to, rgba(229, 255, 229, 0.08), transparent $size)"
  }
}
