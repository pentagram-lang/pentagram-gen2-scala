package tacit.jsShell

import org.scalajs.dom

trait RenderSimple[RootArg <: dom.Element]
    extends Render[RootArg] {

  def renderSimple: RenderTag

  def render: RenderComponent = {
    val root = renderSimple
    (root, new Component(_))
  }

  final class Component(
    val root: Root
  )
}
