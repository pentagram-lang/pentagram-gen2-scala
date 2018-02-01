package tacit.jsShell

import org.scalajs.dom.html
import scala.language.postfixOps
import scalacss.ScalatagsCss._
import scalatags.JsDom.all.{
  Double2CssNumber => _,
  Int2CssNumber => _,
  _
}

import CssSettings.Defaults._

object WelcomeLines extends Render {
  def render: RenderComponent = {
    val root = div(
      Style.root,
      h1(
        Style.header,
        "Welcome to ",
        span(Style.highlight, "Tacit"),
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
        "To get started, try a simple expression like ",
        span(Style.highlight, "1 1 +")
      ),
      div(
        "----------------------------------------------------"
      )
    )
    (root, new Component(_))
  }

  type Root = html.Div

  final class Component(
    val root: Root
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
      color(c"#d3a7ff")
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
      color(c"#9069b3")
    )

    val navLink: StyleA = style(
      marginLeft(1 ch),
      color.inherit
    )
  }
}
