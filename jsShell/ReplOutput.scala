package tacit.jsShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._

import tacit.core.OutputBlock

import CssSettings.Defaults._
import HtmlSettings.Defaults._

object ReplOutput extends Render[html.Div] {
  def render: RenderComponent = {
    val root = div(
      Style.root,
      WelcomeLines().root
    )
    (root, new Component(_))
  }

  final class Component(
    val root: Root
  ) {
    def echoInput(input: String): Unit =
      addLine(EchoLine(input).root)

    @SuppressWarnings(
      Array("org.wartremover.warts.Recursion"))
    def writeBlock(block: OutputBlock): Unit =
      block match {
        case OutputBlock.ErrorHighlightPrevious(_) =>
          ()
        case OutputBlock.ErrorHighlightNew(_) =>
          ()
        case OutputBlock.ErrorMessage(error) =>
          addLine(ErrorLine(error.message).root)
        case OutputBlock.ValueText(text) =>
          addLine(ValueLine(text).root)
        case OutputBlock.Multi(blocks) =>
          blocks foreach {
            writeBlock(_)
          }
      }

    private def addLine(
      line: html.Div
    ): Unit = {
      val _ = root.appendChild(line)
    }
  }

  object Style extends StyleSheet.Inline {
    val root: StyleA = style()
  }
}
