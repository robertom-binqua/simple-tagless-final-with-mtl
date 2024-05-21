package org.binqua.examples.taglessfinal

import cats.data.State
import cats.mtl.Stateful

class ProgramSpec extends munit.FunSuite {

  test("using Stateful mtl") {
    type StateM[A] = State[MyState, A]

    case class MyState(toBeRead: String, toBePrinted: List[String])

    class StatefulMyConsole[F[_]](implicit stateful: Stateful[F, MyState])
        extends MyConsole[F] {

      override def read(): F[String] = stateful.inspect(_.toBeRead)

      override def print(toBePrinted: String): F[Unit] =
        stateful.modify(s => s.copy(toBePrinted = toBePrinted :: s.toBePrinted))

    }

    val testState: State[MyState, StatefulMyConsole[StateM]] =
      State.pure(new StatefulMyConsole[StateM]())

    assertEquals(
      testState
        .flatMap(Program.run(_))
        .run(MyState("inputFromUser", Nil))
        .value
        ._1
        .toBePrinted,
      List("inputFromUser")
    )

  }

  test("using only the State") {

    type StateM[A] = State[MyState, A]

    case class MyState(toBeRead: String, toBePrinted: List[String])

    class StateMyConsole extends MyConsole[StateM] {

      override def read(): StateM[String] = State.inspect(_.toBeRead)

      override def print(toBePrinted: String): StateM[Unit] =
        State.modify(s => s.copy(toBePrinted = toBePrinted :: s.toBePrinted))

    }

    val programWithState: State[MyState, Unit] = State
      .pure(new StateMyConsole())
      .flatMap(aConsole => Program.run(aConsole))

    assertEquals(
      programWithState.run(MyState("inputFromUser", Nil)).value._1.toBePrinted,
      List("inputFromUser")
    )

  }
}
