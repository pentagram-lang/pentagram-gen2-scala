package tacit.jsShell

import scala.language.postfixOps
import scalacss.internal.Attr
import scalacss.internal.FontFace
import scalacss.internal.FontFace._
import scalacss.internal.ValueT.TypedAttr_Color

import CssSettings.Defaults._

object RootStyle extends StyleSheet.Inline {
  import RootStyle.dsl._

  val root: StyleA = style(
    backgroundColor(Colors.background),
    fontFamily.attr := "'Hack', 'DejaVu Sans Mono', 'Consolas', monospace",
    fontWeight._400,
    fontSize.large,
    color(Colors.normal),
    caretColor(Colors.highlight),
    lineHeight(2.0 em)
  )

  object caretColor extends TypedAttr_Color {
    override val attr = Attr.real("caret-color")
  }

  type FontStyleTransform =
    FontStyleBuilder[Option[String]] => FontFace[
      Option[String]]

  type FontWeightTransform =
    FontWeightBuilder[Option[String]] => FontFace[
      Option[String]]

  def registerFontFace(
    family: String
  )(
    style: FontStyleTransform,
    weight: FontWeightTransform,
    suffix: String
  ): Unit = {
    val shortName = s"$family-$suffix".replaceAll(" ", "")
    val longName = s"$family $suffix"
    val _ = fontFace(family)(srcBuilder => {
      val withSrc = srcBuilder.src(
        s"local('$longName')",
        s"local('$shortName')",
        s"url('jsShell/fonts/${shortName.toLowerCase()}.woff2') format('woff2')"
      )
      val withStyle = style(withSrc.fontStyle)
      val withWeight = weight(withStyle.fontWeight)
      withWeight
    })
  }

  def registerFontFaceAllStylesAllWeights(
    family: String
  ): Unit = {
    val register = registerFontFace(family)(_, _, _)
    register(_.normal, _._400, "Regular")
    register(_.italic, _._400, "Italic")
    register(_.normal, _._700, "Bold")
    register(_.italic, _._700, "Bold Italic")
  }

  registerFontFaceAllStylesAllWeights("Hack")
}
