package tacit.jsShell

import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalatags.JsDom.all.{
  Double2CssNumber => _,
  Int2CssNumber => _,
  _
}

import tacit.core.OutputBlock

import CssSettings.Defaults._

object ReplOutput extends Render {
  def render: RenderComponent = {
    val root = div(
      Style.root,
      WelcomeLines().root
    )
    (root, new Component(_))
  }

  type Root = html.Div

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
          addResultWithStyle(error.message, Some(Style.error))
        case OutputBlock.ValueText(text) =>
          addResult(text)
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

    private def addResult(
      text: String
    ): Unit =
      addResultWithStyle(text, None)

    private def addResultWithStyle(
      text: String,
      style: Option[StyleA]
    ): Unit = {
      val _ = root.appendChild(div(text, style).render)
    }
  }

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style()

    val error: StyleA = style(
      color(Colors.error)
    )
  }
}
