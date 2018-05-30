package tacit.jsShell

import scalatags.jsdom
import scalatags.LowPriorityImplicits
import scalatags.JsDom

object HtmlSettings {
  object Defaults
      extends JsDom.Cap
      with JsDom.Attrs
      with JsDom.Styles
      with jsdom.Tags
      with JsDom.Aggregate
      with LowPriorityImplicits {
    object SvgAttrs extends JsDom.Cap with JsDom.SvgAttrs

    object SvgTags extends JsDom.Cap with jsdom.SvgTags

    object Tags2 extends JsDom.Cap with jsdom.Tags2
  }
}
