package tacit.webShell

import org.scalajs.dom

object Main extends App {
  private def run(): Unit = {
    val _ = dom.document.body.appendChild(Shell.render)
  }

  run()
}
