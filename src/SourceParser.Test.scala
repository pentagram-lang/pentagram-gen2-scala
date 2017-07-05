package tacit

import org.scalatest.{Assertion,FreeSpec}
import com.github.dwickern.macros.NameOf._
import scala.language.reflectiveCalls
import fastparse.all._

import SourceParser._

class SourceParserSpec extends FreeSpec {
  nameOf(SourceParser) - {
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
        check.positive("1", SourceTermLiteral(0, "1"))
      }
      "does not parse letters" in {
        check.negative("a")
      }
      "parses multiple digits" in {
        check.positive("123456789", SourceTermLiteral(0, "123456789"))
      }
    }

    nameOf(invalidNumber) - {
      def check = checkParse(invalidNumber)
      "parses single digits" in {
        check.positive("1+", InvalidSourceTermLiteralWithSuffix(0, "1+"))
      }
    }

    nameOf(operator) - {
      def check = checkParse(operator)
      "parses plus" in {
        check.positive("+", SourceTermPlus(0, "+"))
      }
    }

    nameOf(unknownTerm) - {
      def check = checkParse(unknownTerm)
      "parses plus" in {
        check.positive("+", ValidSourceTerm(SourceTermPlus(0, "+")))
      }
      "parses single digits" in {
        check.positive("1", ValidSourceTerm(SourceTermLiteral(0, "1")))
      }
      "parses invalid single digits" in {
        check.positive("1+", InvalidSourceTermLiteralWithSuffix(0, "1+"))
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
            ValidSourceTerm(SourceTermLiteral(0, "10")),
            ValidSourceTerm(SourceTermLiteral(3, "20")),
            ValidSourceTerm(SourceTermPlus(6, "+"))))
      }
      "parses mixed expression" in {
        check.positive(
          "10 2 +  3 4 +  +",
          Seq(
            ValidSourceTerm(SourceTermLiteral(0, "10")),
            ValidSourceTerm(SourceTermLiteral(3, "2")),
            ValidSourceTerm(SourceTermPlus(5, "+")),
            ValidSourceTerm(SourceTermLiteral(8, "3")),
            ValidSourceTerm(SourceTermLiteral(10, "4")),
            ValidSourceTerm(SourceTermPlus(12, "+")),
            ValidSourceTerm(SourceTermPlus(15, "+"))))
      }
      "does not parse nothing" in {
        check.negative("")
      }
      "does not parse when whitespace missing" in {
        check.positive(
          "1 23+ *",
          Seq(
            ValidSourceTerm(SourceTermLiteral(0, "1")),
            InvalidSourceTermLiteralWithSuffix(2, "23+"),
            InvalidSourceTermOther(6, "*")))
      }
    }
  }
}
