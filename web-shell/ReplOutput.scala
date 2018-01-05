package tacit.webShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalatags.JsDom.all._

import CssSettings.Defaults._

final class ReplOutput(
  val root: html.Div
) {
  def addResult(text: String): Unit = {
    root.appendChild(div(text).render)
  }
}

object ReplOutput {
  def apply(xs: Modifier*): ReplOutput = {
    val root = div(
      Style.root
    )
    new ReplOutput(root.render)
  }

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root = style(
      backgroundColor(black),
      color(white)
    )
  }
}
