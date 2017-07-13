package tacit

import org.scalatest.{Assertion,FreeSpec}
import com.github.dwickern.macros.NameOf._
import scala.language.reflectiveCalls
import fastparse.all._

import LineParser._

class LineParserSpec extends FreeSpec {
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
        check.positive("1", TermLiteral(0, "1"))
      }
      "does not parse letters" in {
        check.negative("a")
      }
      "parses multiple digits" in {
        check.positive("123456789", TermLiteral(0, "123456789"))
      }
    }

    nameOf(invalidNumber) - {
      def check = checkParse(invalidNumber)
      "parses single digits" in {
        check.positive("1+", InvalidTermLiteralWithSuffix(0, "1+"))
      }
    }

    nameOf(operator) - {
      def check = checkParse(operator)
      "parses plus" in {
        check.positive("+", TermPlus(0, "+"))
      }
    }

    nameOf(unknownTerm) - {
      def check = checkParse(unknownTerm)
      "parses plus" in {
        check.positive("+", ValidTerm(TermPlus(0, "+")))
      }
      "parses single digits" in {
        check.positive("1", ValidTerm(TermLiteral(0, "1")))
      }
      "parses invalid single digits" in {
        check.positive("1+", InvalidTermLiteralWithSuffix(0, "1+"))
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
            ValidTerm(TermLiteral(0, "10")),
            ValidTerm(TermLiteral(3, "20")),
            ValidTerm(TermPlus(6, "+"))))
      }
      "parses mixed expression" in {
        check.positive(
          "10 2 +  3 4 +  +",
          Seq(
            ValidTerm(TermLiteral(0, "10")),
            ValidTerm(TermLiteral(3, "2")),
            ValidTerm(TermPlus(5, "+")),
            ValidTerm(TermLiteral(8, "3")),
            ValidTerm(TermLiteral(10, "4")),
            ValidTerm(TermPlus(12, "+")),
            ValidTerm(TermPlus(15, "+"))))
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
            ValidTerm(TermLiteral(0, "1")),
            InvalidTermLiteralWithSuffix(2, "23+"),
            InvalidTermOther(6, "*")))
      }
    }
  }
}
