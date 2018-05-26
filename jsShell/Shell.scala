package tacit.jsShell

import org.scalajs.dom.html

object Shell {
  trait Component {
    val root: html.Div
    def autofocus(): Unit = ()
  }
}
