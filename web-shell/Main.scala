package tacit.webShell

import org.scalajs.dom

object Main extends App {
  dom.document.body.appendChild(Shell.render)
}
