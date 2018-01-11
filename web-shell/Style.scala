package tacit.webShell

import org.scalajs.dom.html
import scalatags.JsDom.TypedTag

import CssExtensions._

object Style {
  val renderTags: Seq[TypedTag[html.Style]] = {
    val styles = Seq(
      Repl.Style,
      ReplInput.Style,
      ReplOutput.Style,
      TextInput.Style
    )
    styles.map(_.renderTag)
  }
}
