package tacit.webShell

import CssExtensions._

object Style {
  val renderTags = {
    val styles = Seq(
      TextInput.Style
    )
    styles.map(_.renderTag)
  }
}
