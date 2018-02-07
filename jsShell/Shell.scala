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

import CssSettings.Defaults._

object Shell extends Render {
  def render: RenderComponent = {
    val repl = Repl()

    val root = div(
      Style.root,
      AllStyles.renderTags,
      ScreenEdge().root,
      repl.root
    )
    (root, new Component(_, repl))
  }

  type Root = html.Div

  final class Component(
    val root: Root,
    val repl: Repl.Component
  ) {
    def autofocus(): Unit = repl.autofocus()

    def handleClick(event: dom.MouseEvent): Unit = {
      val _ = event

      val selection = dom.document.getSelection()
      val isSelectionEmpty = (
        selection.anchorNode == selection.focusNode
          && selection.anchorOffset == selection.focusOffset
      )

      if (isSelectionEmpty) {
        // Only change focus when the click didn't result in
        // a selection, to avoid interfering with the user's
        // intention
        autofocus()
      }
    }

    dom.document.addEventListener(
      "click",
      handleClick(_),
      useCapture = false)
  }

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      display.flex,
      flexDirection.column,
      alignItems.center,
      minHeight(100 vh)
    )
  }
}
