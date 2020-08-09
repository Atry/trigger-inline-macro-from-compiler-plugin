package mypackage
object Main {
  def main(args: Array[String]) = if (math.random > 0.5) {
    println(identity("then"))
  } else {
    println(identity("else"))
  }
}