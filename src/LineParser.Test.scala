package tacit

import org.scalatest.{Assertion,FreeSpec}
import com.github.dwickern.macros.NameOf._
import fastparse.all._

import LineParser._
import SyntaxTerm._
import Arithmetic._
import SourceLocationExtensions._

final class LineParserSpec extends FreeSpec {
  nameOf(LineParser) - {
    final case class CheckParse(parser: P[Any]) {
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
      val check = CheckParse(number)
      "parses single digits" in {
        check.positive("1", Literal(1, 0 -- 1))
      }
      "does not parse letters" in {
        check.negative("a")
      }
      "parses multiple digits" in {
        check.positive("123456789", Literal(123456789, 0 -- 9))
      }
    }

    nameOf(invalidNumber) - {
      val check = CheckParse(invalidNumber)
      "parses single digits" in {
        check.positive("1+", InvalidLiteralSuffix(1 -- 2))
      }
    }

    nameOf(operator) - {
      val check = CheckParse(operator)
      "parses plus" in {
        check.positive("+", Operator(+, 0 -- 1))
      }
      "parses minus" in {
        check.positive("-", Operator(-, 0 -- 1))
      }
      "parses multiply" in {
        check.positive("*", Operator(*, 0 -- 1))
      }
      "parses divide" in {
        check.positive("/", Operator(/, 0 -- 1))
      }
    }

    nameOf(unknownTerm) - {
      val check = CheckParse(unknownTerm)
      "parses plus" in {
        check.positive("-", Valid(Operator(-, 0 -- 1)))
      }
      "parses single digits" in {
        check.positive("1", Valid(Literal(1, 0 -- 1)))
      }
      "parses invalid single digits" in {
        check.positive("1*", InvalidLiteralSuffix(1 -- 2))
      }
      "does not parse whitespace" in {
        check.negative("1/  ")
      }
    }

    nameOf(expression) - {
      val check = CheckParse(expression)
      "parses simple expression" in {
        check.positive(
          "10 20 *",
          Seq(
            Valid(Literal(10, 0 -- 2)),
            Valid(Literal(20, 3 -- 5)),
            Valid(Operator(*, 6 -- 7))))
      }
      "parses mixed expression" in {
        check.positive(
          "10 2 +  3 4 -  /",
          Seq(
            Valid(Literal(10, 0 -- 2)),
            Valid(Literal(2, 3 -- 4)),
            Valid(Operator(+, 5 -- 6)),
            Valid(Literal(3, 8 -- 9)),
            Valid(Literal(4, 10 -- 11)),
            Valid(Operator(-, 12 -- 13)),
            Valid(Operator(/, 15 -- 16))))
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
          "1 23+ ?",
          Seq(
            Valid(Literal(1, 0 -- 1)),
            InvalidLiteralSuffix(4 -- 5),
            InvalidOther(6 -- 7)))
      }
    }
  }
}
