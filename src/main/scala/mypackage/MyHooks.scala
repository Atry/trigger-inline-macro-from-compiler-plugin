package mypackage
import quoted._
object MyHooks {
  // inline 
  def myHook[A](a: A): A = a
  //  ${
  //   identity('a)
  // }

  def ifThenElse[A](condition: Boolean, thenBlock: A, elseBlock: A): A = ???

}
