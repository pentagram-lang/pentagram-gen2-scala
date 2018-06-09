package tacit.jsShell

import scala.scalajs.js.Dynamic

object Mode {
  @SuppressWarnings(
    Array("org.wartremover.warts.AsInstanceOf"))
  private val modeList: Array[String] = {
    val dynamicHtmlElement =
      Dynamic.global.document.documentElement
    val dynamicMode = dynamicHtmlElement.dataset.mode
    dynamicMode.asInstanceOf[String].split(",")
  }

  def isRepl: Boolean =
    modeList.contains("repl")

  def isDev: Boolean =
    modeList.contains("dev")
}
