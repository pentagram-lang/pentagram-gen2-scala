package tacit.jsShell

import org.scalajs.dom.html
import scala.language.postfixOps
import scalacss.ScalatagsCss._

import CssSettings.Defaults._
import HtmlSettings.Defaults._
import HtmlSettings.Defaults.Tags2._

object HomeStart extends RenderSimple[html.Element] {
  def renderSimple: RenderTag =
    section(
      Style.root,
      h2(
        Style.header,
        "Start here"
      ),
      div(
        Style.columns,
        div(
          Style.actions,
          Button(
            Button.Style.border2,
            Button.Style.darkShadow,
            Style.buttonMargin,
            Icon.Go().root,
            "Interactive tutorial"
          ).root,
          Button(
            Button.Style.border2,
            Button.Style.darkShadow,
            Style.buttonMargin,
            Icon.Video().root,
            "Video introduction"
          ).root,
          Button(
            Button.Style.border2,
            Button.Style.darkShadow,
            Icon.Pamphlet().root,
            "Getting started guide"
          ).root
        ),
        p(
          Style.status,
          strong("Status:"),
          br(),
          em("Almost all features not yet implemented!")
        )
      )
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      display.flex,
      flexDirection.column,
      marginLeft(-1.0 ch),
      backgroundColor(Colors.special2.pale),
      paddingTop(1.5 em),
      paddingBottom(1.8 em)
    )

    val columns: StyleA = style(
      display.flex,
      flexDirection.row
    )

    val actions: StyleA = style(
      display.flex,
      flexDirection.column,
      marginRight(7 ch),
      marginLeft(-0.2 ch)
    )

    val header: StyleA = style(
      marginTop.`0`,
      marginBottom(0.6 em),
      color(Colors.special2.normal),
      fontSize(2.3 em),
      fontWeight._700,
      lineHeight(1)
    )

    val buttonMargin: StyleA = style(
      marginBottom(1 em)
    )

    val status: StyleA = style(
      marginTop.auto,
      marginBottom.auto,
      borderStyle.solid,
      borderWidth(2 px),
      borderRadius(4 px),
      borderColor(Colors.special2.light),
      paddingTop(0.5 em),
      padding.horizontal(1.5 ch),
      paddingBottom(0.45 em),
      maxWidth(20 ch),
      color(Colors.special2.dark),
      fontSize(1.2 em)
    )
  }
}
