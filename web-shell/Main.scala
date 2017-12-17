package tacit.webShell

import org.scalajs.dom

import tacit.core.LineParser

object Application {
  def main(args: Array[String]): Unit = {
    val parseResult = LineParser.parse("1 1 +")
    dom.window.alert(s"Hi from Scala-js-dom: $parseResult")
  }
}
