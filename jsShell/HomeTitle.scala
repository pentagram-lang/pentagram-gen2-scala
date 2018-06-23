package tacit.jsShell

import org.scalajs.dom.html
import scala.language.postfixOps
import scalacss.ScalatagsCss._

import CssSettings.Defaults._
import HtmlSettings.Defaults._

object HomeTitle extends RenderSimple[html.Heading] {
  def renderSimple: RenderTag =
    h1(
      Style.root,
      "Welcome to Tacit! (PR1)"
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      marginTop.`0`,
      marginBottom(0.4 em),
      fontSize(2.6 em),
      fontWeight._400,
      lineHeight(1)
    )
  }
}
