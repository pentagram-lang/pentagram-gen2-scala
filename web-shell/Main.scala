package tacit.webShell

import org.scalajs.dom

object Main extends App {
  val _ = dom.document.body.appendChild(Shell.render)
}
