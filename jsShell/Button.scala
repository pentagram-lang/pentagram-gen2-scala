package tacit.jsShell

import org.scalajs.dom.html
import scala.language.postfixOps
import scalacss.ScalatagsCss._

import CssSettings.Defaults._
import HtmlSettings.Defaults._

object Button extends RenderSimple[html.Anchor] {
  def renderSimple: RenderTag =
    a(
      Style.root,
      href := "#"
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      boxShadow := s"5px 5px 0px -1px ${Colors.shadow.value}",
      borderStyle.solid,
      borderWidth(3 px),
      borderRadius(4 px),
      padding.vertical(0.4 em),
      padding.horizontal(1.5 ch),
      fontSize(1.08 em),
      textDecoration := "none"
    )

    val border0: StyleA = style(
      borderColor(Colors.special0.normal),
      color(Colors.special0.dark)
    )

    val border1: StyleA = style(
      borderColor(Colors.special1.normal),
      color(Colors.special1.dark)
    )

    val border2: StyleA = style(
      borderColor(Colors.special2.normal),
      color(Colors.special2.dark)
    )
  }
}
