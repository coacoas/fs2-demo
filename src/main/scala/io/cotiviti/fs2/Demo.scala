package io.cotiviti.fs2

import cats.effect.IO
import fs2._
import java.nio.file.Paths
import scala.util.Random

object Demo extends App {
  // Sink[E, I] <=> Stream[E, I] => Stream[E, Unit]
  val log: Sink[IO, String] = _.evalMap(s => IO { println(s) })
  import scala.concurrent.ExecutionContext.Implicits.global

  val s: Stream[IO, Unit] =
    Stream.bracket(IO {
      println("Open")
    })(
      use = _ =>   Stream.eval(IO {
        Random.nextInt(100)
      }).repeat
        .take(100)
        .map(c => 32 + (9 * c / 5))
        .flatMap(f => Stream(f, f))
        .map(_.toString)
        .observe(log)
        .evalMap(_ => IO.raiseError(new RuntimeException("blah")))
        .through(text.utf8Encode)
        .to(io.file.writeAll(Paths.get("/tmp/f"))),
      release = _ => IO { println("Close") }
    )

  println(s"Begin: $s")
  println(s.compile.drain.unsafeRunSync())
  println(s"End: $s")
}
