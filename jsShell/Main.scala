package tacit.jsShell

import org.scalajs.dom.document.body
import org.scalajs.dom.raw.Node

object Main extends App {
  private def run(): Unit = {
    val bodyClassName = BodyStyle.root.className.value
    val rootClassName = RootStyle.root.className.value
    val shell = loadShell()
    val root = body.parentElement

    body.className = bodyClassName
    root.className = rootClassName
    AllStyles.renderTags.foreach { appendToBody(_) }
    appendToBody(shell.root)
    shell.autofocus()
  }

  private def appendToBody(node: Node): Unit = {
    val _ = body.appendChild(node)
  }

  private def loadShell(): Shell.Component =
    if (Mode.isRepl) {
      ReplShell()
    } else {
      HomeShell()
    }

  run()
}
