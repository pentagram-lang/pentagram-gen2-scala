package tacit.core

import com.github.dwickern.macros.NameOf._
import org.scalameter._

import LineParser._

object LineParserBenchmark extends Bench.ForkedTime {
  val sizes: Gen[Int] =
    Gen.exponential("size")(10, 10 << 6, 2)

  val lines: Gen[String] =
    for (size <- sizes) yield {
      "123 456 + 99032L 11 +   " * size
    }

  performance of nameOf(LineParser) in {
    measure method nameOf(parse _) in {
      using(lines) in { line =>
        LineParser.parse(line)
      }
    }
  }
}
