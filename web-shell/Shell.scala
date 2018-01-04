package tacit.webShell

import scalatags.JsDom.all._
import org.scalajs.dom.Event

import tacit.core.LineParser

object Shell {
  val shellInput = TextInput()

  val shellOutput = p().render

  val root =
    div(
      Style.renderTags,
      form(
        onsubmit := { event: Event =>
          event.preventDefault
          println(s"Submit ${shellInput.value}")
          shellOutput.textContent = LineParser.parse(shellInput.value).toString()
        },
        shellInput.element
      ),
      shellOutput
    )

  def render = root.render
}
