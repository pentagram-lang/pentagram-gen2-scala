package tacit.jsShell

import org.scalajs.dom.html
import scala.language.postfixOps
import scalacss.ScalatagsCss._

import CssSettings.Defaults._
import HtmlSettings.Defaults._
import HtmlSettings.Defaults.Tags2._

object HomeDetails extends RenderSimple[html.Element] {
  def renderSimple: RenderTag =
    section(
      Style.root,
      h2(
        Style.question,
        "Why Tacit?"
      ),
      p(
        Style.answer,
        "As programmers, we immerse ourselves in programming languages every day as we communicate, think, organize, and bring our ideas to life."
      ),
      p(
        Style.answer,
        "And the more we use a language, the more the language’s positive (and negative) traits ",
        em("affect our productivity and our well-being"),
        "."
      ),
      p(
        Style.answer,
        "Tacit is an experiment to advance programming language ",
        strong("ergonomics"),
        ", while simultaneously pursuing goals of ",
        strong("determinism"),
        " and ",
        strong("efficiency"),
        "."
      ),
      h2(
        Style.question,
        "How does Tacit help beginner programmers?"
      ),
      p(
        Style.answer,
        "Tacit uses ",
        strong("ergonomics"),
        " to helps beginners, via simple syntax rules, functional programming, and the development environment itself."
      ),
      p(
        Style.answer,
        "For example, with significant whitespace, beginners are spared confusing syntax errors. With functional programming, beginners can focus on how values get calculated. And with high quality error messages, beginners get immediate and helpful feedback."
      ),
      h2(
        Style.question,
        "How does Tacit help experienced programmers?"
      ),
      p(
        Style.answer,
        "The benefits of Tacit’s beginner-friendly ",
        strong("ergonomics"),
        " get amplified for experienced programmers writing larger programs.",
      ),
      p(
        Style.answer,
        "Simple syntax rules aren’t just for easier learning, they make every code change easier. Functional programming is a full paradigm for reducing the cognitive complexity. And good error messages are important for everyone, because we all make coding mistakes.",
      ),
      h2(
        Style.question,
        "How does Tacit help programs run correctly?"
      ),
      p(
        Style.answer,
        "Tacit helps with program correctness using two forms of ",
        strong("determinism"),
        ": functional programming and fail-fast errors."
      ),
      p(
        Style.answer,
        "Functional programming avoids side effects (a major source of unexpected program behavior), and fail-fast errors prevent problems from propagating past their source."
      ),
      h2(
        Style.question,
        "How does Tacit help programs run fast?"
      ),
      p(
        Style.answer,
        "Tacit’s ",
        strong("efficiency"),
        " comes from an optimizing compiler that uses static typing together with techniques like tail-call optimization, static dispatch, monomorphism, method inlining, and SIMD."
      ),
      p(
        Style.answer,
        "For functional programming, instead of preventing shared, mutable state by disabling ",
        em("mutable"),
        " state, Tacit’s default is to disable ",
        em("shared"),
        " state. This allows for fast mutation and less memory allocated."
      )
    )

  object Style extends StyleSheet.Inline {
    import Style.dsl._

    val root: StyleA = style(
      display.flex,
      flexDirection.column,
      paddingTop(1.5 em),
      maxWidth(60 ch)
    )

    val question: StyleA = style(
      marginTop(0.6 em),
      marginBottom(0.8 em),
      marginLeft(-1.4 ch),
      color(Colors.special0.dark),
      fontSize(1.2 em),
      fontWeight._400,
      lineHeight(1.4)
    )

    val answer: StyleA = style(
      marginTop.`0`,
      marginBottom(1.4 em)
    )
  }
}
