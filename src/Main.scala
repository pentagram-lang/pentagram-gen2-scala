package tacit

import scala.annotation.tailrec
import org.jline.reader.{ LineReaderBuilder, UserInterruptException, EndOfFileException }

object Main {
  def main(args: Array[String]) = {
    println("Starting loop")

    val reader = LineReaderBuilder.builder().build()
    val prompt = "tacit> "

    @tailrec
    def loop: Unit = {
      try {
        val line = reader.readLine(prompt)
        SourceParser.parse(line) match {
          case Left(errors) => println(s"${errors.length} errors!")
          case Right(result) => println(result)
        }
      }
      catch {
        case _: UserInterruptException => ()
        case _: EndOfFileException => return ()
      }
      loop
    }

    loop
  }
}
