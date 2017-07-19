package tacit

sealed trait InputLine

object InputLine {
  case class Value(text: String) extends InputLine
  case class UserInterrupt() extends InputLine
  case class EndOfStream() extends InputLine
}
