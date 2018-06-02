package tacit.jsShell

import org.scalajs.dom.html

import CssExtensions._

object AllStyles {
  val renderTags: Seq[html.Style] = {
    val styles = Seq(
      BodyStyle,
      Button.Style,
      EchoLine.Style,
      ErrorLine.Style,
      GlobalStyle,
      HighlightGlow.Style,
      HomeIntro.Style,
      HomeNav.Style,
      HomeShell.Style,
      HomeStart.Style,
      HomeTitle.Style,
      Icon.Style,
      Mascot.Style,
      PromptSpan.Style,
      Repl.Style,
      ReplInput.Style,
      ReplOutput.Style,
      ReplShell.Style,
      RootStyle,
      TextInput.Style,
      ValueLine.Style,
      WelcomeLines.Style
    )
    styles.map(_.renderTag)
  }
}
