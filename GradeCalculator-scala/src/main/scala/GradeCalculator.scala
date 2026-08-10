import scala.io.StdIn.readInt

object GradeCalculator extends App {

  println("Enter marks (0-100): ")
  val marks = readInt()

  val grade =
    if (marks < 0 || marks > 100) "Invalid marks"
    else if (marks >= 90) "A+"
    else if (marks >= 80) "A"
    else if (marks >= 70) "B"
    else if (marks >= 60) "C"
    else if (marks >= 50) "D"
    else "F"

  println(s"Marks: $marks")
  println(s"Grade: $grade")
}