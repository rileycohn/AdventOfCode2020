import java.io.File
import java.lang.Long.parseLong

fun main() {
    val fileName = "src/Day14.txt"
    val lines = File(fileName).readLines()
    println("Part 1: " + part1(lines))
    println("Part 2: " + part2(lines))
}

fun part1(lines: List<String>): Long {
    var memory = HashMap<Int, Long>()
    var mask = ""

    for (i in lines.indices) {
        if (lines[i].contains("mask")) {
            mask = lines[i].subSequence(7, lines[i].length) as String
            continue
        }

        val match = Regex("mem\\[(\\d+)\\]").find(lines[i])!!
        val groupValues = match.destructured.toList()
        val memoryLocation = groupValues[0]
        val value = lines[i].substringAfter("= ").toInt()
        // Do the bit mask
        var binaryValue = Integer.toBinaryString(value).padStart(36, '0')

        // Track final string here
        var finalValue: MutableList<Char> = mutableListOf()
        // Iterate the binary string and update values from mask where maskVal != X
        for (j in binaryValue.indices) {
            if (mask[j] != 'X') {
                // Override the value in the final binary string to the mask value
                finalValue.add(mask[j])
            } else {
                finalValue.add(binaryValue[j])
            }
        }

        val binaryString = finalValue.joinToString("")

        val maskedToInt = parseLong(binaryString, 2)

        // Store in memory
        memory[memoryLocation.toInt()] = maskedToInt
    }

    // Once we have everything in the memory map, get the sum of all values
    var sum = 0L
    memory.forEach {
        sum += it.value
    }

    return sum
}

fun part2(lines: List<String>): Long {
    var memory = HashMap<Long, Long>()
    var mask = ""

    for (i in lines.indices) {
        if (lines[i].contains("mask")) {
            mask = lines[i].subSequence(7, lines[i].length) as String
            continue
        }

        val match = Regex("mem\\[(\\d+)\\]").find(lines[i])!!
        val groupValues = match.destructured.toList()
        val memoryLocation = groupValues[0]
        val value = lines[i].substringAfter("= ").toInt()

        // Do the bit mask
        var binaryValue = Integer.toBinaryString(memoryLocation.toInt()).padStart(36, '0')

        // Track final string here
        var finalValue: MutableList<Char> = mutableListOf()
        // Iterate the binary string and update values from mask where maskVal != X
        for (j in binaryValue.indices) {
            if (mask[j] != '0') {
                // Override the value in the final binary string to the mask value
                finalValue.add(mask[j])
            } else {
                finalValue.add(binaryValue[j])
            }
        }

        val binaryString = finalValue.joinToString("")

        // At this point we have the binary string with the floating values 'X'
        var combos = mutableSetOf<String>()
        getAllCombinations(binaryString.toCharArray(), 0, combos)

        combos.forEach {
            // Now loop through combos
            val maskedToInt = parseLong(it, 2)

            // Store in memory
            memory[maskedToInt] = value.toLong()
        }
    }

    // Once we have everything in the memory map, get the sum of all values
    var sum = 0L
    memory.forEach {
        sum += it.value
    }

    return sum
}

fun getAllCombinations(pattern: CharArray, i: Int, combos: MutableSet<String>) {
    if (i == pattern.size) {
        combos.add(pattern.joinToString(""))
        return
    }

    // if the current character is 'X'
    if (pattern[i] == 'X') {
        var ch = '0'
        while (ch <= '1') {

            // replace '?' with 0 and 1
            pattern[i] = ch

            // recur for the remaining pattern
            getAllCombinations(pattern, i + 1, combos)

            // backtrack as array is passed by reference to the function
            pattern[i] = 'X'
            ch++
        }
        return
    }

    // if the current character is 0 or 1, ignore it and
    // recur for the remaining pattern
    getAllCombinations(pattern, i + 1, combos)
}

main()