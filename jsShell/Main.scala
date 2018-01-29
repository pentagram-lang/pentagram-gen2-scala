package tacit.jsShell

import org.scalajs.dom

object Main extends App {
  private def run(): Unit = {
    val bodyClassName = BodyStyle.root.className.value
    val rootClassName = RootStyle.root.className.value
    val shell = Shell()
    val body = dom.document.body
    val root = body.parentElement

    body.className = bodyClassName
    root.className = rootClassName
    val _ = body.appendChild(shell.root)
    shell.autofocus()
  }

  run()
}
