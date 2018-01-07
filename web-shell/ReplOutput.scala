package tacit.webShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalatags.JsDom.all._

import tacit.core.OutputBlock

import CssSettings.Defaults._

final class ReplOutput(
  val root: html.Div
) {
  def writeBlock(block: OutputBlock): Unit =
    block match {
      case OutputBlock.ErrorHighlightPrevious(error) =>
        ()
      case OutputBlock.ErrorHighlightNew(error) =>
        ()
      case OutputBlock.ErrorMessage(error) =>
        addResult(error.message, Some(ReplOutput.Style.error))
      case OutputBlock.NormalText(text) =>
        addResult(text)
      case OutputBlock.ValueText(text) =>
        addResult(text)
      case OutputBlock.Multi(blocks) =>
        blocks.map(writeBlock)
    }

  def addResult(
    text: String,
    style: Option[StyleA] = None
  ): Unit =
    root.appendChild(div(text, style).render)
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

    val root = style()

    val error = style(
      color(red)
    )
  }
}
