package tacit.jsShell

import org.scalajs.dom.html
import scalatags.JsDom.TypedTag

import CssExtensions._

object AllStyles {
  val renderTags: Seq[TypedTag[html.Style]] = {
    val styles = Seq(
      BodyStyle,
      Repl.Style,
      ReplInput.Style,
      ReplOutput.Style,
      Shell.Style,
      TextInput.Style
    )
    styles.map(_.renderTag)
  }
}
