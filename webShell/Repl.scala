package tacit.webShell

import org.scalajs.dom
import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalatags.JsDom.all._

import tacit.core.ReadEvalPrintChain

import CssSettings.Defaults._

object Repl extends Render {
  def render: RenderComponent = {
    val replInput = ReplInput()
    val replOutput = ReplOutput()
    val root = div(
      Style.root,
      replInput.root,
      replOutput.root
    )
    (root, new Component(_, replInput, replOutput))
  }

  type Root = html.Div

  final class Component(
    val root: Root,
    val replInput: ReplInput.Component,
    val replOutput: ReplOutput.Component
  ) {
    replInput.onSubmit(handleSubmit(_))

    def handleSubmit(event: dom.Event): Unit = {
      event.preventDefault()
      val outputBlock =
        ReadEvalPrintChain.readEvalPrint(replInput.value)
      replOutput.writeBlock(outputBlock)
      replInput.reset()
    }
  }

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      backgroundColor(black),
      color(white)
    )
  }
}
