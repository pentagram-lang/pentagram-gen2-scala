package tacit.webShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalatags.JsDom.all._

import tacit.core.OutputBlock

import CssSettings.Defaults._

final class ReplOutput(
  val root: html.Div
) {
  @SuppressWarnings(Array("org.wartremover.warts.Recursion"))
  def writeBlock(block: OutputBlock): Unit =
    block match {
      case OutputBlock.ErrorHighlightPrevious(_) =>
        ()
      case OutputBlock.ErrorHighlightNew(_) =>
        ()
      case OutputBlock.ErrorMessage(error) =>
        addResultWithStyle(
          error.message,
          Some(ReplOutput.Style.error))
      case OutputBlock.NormalText(text) =>
        addResult(text)
      case OutputBlock.ValueText(text) =>
        addResult(text)
      case OutputBlock.Multi(blocks) =>
        blocks foreach {
          writeBlock(_)
        }
    }

  def addResult(
    text: String
  ): Unit =
    addResultWithStyle(text, None)

  def addResultWithStyle(
    text: String,
    style: Option[StyleA]
  ): Unit = {
    val _ = root.appendChild(div(text, style).render)
    ()
  }
}

object ReplOutput {
  def apply(xs: Modifier*): ReplOutput = {
    val root = div(
      Style.root,
      xs
    )
    new ReplOutput(root.render)
  }

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style()

    val error: StyleA = style(
      color(red)
    )
  }
}
