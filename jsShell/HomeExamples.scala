package tacit.jsShell

import org.scalajs.dom.html
import scala.language.postfixOps
import scalacss.ScalatagsCss._

import CssSettings.Defaults._
import HtmlSettings.Defaults._
import HtmlSettings.Defaults.Tags2._

object HomeExamples extends RenderSimple[html.Element] {
  def renderSimple: RenderTag =
    section(
      Style.root,
      h2(
        Style.header,
        "Examples"
      ),
      pre(
        Style.code,
        """-- Hello world
          |
          |ask-name =/
          |  'What is your name?' ask
          |
          |hello =/ name /=
          |  'Hello [name]!' say
          |
          |friend = ask-name
          |'World' hello
          |friend hello""".stripMargin
      ),
      div(
        Style.buttonGroup,
        Button(
          Button.Style.border0,
          Style.button,
          "◀"
        ).root,
        Button(
          Button.Style.border0,
          Style.button,
          "Open"
        ).root,
        Button(
          Button.Style.border0,
          Style.button,
          "▶"
        ).root
      )
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      display.flex,
      flexDirection.column,
      marginTop(3.5 em),
      marginLeft(6 ch)
    )

    val header: StyleA = style(
      marginTop.`0`,
      marginBottom(0.3 em),
      marginLeft(-0.2 ch),
      fontSize(1.5 em),
      fontWeight._400,
      color(Colors.special0.dark)
    )

    val code: StyleA = style(
      marginTop.`0`,
      marginBottom(1.1 em),
      borderStyle.solid,
      borderWidth(2 px),
      borderRadius(4 px),
      borderColor(Colors.special0.normal),
      padding.vertical(1.2 em),
      padding.horizontal(2.4 ch),
      color(Colors.special0.midnight),
      fontSize(0.95 em)
    )

    val buttonGroup: StyleA = style(
      display.flex,
      flexDirection.row
    )

    val button: StyleA = style(
      marginRight(2 ch),
      borderWidth(2 px),
      fontSize(1.0 em)
    )
  }
}
