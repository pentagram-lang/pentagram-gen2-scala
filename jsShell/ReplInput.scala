package tacit.jsShell

import org.scalajs.dom
import org.scalajs.dom.html
import scalacss.ScalatagsCss._

import CssSettings.Defaults._
import HtmlSettings.Defaults._

object ReplInput extends Render[html.Form] {
  def render: RenderComponent = {
    val textInput = TextInput()
    val root = form(
      Style.root,
      label(
        Style.label,
        PromptSpan(Style.prompt).root,
        textInput.root)
    )
    (root, new Component(_, textInput))
  }

  final class Component(
    val root: Root,
    val textInput: TextInput.Component
  ) {
    def onSubmit(handler: dom.Event => Unit): Unit =
      root.addEventListener("submit", handler(_))

    def value: String = textInput.value

    def reset(): Unit = textInput.value = ""

    def autofocus(): Unit = textInput.focus()
  }

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      display.flex
    )

    val label: StyleA = style(
      display.flex,
      flexGrow(1)
    )

    val prompt: StyleA = style(
      HighlightGlow.Style.root
    )
  }
}
