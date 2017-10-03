package tacit

sealed trait Arithmetic

object Arithmetic {
  final object + extends Arithmetic
  final object - extends Arithmetic
  final object * extends Arithmetic
  final object / extends Arithmetic
}
