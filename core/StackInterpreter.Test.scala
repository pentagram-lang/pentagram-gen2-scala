package tacit.core

import org.scalatest.{Assertion,FreeSpec}
import com.github.dwickern.macros.NameOf._

import StackInterpreter._
import SyntaxTerm._
import Arithmetic._
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
          Operator(A_+, 4 -- 5),
          List(
            Value(2, 2 -- 3),
            Value(1, 0 -- 1)),
          List(
            Apply(
              A_+,
              Value(1, 0 -- 1),
              Value(2, 2 -- 3),
              4 -- 5)))
        check.positive(
          Operator(A_-, 6 -- 7),
          List(
            Value(3, 4 -- 5),
            Value(2, 2 -- 3),
            Value(1, 0 -- 1)),
          List(
            Apply(
              A_-,
              Value(2, 2 -- 3),
              Value(3, 4 -- 5),
              6 -- 7),
            Value(1, 0 -- 1)))
      }

      "uses operators for operators" in {
        check.positive(
          Operator(A_*, 8 -- 9),
          List(
            Apply(
              A_/,
              Value(2, 2 -- 3),
              Value(3, 4 -- 5),
              6 -- 7),
            Value(1, 0 -- 1)),
          List(
            Apply(
              A_*,
              Value(1, 0 -- 1),
              Apply(
                A_/,
                Value(2, 2 -- 3),
                Value(3, 4 -- 5),
                6 -- 7),
              8 -- 9)))
      }

      "fails for stack underflow" in {
        check.negative(
          Operator(A_+, 0 -- 1),
          List(),
          StackUnderflow(
            List(),
            Seq("initial-value", "addition"),
            0 -- 1))
        check.negative(
          Operator(A_-, 2 -- 3),
          List(
            Value(1, 0 -- 1)),
          StackUnderflow(
            List(
              Value(1, 0 -- 1)),
            Seq("initial-value", "subtraction"),
            2 -- 3))
      }
    }
  }
}
