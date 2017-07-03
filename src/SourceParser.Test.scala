package tacit

import org.scalatest.{Assertion,FreeSpec}
import com.github.dwickern.macros.NameOf._
import scala.language.reflectiveCalls

import SourceParser._

class SourceParserSpec extends FreeSpec {
  nameOf(SourceParser) - {
    def checkParse(parser: PackratParser[Any]) = new {
      def positive(in: String, result: Any): Assertion = {
        parseAll(parser, in) match {
          case Success(matched, _) =>
            assert(matched == result)
          case Failure(message, _) =>
            fail(s"Parse failed: $message")
          case Error(message, _) =>
            fail(s"Parse error: $message")
        }
      }
      def negative(in: String): Assertion = {
        parseAll(parser, in) match {
          case Success(matched, _) =>
            fail(s"Parse succeeded: $matched")
          case Failure(_, _) =>
            assert(true)
          case Error(_, _) =>
            assert(true)
        }
      }
    }

    nameOf(number) - {
      def check = checkParse(number)
      "parses single digits" in {
        check.positive("1", SourceTermLiteral(1))
      }
      "does not parse letters" in {
        check.negative("a")
      }
      "parses multiple digits" in {
        check.positive("123456789", SourceTermLiteral(123456789))
      }
    }

    nameOf(operator) - {
      def check = checkParse(operator)
      "parses plus" in {
        check.positive("+", SourceTermPlus())
      }
    }

    nameOf(expression) - {
      def check = checkParse(expression)
      "parses mixed expression" in {
        check.positive(
          "1 2 +  3 4 +  +",
          List(
            SourceTermLiteral(1),
            SourceTermLiteral(2),
            SourceTermPlus(),
            SourceTermLiteral(3),
            SourceTermLiteral(4),
            SourceTermPlus(),
            SourceTermPlus()))
      }
      "does not parse nothing" in {
        check.negative("")
      }
      "does not parse when whitespace missing" in {
        check.negative("1 2+")
      }
    }
  }
}
