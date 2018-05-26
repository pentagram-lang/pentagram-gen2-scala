package tacit.jsShell

import org.scalajs.dom.document.body
import scala.scalajs.js.Dynamic

object Main extends App {
  private def run(): Unit = {
    val bodyClassName = BodyStyle.root.className.value
    val rootClassName = RootStyle.root.className.value
    val shell = loadShell()
    val root = body.parentElement

    body.className = bodyClassName
    root.className = rootClassName
    val _ = body.appendChild(shell.root)
    shell.autofocus()
  }

  private def loadShell(): Shell.Component = {
    val mode = getMode()
    if (mode == "repl") {
      ReplShell()
    } else {
      HomeShell()
    }
  }

  @SuppressWarnings(
    Array("org.wartremover.warts.AsInstanceOf"))
  private def getMode(): String = {
    val dynamicHtmlElement =
      Dynamic.global.document.documentElement
    val mode = dynamicHtmlElement.dataset.mode
    mode.asInstanceOf[String]
  }

  run()
}
