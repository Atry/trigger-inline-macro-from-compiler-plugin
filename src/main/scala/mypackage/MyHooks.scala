package mypackage
import quoted._
object MyHooks {
  inline def myHook[A](a: A): A = ${
    identity('a)
  }
}
