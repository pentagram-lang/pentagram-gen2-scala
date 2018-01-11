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

  def InfoAnnotation(
    sourceLocation: Option[SourceLocation],
    message: String
  ): Annotation =
    Annotation(sourceLocation, message, isError = false)

  def ErrorAnnotation(
    sourceLocation: Option[SourceLocation],
    message: String
  ): Annotation =
    Annotation(sourceLocation, message, isError = true)
}
