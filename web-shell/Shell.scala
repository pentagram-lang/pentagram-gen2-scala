package tacit.webShell

import scalatags.JsDom.all._
import scalacss.ScalatagsCss._

import tacit.core.LineParser
import CssExtensions._

object Shell {
  val parseResult = LineParser.parse("1 1 +")

  val root =
    div(
      Style.renderTag,
      p(
        Style.highlight,
        "Hello scalatags + scalacss world"
      ),
      p(
        s"Parsed: $parseResult"
      )
    )

  def render = root.render
}
