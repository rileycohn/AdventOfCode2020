import scala.io.Source

object Day13 {
  def main(args: Array[String]): Unit = {

    val filename = "src/main/scala/Day13.txt"
    val bufferedSource = Source.fromFile(filename)
    val lines = bufferedSource.getLines.toArray
    bufferedSource.close

    println("Part1: " + part1(lines))
    println("Part2: " + part2(lines))

  }

  def part1(lines: Array[String]): Int = {
    val earliestTimestamp = lines(0).toInt
    val availableBusses = lines(1).split(",")

    // Find the closest bus
    var closestRemainder = 9999999
    var closestBus = 0

    for (bus <- availableBusses) {
      if (bus != "x") {
        val remainder = bus.toInt - (earliestTimestamp % bus.toInt)
        if (remainder < closestRemainder) {
          closestRemainder = remainder
          closestBus = bus.toInt
        }
      }
    }

    // Now we know which bus we need to take
    // remainder * bus id
    closestRemainder * closestBus
  }

  def part2(lines: Array[String]): Long = {

    val availableBusses = lines(1).split(",")
    var timestamp = availableBusses(0).toLong
    var step = 1L

    for (i <- availableBusses.indices) {
      val busId = availableBusses(i)
      if (busId != "x") {
        while ((timestamp + i) % busId.toInt != 0) {
          timestamp += step
        }
        step *= busId.toInt
      }
    }

    timestamp
  }
}