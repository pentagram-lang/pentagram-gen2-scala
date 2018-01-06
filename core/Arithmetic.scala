package tacit.core

sealed trait Arithmetic {
  def parameterNames: (String, String)
}

object Arithmetic {
  final object A_+ extends Arithmetic {
    val parameterNames = ("initial-value", "addition")
  }
  final object A_- extends Arithmetic {
    val parameterNames = ("initial-value", "subtraction")
  }
  final object A_* extends Arithmetic {
    val parameterNames = ("initial-value", "multiplier")
  }
  final object A_/ extends Arithmetic {
    val parameterNames = ("initial-value", "divisor")
  }
}
