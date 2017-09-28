package tacit

import org.scalatest.{Assertion,FreeSpec}
import com.github.dwickern.macros.NameOf._

import StackInterpreter._
import SyntaxTerm._
import Expression._
import SourceLocationExtensions._

final class StackInterpreterSpec extends FreeSpec {
  nameOf(StackInterpreter) - {
    nameOf(interpretOne(_, _)) - {
      object check {
        def positive(
          term: SyntaxTerm,
          stack: List[Expression],
          result: List[Expression]
        ): Assertion =
          assert(
            interpretOne(term, stack)
            == Expression.Valid(result))

        def negative(
          term: SyntaxTerm,
          stack: List[Expression],
          result: Expression.Unknown
        ): Assertion =
          assert(
            interpretOne(term, stack)
            == result)
      }

      "adds values to stack" in {
        check.positive(
          Literal(1, 0 -- 1),
          List(),
          List(
            Value(1, 0 -- 1)))
        check.positive(
          Literal(2, 2 -- 3),
          List(
            Value(1, 0 -- 1)),
          List(
            Value(2, 2 -- 3),
            Value(1, 0 -- 1)))
      }

      "uses values for operators" in {
        check.positive(
          Plus(4 -- 5),
          List(
            Value(2, 2 -- 3),
            Value(1, 0 -- 1)),
          List(
            Add(
              Value(1, 0 -- 1),
              Value(2, 2 -- 3),
              4 -- 5)))
        check.positive(
          Plus(6 -- 7),
          List(
            Value(3, 4 -- 5),
            Value(2, 2 -- 3),
            Value(1, 0 -- 1)),
          List(
            Add(
              Value(2, 2 -- 3),
              Value(3, 4 -- 5),
              6 -- 7),
            Value(1, 0 -- 1)))
      }

      "uses operators for operators" in {
        check.positive(
          Plus(8 -- 9),
          List(
            Add(
              Value(2, 2 -- 3),
              Value(3, 4 -- 5),
              6 -- 7),
            Value(1, 0 -- 1)),
          List(
            Add(
              Value(1, 0 -- 1),
              Add(
                Value(2, 2 -- 3),
                Value(3, 4 -- 5),
                6 -- 7),
              8 -- 9)))
      }

      "fails for stack underflow" in {
        check.negative(
          Plus(0 -- 1),
          List(),
          StackUnderflow(
            List(),
            Seq("initial-value", "addition"),
            0 -- 1))
        check.negative(
          Plus(2 -- 3),
          List(
            Value(1, 0 -- 1)),
          StackUnderflow(
            List(
              Value(1, 0 -- 1)),
            Seq("initial-value", "addition"),
            2 -- 3))
      }
    }
  }
}
