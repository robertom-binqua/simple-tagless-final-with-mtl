package org.binqua.examples.taglessfinal

import cats.FlatMap
import cats.effect.{IO, IOApp}
import cats.implicits._

trait MyConsole[F[_]] {

  def read(): F[String]

  def print(toBePrinted: String): F[Unit]

}

class MyIOConsole extends MyConsole[IO] {

  override def read(): IO[String] = IO.readLine

  override def print(toBePrinted: String): IO[Unit] = IO.println(toBePrinted)

}

object Program {

  def run[F[_]: FlatMap](myConsole: MyConsole[F]): F[Unit] =
    for {
      toBePrinted <- myConsole.read()
      result <- myConsole.print(toBePrinted)
    } yield result

}

object Runner extends IOApp.Simple {

  override def run: IO[Unit] = Program.run(new MyIOConsole())

}



