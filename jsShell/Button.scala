package tacit.jsShell

import org.scalajs.dom.html
import scala.concurrent.duration._
import scala.language.postfixOps
import scalacss.ScalatagsCss._
import scalacss.internal.Macros.Color

import CssSettings.Defaults._
import HtmlSettings.Defaults._

object Button extends RenderSimple[html.Anchor] {
  def renderSimple: RenderTag =
    a(
      Style.root,
      href := "javascript:;"
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    def makeBoxShadow(color: Color): String =
      s"5px 5px 0px -1px ${color.value}"

    val root: StyleA = style(
      transitionDuration(
        200 milliseconds,
        400 milliseconds,
        100 milliseconds),
      transitionProperty := "color, box-shadow, transform",
      boxShadow := makeBoxShadow(Colors.shadow),
      borderStyle.solid,
      borderWidth(3 px),
      borderRadius(4 px),
      backgroundColor(Colors.background),
      paddingTop(0.2 em),
      paddingRight(1.0 ch),
      paddingBottom(0.1 em),
      paddingLeft(0.8 ch),
      fontSize(1.24 em),
      textDecoration := "none",
      &.hover(
        transform := "translate(-2px, 0)",
      ),
      &.active(
        transform := "translate(4px, 4px)",
        boxShadow := "0px 0px 0px -1px transparent"
      ),
      &.hover.active(
        transform := "translate(2px, 4px)"
      )
    )

    val border0: StyleA = style(
      borderColor(Colors.special0.normal),
      svgFill := Colors.special0.normal,
      color(Colors.special0.dark),
      &.hover(
        color(Colors.special0.normal)
      )
    )

    val border1: StyleA = style(
      borderColor(Colors.special1.normal),
      svgFill := Colors.special1.normal,
      color(Colors.special1.dark),
      &.hover(
        color(Colors.special1.normal)
      )
    )

    val border2: StyleA = style(
      borderColor(Colors.special2.normal),
      svgFill := Colors.special2.normal,
      color(Colors.special2.dark),
      &.hover(
        color(Colors.special2.normal)
      )
    )

    val darkShadow: StyleA = style(
      boxShadow := makeBoxShadow(Colors.darkShadow),
    )
  }
}
