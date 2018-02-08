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
      backgroundImage := allGradients.mkString(","),
      willChange := "transform"
    )

    def allGradients: Seq[String] =
      highlightGradients ++ baseGradients

    def baseGradients: Seq[String] =
      Seq("1em", "3em").flatMap(
        allSideGradients("#1a1a1a", _))

    def highlightGradients: Seq[String] =
      Seq("2em", "4em").flatMap(
        allSideGradients("rgba(229, 255, 229, 0.08)", _))

    def allSideGradients(
      startColor: String,
      stopSize: String
    ): Seq[String] =
      Seq("top", "right", "bottom", "left")
        .map(singleGradient(_, startColor, stopSize))

    def singleGradient(
      side: String,
      startColor: String,
      stopSize: String
    ): String =
      s"linear-gradient(to $side, $startColor, transparent $stopSize)"
  }
}
