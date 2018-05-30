package tacit.jsShell

import scala.language.postfixOps
import scalacss.ScalatagsCss._
import scalacss.internal.Length

import CssSettings.Defaults._
import HtmlSettings.Defaults._
import HtmlSettings.Defaults.SvgAttrs._
import HtmlSettings.Defaults.SvgTags._

object Mascot extends RenderSimple[org.scalajs.dom.svg.SVG] {
  val sourceWidth: Double = 122
  val sourceHeight: Double = 210

  def renderSimple: RenderTag =
    scalatags.JsDom.svgTags.svg(
      Style.root,
      viewBox := s"0 0 $sourceWidth $sourceHeight",
      path(
        d := Path.outline
      ),
      path(
        Style.fill,
        d := Path.fill
      ),
      path(
        Style.colorFill,
        d := Path.colorFill
      )
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val outputWidth: Length[Double] = 12.0 ch

    val root: StyleA = style(
      marginTop(0.2 em),
      marginRight(1.7 ch),
      width(outputWidth),
      height(outputWidth * sourceHeight / sourceWidth),
      svgFill := "none",
      svgStroke(Colors.normal),
      svgStrokeWidth := "3"
    )

    val fill: StyleA = style(
      svgFill := Colors.normal,
      svgStroke.initial
    )

    val colorFill: StyleA = style(
      svgFill := Colors.special0.pale
    )
  }

  object Path {
    val outline: String =
      "m 37.856282,166.51829 c 0,0 -13.179859,2.10616 -12.506254,9.59816 0.674019,7.49211 11.330939,7.44308 11.330939,7.44308 M 4.5734426,156.05576 c 2.0821521,4.72332 32.5738674,-6.26126 32.5738674,-6.26126 5.451243,24.89185 -4.458106,48.94483 -0.79363,53.22094 3.663771,4.27603 12.861564,-12.05182 16.684249,-21.91452 l 27.012413,-0.78329 c 0,0 6.456481,29.45126 10.725595,26.21922 4.268997,-3.23199 4.415278,-37.14674 -1.986278,-55.1775 0,0 29.043931,-23.67352 28.998691,-30.52384 -0.047,-6.85039 -24.329972,12.77372 -37.934583,9.3642 0,0 15.48526,-13.65302 16.155693,-26.7908 C 96.682774,90.21503 88.257698,76.889649 80.093109,74.010454 78.200126,50.825907 115.25865,14.748372 89.318279,5.6094105 63.378017,-3.5295862 62.283069,50.909294 64.612026,69.788593 60.851377,69.325239 54.745032,69.719173 51.94815,70.819311 35.835755,38.600015 59.190684,1.8283144 33.001039,3.2593476 6.8113943,4.69033 18.739156,66.169863 37.502443,78.464481 c 0,0 -15.011865,13.506341 -11.863081,27.204559 3.148612,13.69826 19.827688,26.35686 19.827688,26.35686 -9.721354,2.65077 -42.9781135,19.29819 -40.8927886,24.02927 z"

    val fill: String =
      "m 71.275485,120.23198 -11.240906,-1.86511 c 0,0 0.365428,7.05862 4.301307,7.53018 3.936306,0.47146 6.939599,-5.66507 6.939599,-5.66507 z m 7.588937,-24.859281 c 6.255112,-0.880588 3.835329,12.629621 2.026174,15.082841 l -2.812302,-0.29912 C 77.226719,107.69035 74.024855,96.053722 78.86393,95.372504 Z m -27.480608,-0.44086 c 6.1319,-0.164191 4.63253,12.350531 3.210411,15.408701 l -3.769223,0.54143 c -1.16057,-1.93012 -4.833096,-15.805517 0.55928,-15.950131 z"

    val colorFill: String =
      "m 67.031038,169.10284 c -7.84535,-0.52698 -18.364034,-12.04181 -11.076438,-18.76238 4.399944,-4.05754 9.711544,2.46927 9.711544,2.46927 0,0 4.086876,-6.19882 8.571358,-3.2937 8.622178,5.58582 -0.889214,16.81825 -7.206464,19.58681 z"
  }
}
