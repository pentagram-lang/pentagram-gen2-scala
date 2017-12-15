package tacit.core

final case class SourceLocation(
  begin: Int,
  end: Int
) {
  def length = end - begin
}

object SourceLocationExtensions {
  implicit final class SourceLocationHelper(val begin: Int) extends AnyVal {
    def --(end: Int): SourceLocation =
      SourceLocation(begin, end)
  }
}
