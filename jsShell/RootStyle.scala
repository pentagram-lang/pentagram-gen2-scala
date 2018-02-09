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
    fontFamily.attr := "'Source Code Pro', monospace",
    fontWeight._500,
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

  def registerFontFaceCustomSuffix(
    family: String,
    style: FontStyleTransform,
    weight: FontWeightTransform,
    shortNameSuffix: String,
    longNameSuffix: String
  ): Unit = {
    val shortName =
      s"${family.replaceAll(" ", "")}-$shortNameSuffix"
    val longName = s"$family$longNameSuffix"
    val _ = fontFace(family)(srcBuilder => {
      val withSrc = srcBuilder.src(
        s"local('$longName')",
        s"local('$shortName')",
        s"url('jsShell/fonts/$shortName.otf.woff2') format('woff2')"
      )
      val withStyle = style(withSrc.fontStyle)
      val withWeight = weight(withStyle.fontWeight)
      withWeight
    })
  }

  def registerFontFace(
    family: String,
    style: FontStyleTransform,
    styleShortName: String,
    styleLongName: String,
    weight: FontWeightTransform,
    weightName: String
  ): Unit =
    registerFontFaceCustomSuffix(
      family,
      style,
      weight,
      s"$weightName$styleShortName",
      s" $weightName$styleLongName"
    )

  def registerFontFaceNormal(
    family: String,
    weight: FontWeightTransform,
    weightName: String
  ): Unit =
    registerFontFace(
      family,
      _.normal,
      "",
      "",
      weight,
      weightName
    )

  def registerFontFaceItalic(
    family: String,
    weight: FontWeightTransform,
    weightName: String
  ): Unit =
    registerFontFace(
      family,
      _.italic,
      "It",
      " Italic",
      weight,
      weightName
    )

  def registerFontFaceAllStyles(
    family: String,
    weight: FontWeightTransform,
    weightName: String
  ): Unit = {
    registerFontFaceNormal(family, weight, weightName)
    registerFontFaceItalic(family, weight, weightName)
  }

  def registerFontFaceAllStylesAllWeights(
    family: String
  ): Unit = {
    registerFontFaceAllStyles(family, _._200, "ExtraLight")
    registerFontFaceAllStyles(family, _._300, "Light")
    registerFontFaceCustomSuffix(
      family,
      _.normal,
      _._400,
      "Regular",
      "")
    registerFontFaceCustomSuffix(
      family,
      _.italic,
      _._400,
      "It",
      "Italic")
    registerFontFaceAllStyles(family, _._500, "Medium")
    registerFontFaceAllStyles(family, _._600, "Semibold")
    registerFontFaceAllStyles(family, _._700, "Bold")
    registerFontFaceAllStyles(family, _._800, "Black")
  }

  registerFontFaceAllStylesAllWeights("Source Code Pro")
}
