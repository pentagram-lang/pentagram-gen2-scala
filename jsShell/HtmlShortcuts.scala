package tacit.jsShell

import org.scalajs.dom.Element
import scalatags.generic

import HtmlSettings.Defaults._

object HtmlShortcuts {
  val targetBlank: generic.AttrPair[Element, String] =
    target := "_blank"
}
