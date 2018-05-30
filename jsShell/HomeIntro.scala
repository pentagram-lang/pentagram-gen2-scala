package tacit.jsShell

import org.scalajs.dom.html
import scala.language.postfixOps
import scalacss.ScalatagsCss._

import CssSettings.Defaults._
import HtmlSettings.Defaults._

object HomeIntro extends RenderSimple[html.Div] {
  def renderSimple: RenderTag =
    div(
      Style.root,
      p(
        Style.tagline,
        "A friendly programming language that uses ",
        strong("ergonomics"),
        ", ",
        strong("determinism"),
        ", and ",
        strong("efficiency"),
        " to help you express your creativity."
      ),
      p(
        Style.info,
        Style.info0,
        "With powerful features like ",
        strong("significant whitespace"),
        " and ",
        strong("functional programming"),
        ", the Tacit language works together with",
        " you and helps you out at every step."
      ),
      p(
        Style.info,
        Style.info1,
        "The Tacit community is here to help too!",
        " We know how tough coding can be, and we do our",
        " best to make this a safe space where ",
        strong("kindness"),
        " and ",
        strong("empathy"),
        " are the norm.",
      )
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      display.flex,
      flexDirection.column
    )

    val tagline: StyleA = style(
      fontSize(1.08 em),
      marginTop.`0`,
      marginBottom(0.8 em)
    )

    val info: StyleA = style(
      marginTop.`0`,
      padding.vertical(0.8 em),
      padding.horizontal(2 ch),
      borderStyle.solid,
      borderWidth(2 px)
    )

    val info0: StyleA = style(
      borderColor(Colors.special0.light),
      marginBottom(1.1 em)
    )

    val info1: StyleA = style(
      borderColor(Colors.special1.light),
      marginBottom.`0`
    )
  }
}
