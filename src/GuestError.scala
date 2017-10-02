package tacit

final case class GuestError(
  sourceLocation: SourceLocation,
  message: String,
  annotations: Seq[GuestError.Annotation] = Seq())

object GuestError {
  final case class Annotation(
    sourceLocation: Option[SourceLocation],
    message: String,
    isError: Boolean)
}
