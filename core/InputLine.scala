package tacit

sealed trait InputLine

object InputLine {
  final case class Value(text: String) extends InputLine
  final case class UserInterrupt() extends InputLine
  final case class EndOfStream() extends InputLine
}
