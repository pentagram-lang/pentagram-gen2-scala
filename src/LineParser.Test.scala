package tacit

import org.scalatest.{Assertion,FreeSpec}
import com.github.dwickern.macros.NameOf._
import scala.language.reflectiveCalls
import fastparse.all._

import LineParser._
import SyntaxTerm._

final class LineParserSpec extends FreeSpec {
  nameOf(LineParser) - {
    def checkParse(parser: P[Any]) = new {
      val fullParser = P( parser ~ End )

      def positive(in: String, result: Any): Assertion =
        fullParser.parse(in).fold(
          (failedParser, position, extra) =>
            fail(s"Parse failed: $failedParser $position $extra"),
          (matched, _) =>
            assert(matched == result))

      def negative(in: String): Assertion =
        fullParser.parse(in).fold(
          (failedParser, position, extra) =>
            assert(true),
          (matched, _) =>
            fail(s"Parse succeeded: $matched"))
    }

    nameOf(number) - {
      def check = checkParse(number)
      "parses single digits" in {
        check.positive("1", Literal(0, "1"))
      }
      "does not parse letters" in {
        check.negative("a")
      }
      "parses multiple digits" in {
        check.positive("123456789", Literal(0, "123456789"))
      }
    }

    nameOf(invalidNumber) - {
      def check = checkParse(invalidNumber)
      "parses single digits" in {
        check.positive("1+", InvalidLiteralSuffix(1, "+"))
      }
    }

    nameOf(operator) - {
      def check = checkParse(operator)
      "parses plus" in {
        check.positive("+", Plus(0, "+"))
      }
    }

    nameOf(unknownTerm) - {
      def check = checkParse(unknownTerm)
      "parses plus" in {
        check.positive("+", Valid(Plus(0, "+")))
      }
      "parses single digits" in {
        check.positive("1", Valid(Literal(0, "1")))
      }
      "parses invalid single digits" in {
        check.positive("1+", InvalidLiteralSuffix(1, "+"))
      }
      "does not parse whitespace" in {
        check.negative("1+  ")
      }
    }

    nameOf(expression) - {
      def check = checkParse(expression)
      "parses simple expression" in {
        check.positive(
          "10 20 +",
          Seq(
            Valid(Literal(0, "10")),
            Valid(Literal(3, "20")),
            Valid(Plus(6, "+"))))
      }
      "parses mixed expression" in {
        check.positive(
          "10 2 +  3 4 +  +",
          Seq(
            Valid(Literal(0, "10")),
            Valid(Literal(3, "2")),
            Valid(Plus(5, "+")),
            Valid(Literal(8, "3")),
            Valid(Literal(10, "4")),
            Valid(Plus(12, "+")),
            Valid(Plus(15, "+"))))
      }
      "parses nothing" in {
        check.positive(
          "",
          Seq())
      }
      "parses whitespace" in {
        check.positive(
          "   ",
          Seq())
      }
      "does not parse when whitespace missing" in {
        check.positive(
          "1 23+ *",
          Seq(
            Valid(Literal(0, "1")),
            InvalidLiteralSuffix(4, "+"),
            InvalidOther(6, "*")))
      }
    }
  }
}
