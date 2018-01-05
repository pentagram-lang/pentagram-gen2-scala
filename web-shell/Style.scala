package tacit.webShell

import CssExtensions._

object Style {
  val renderTags = {
    val styles = Seq(
      Repl.Style,
      ReplInput.Style,
      ReplOutput.Style,
      TextInput.Style
    )
    styles.map(_.renderTag)
  }
}
