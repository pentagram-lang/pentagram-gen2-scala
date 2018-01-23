package tacit.webShell

import org.scalajs.dom

object Main extends App {
  private def run(): Unit = {
    val bodyClassName = BodyStyle.root.className.value
    val shell = Shell().root
    val body = dom.document.body

    body.className = bodyClassName
    val _ = body.appendChild(shell)
  }

  run()
}
