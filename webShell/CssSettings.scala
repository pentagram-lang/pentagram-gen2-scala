package tacit.webShell

import scalacss.devOrProdDefaults
import scalacss.defaults.Exports
import scalacss.internal.mutable.Settings

object CssSettings {
  val Defaults: Exports with Settings = devOrProdDefaults
}
