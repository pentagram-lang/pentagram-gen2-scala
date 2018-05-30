package tacit.jsShell

import org.scalajs.dom
import scalatags.JsDom.TypedTag

import HtmlSettings.Defaults._

trait Render[RootArg <: dom.Element] {
  type Root = RootArg

  type Component

  type RenderTag = TypedTag[Root]

  type RenderComponent = (RenderTag, Root => Component)

  def render: RenderComponent

  def apply(xs: Modifier*): Component = {
    val (rootTag, partialComponent) = render
    val root = rootTag(xs).render
    partialComponent(root)
  }
}
