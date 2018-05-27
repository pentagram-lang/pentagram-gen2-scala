package tacit.jsShell

import org.scalajs.dom.html
import scala.language.postfixOps
import scalacss.ScalatagsCss._
import scalatags.JsDom.all.{
  Double2CssNumber => _,
  Int2CssNumber => _,
  _
}

import CssSettings.Defaults._

object HomeTitle extends RenderSimple[html.Heading] {
  def renderSimple: RenderTag =
    h1(
      Style.root,
      "Welcome to Tacit!"
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      fontSize(2.2 em),
      fontWeight._400,
      margin.vertical(`0`)
    )
  }
}
