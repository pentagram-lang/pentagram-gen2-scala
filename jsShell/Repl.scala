package tacit.jsShell

import org.scalajs.dom
import org.scalajs.dom.html
import scala.language.postfixOps
import scalacss.ScalatagsCss._
import scalatags.JsDom.all.{
  Double2CssNumber => _,
  Int2CssNumber => _,
  _
}

import tacit.core.ReadEvalPrintChain

import CssSettings.Defaults._

object Repl extends Render {
  def render: RenderComponent = {
    val replOutput = ReplOutput()
    val replInput = ReplInput()
    val root = div(
      Style.root,
      replOutput.root,
      replInput.root
    )
    (root, new Component(_, replOutput, replInput))
  }

  type Root = html.Div

  final class Component(
    val root: Root,
    val replOutput: ReplOutput.Component,
    val replInput: ReplInput.Component
  ) {
    def handleSubmit(event: dom.Event): Unit = {
      event.preventDefault()
      val input = replInput.value
      replOutput.echoInput(ReplInput.Prompt, input)
      val outputBlock =
        ReadEvalPrintChain.readEvalPrint(input)
      replOutput.writeBlock(outputBlock)
      replInput.reset()
    }

    def autofocus(): Unit = replInput.autofocus()

    replInput.onSubmit(handleSubmit(_))
  }

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      display.flex,
      flexDirection.column,
      padding(15 vh, `0`),
      width(60 ex),
      color(white),
      fontSize(18 px),
      fontFamily.attr := "monospace",
      lineHeight(1.8 em)
    )
  }
}
