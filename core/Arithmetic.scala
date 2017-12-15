package tacit

sealed trait Arithmetic {
  def parameterNames: (String, String)
}

object Arithmetic {
  final object + extends Arithmetic {
    val parameterNames = ("initial-value", "addition")
  }
  final object - extends Arithmetic {
    val parameterNames = ("initial-value", "subtraction")
  }
  final object * extends Arithmetic {
    val parameterNames = ("initial-value", "multiplier")
  }
  final object / extends Arithmetic {
    val parameterNames = ("initial-value", "divisor")
  }
}
