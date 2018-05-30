package tacit.jsShell

import org.scalajs.dom.html
import scala.language.postfixOps
import scalacss.ScalatagsCss._

import CssSettings.Defaults._
import HtmlSettings.Defaults._

object WelcomeLines extends RenderSimple[html.Div] {
  def renderSimple: RenderTag =
    div(
      Style.root,
      h1(
        Style.header,
        "Welcome to ",
        HighlightGlow("Tacit").root,
        ", a new programming language"
      ),
      h2(
        Style.note,
        "(goals: ergonomics, determinism, efficiency)"
      ),
      tag("nav")(
        Style.nav,
        a(
          Style.navLink,
          href := "https://github.com/tacit-lang/tacit",
          "GitHub (source)"
        ),
        a(
          Style.navLink,
          href := "https://example.org",
          "Medium (blog)"
        )
      ),
      tag("nav")(
        Style.nav,
        a(
          Style.navLink,
          href := "https://example.org",
          "Gitter (chat)"
        ),
        a(
          Style.navLink,
          href := "https://example.org",
          "Discourse (forum)"
        )
      ),
      div(
        "Get started with a simple expression: ",
        span(Style.highlight, "1 1 +")
      )
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style()

    val header: StyleA = style(
      margin.`0`,
      fontSize.inherit,
      fontWeight.inherit
    )

    val highlight: StyleA = style(
      color(Colors.Repl.highlight)
    )

    val note: StyleA = style(
      marginTop.`0`,
      marginRight.`0`,
      marginBottom.`0`,
      marginLeft(2 ch),
      fontSize.inherit,
      fontWeight.inherit,
      fontStyle.italic
    )

    val nav: StyleA = style(
      marginLeft(1 ch),
      fontStyle.italic,
      color(Colors.Repl.link)
    )

    val navLink: StyleA = style(
      marginLeft(1 ch),
      color.inherit,
      &.selection(
        color(Colors.Repl.highlight)
      )
    )
  }
}
