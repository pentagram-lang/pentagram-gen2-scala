package tacit.jsShell

import org.scalajs.dom.html
import scala.language.postfixOps
import scalacss.ScalatagsCss._

import CssSettings.Defaults._
import HtmlSettings.Defaults._
import HtmlSettings.Defaults.Tags2._

object HomeNav extends RenderSimple[html.Element] {
  def renderSimple: RenderTag =
    nav(
      Style.root,
      div(
        Style.verticalRule
      ),
      div(
        Style.container,
        Button(
          Button.Style.border0,
          Style.buttonMarginSmall,
          Icon.Install().root,
          "Install now"
        ).root,
        p(
          Style.installInfo,
          "Version 0.0.0 (February 31, 2018)",
          br(),
          "Free and open source (MIT License)"
        ),
        div(
          Style.horizontalRule
        ),
        Button(
          Button.Style.border1,
          Style.buttonMarginLarge,
          Icon.Search().root,
          "Browse docs"
        ).root,
        Button(
          Button.Style.border1,
          Style.buttonMarginSmall,
          Icon.FriendlyPerson().root,
          "Get help"
        ).root,
        div(
          Style.horizontalRule
        ),
        Button(
          Button.Style.border2,
          Style.buttonMarginLarge,
          href := "repl-dev.html",
          target := "_blank",
          Icon.Cycle().root,
          "Launch REPL"
        ).root,
        Button(
          Button.Style.border2,
          Icon.DevEnv().root,
          "Run web IDE"
        ).root
      )
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      display.flex,
      flexDirection.row,
      marginTop(1 em),
      marginBottom.auto
    )

    val container: StyleA = style(
      display.flex,
      flexDirection.column
    )

    val buttonMarginLarge: StyleA = style(
      marginBottom(1 em)
    )

    val buttonMarginSmall: StyleA = style(
      marginBottom(0.2 em)
    )

    val installInfo: StyleA = style(
      marginTop(1.2 em),
      marginBottom(-0.1 em),
      fontSize(0.65 em),
      fontStyle.italic,
      color(Colors.special0.dark)
    )

    val verticalRule: StyleA = style(
      margin.vertical(0.5 em),
      marginLeft(2.4 ch),
      borderLeftStyle.solid,
      borderLeftWidth(2 px),
      borderColor(Colors.shadow),
      paddingLeft(2.4 ch)
    )

    val horizontalRule: StyleA = style(
      margin.horizontal(0.8 ch),
      marginBottom(1.1 em),
      borderBottomStyle.solid,
      borderBottomWidth(2 px),
      borderColor(Colors.shadow),
      paddingBottom(1.1 em)
    )
  }
}
