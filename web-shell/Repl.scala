package tacit.webShell

import org.scalajs.dom
import org.scalajs.dom.html
import scalacss.ScalatagsCss._
import scalatags.JsDom.all._

import tacit.core.LineParser

import CssSettings.Defaults._

final class Repl(
  val root: html.Div,
  val replInput: ReplInput,
  val replOutput: ReplOutput
) {
  replInput.onSubmit(handleSubmit(_))

  def handleSubmit(event: dom.Event): Unit = {
    event.preventDefault
    replOutput.addResult(LineParser.parse(replInput.value).toString())
  }
}

object Repl {
  def apply(xs: Modifier*): Repl = {
    val replInput = ReplInput()
    val replOutput = ReplOutput()
    val root = div(
      Style.root,
      xs,
      replInput.root,
      replOutput.root
    )
    new Repl(root.render, replInput, replOutput)
  }

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root = style(
      backgroundColor(black),
      color(white)
    )
  }
}
