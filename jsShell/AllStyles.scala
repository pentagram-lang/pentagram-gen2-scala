package tacit.jsShell

import org.scalajs.dom.html
import scalatags.JsDom.TypedTag

import CssExtensions._

object AllStyles {
  val renderTags: Seq[TypedTag[html.Style]] = {
    val styles = Seq(
      BodyStyle,
      EchoLine.Style,
      ErrorLine.Style,
      PromptSpan.Style,
      Repl.Style,
      ReplInput.Style,
      ReplOutput.Style,
      RootStyle,
      Shell.Style,
      TextInput.Style,
      WelcomeLines.Style
    )
    styles.map(_.renderTag)
  }
}
