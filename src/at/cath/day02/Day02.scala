package day02


object Day02 {
  private def checkBounds(a: Int, b: Int): Boolean = {
    0 < math.abs(a - b) && math.abs(a - b) <= 3
  }

  private def verifySequence(row: Array[Int]): Boolean = {
    val diff0 = row(1) - row(0)
    val diff1 = row(2) - row(1)
    val diff2 = row(3) - row(2)
    val rowSlope = List(diff0, diff1, diff2).count(_ > 0) >= 2

    var secondChance = true
    var prev = row(0)
    row.zip(row.tail).zipWithIndex.foreach { case ((a, b), index) =>
      var skipCurrent = false
      val localSlope = b - prev > 0
      if (!checkBounds(prev, b) || localSlope != rowSlope) {
        if (!secondChance) {
          return false
        }
        secondChance = false

        // ca diff
        if (index + 2 >= row.length) {
          return false
        }
        val c = row(index + 2)
        if (checkBounds(a, c)) {
          skipCurrent = true
        } else if (!checkBounds(b, c)) {
          return false
        }

      }
      if (!skipCurrent) {
        prev = b
      }
    }
    true
  }

  def main(args: Array[String]): Unit = {
    val bufferedSource = io.Source.fromFile("Day02.txt")
    val lines = bufferedSource.getLines().toArray

    val matrix = lines.map(_.split(" ").map(_.toInt))
    val safeReports = matrix.count(verifySequence)

    println(s"Safe reports: $safeReports")
    bufferedSource.close()
  }
}
