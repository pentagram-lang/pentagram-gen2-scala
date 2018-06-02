package tacit.jsShell

import org.scalajs.dom.html
import scala.language.postfixOps
import scalacss.ScalatagsCss._
import scalacss.internal.Length

import CssSettings.Defaults._
import HtmlSettings.Defaults._
import HtmlSettings.Defaults.Tags2._

object HomeShell extends Render[html.Div] {
  def render: RenderComponent = {
    val root = div(
      Style.root,
      div(
        Style.top,
        Mascot().root,
        main(
          Style.main,
          HomeTitle().root,
          HomeIntro().root
        ),
        HomeNav().root
      ),
      HomeStart(
        Style.start
      ).root
    )
    (root, new Component(_))
  }

  final class Component(
    val root: Root
  ) extends Shell.Component {}

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val horizontalPadding: Length[Double] = 3.0 ch
    val fullLeftPadding: Length[Double] =
      (horizontalPadding.n + Mascot.Style.outputWidth.n + Mascot.Style.rightMargin.n) ch

    val root: StyleA = style(
      display.flex,
      flexDirection.column,
      padding.vertical(2.4 em)
    )

    val top: StyleA = style(
      display.flex,
      flexDirection.row,
      padding.horizontal(horizontalPadding)
    )

    val main: StyleA = style(
      display.flex,
      flexDirection.column,
      maxWidth(45 ch)
    )

    val start: StyleA = style(
      paddingRight(horizontalPadding),
      paddingLeft(fullLeftPadding)
    )
  }
}
