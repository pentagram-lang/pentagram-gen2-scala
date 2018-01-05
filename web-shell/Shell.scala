package tacit.webShell

import scalatags.JsDom.all._

object Shell {
  val repl = Repl()

  val root =
    div(
      Style.renderTags,
      repl.root
    )

  def render = root.render
}
