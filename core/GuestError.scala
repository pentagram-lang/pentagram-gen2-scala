package tacit.core

final case class GuestError(
  sourceLocation: SourceLocation,
  message: String,
  annotations: Seq[GuestError.Annotation])

object GuestError {
  def withoutAnnotations(
    sourceLocation: SourceLocation,
    message: String
  ): GuestError =
    GuestError(sourceLocation, message, Seq())

  final case class Annotation(
    sourceLocation: Option[SourceLocation],
    message: String,
    isError: Boolean)

  def InfoAnnotation =
    Annotation(_: Option[SourceLocation], _: String, false)

  def ErrorAnnotation =
    Annotation(_: Option[SourceLocation], _: String, true)
}
