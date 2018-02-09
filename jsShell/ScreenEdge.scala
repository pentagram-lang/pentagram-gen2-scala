package tacit.jsShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalacss.internal.Macros.Color
import scalatags.JsDom.all.{
  Double2CssNumber => _,
  Int2CssNumber => _,
  _
}

import CssSettings.Defaults._

object ScreenEdge extends RenderSimple[html.Div] {
  def renderSimple: RenderTag =
    div(
      Style.root
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
        allSideGradients(Colors.background, _))

    def highlightGradients: Seq[String] =
      Seq("2em", "4em").flatMap(
        allSideGradients(Colors.edgeHighlight, _))

    def allSideGradients(
      startColor: Color,
      stopSize: String
    ): Seq[String] =
      Seq("top", "right", "bottom", "left")
        .map(singleGradient(_, startColor, stopSize))

    def singleGradient(
      side: String,
      startColor: Color,
      stopSize: String
    ): String =
      s"linear-gradient(to $side, ${startColor.value}, transparent $stopSize)"
  }
}
