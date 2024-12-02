package day02

object Day02 {
  def main(args: Array[String]): Unit = {
    val bufferedSource = io.Source.fromFile("Day02.txt")
    val lines = bufferedSource.getLines.toArray

    val matrix = lines.map(_.split(" ").map(_.toInt))

    var safeReports = 0
    matrix.foreach(row => {
      var rowSlope = row(1) - row(0) >= 0
      var safeLevel = 2
      row.zip(row.tail).foreach(pair => {
        val (a, b) = pair
        val diff = b - a
        val absDiff = math.abs(diff)
        val localSlope = diff >= 0
        if (absDiff > 3 || absDiff < 1 || localSlope != rowSlope) {
          safeLevel -= 1
        }
        if (safeLevel != 1)
          rowSlope = localSlope
      })
      // no automatic implicit cast in scala, boring!
      safeReports += (if (safeLevel > 0) 1 else 0)
    })
    println(safeReports)
  }
}