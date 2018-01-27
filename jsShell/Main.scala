package tacit.jsShell

import org.scalajs.dom

object Main extends App {
  private def run(): Unit = {
    val bodyClassName = BodyStyle.root.className.value
    val shell = Shell()
    val body = dom.document.body

    body.className = bodyClassName
    val _ = body.appendChild(shell.root)
    shell.autofocus()
  }

  run()
}
