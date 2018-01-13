package tacit.webShell

import org.scalajs.dom.html
import scalatags.JsDom.all._

object Shell {
  private val repl = Repl()

  private val root =
    div(
      Style.renderTags,
      repl.root
    )

  def render: html.Div = root.render
}
