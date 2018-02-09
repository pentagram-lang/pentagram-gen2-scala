package tacit.jsShell

import org.scalajs.dom
import scalatags.JsDom.TypedTag
import scalatags.JsDom.all.{
  Double2CssNumber => _,
  Int2CssNumber => _,
  _
}

trait Render[RootArg <: dom.Element] {
  type Root = RootArg

  type Component

  type RenderComponent = (TypedTag[Root], Root => Component)

  def render: RenderComponent

  def apply(xs: Modifier*): Component = {
    val (rootTag, partialComponent) = render
    val root = rootTag(xs).render
    partialComponent(root)
  }
}
