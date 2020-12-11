package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"sort"
	"strconv"
)

func main() {
	lines, err := readLines("day10.txt")
	if err != nil {
		log.Fatalf("readLines: %s", err)
	}

	lines = append(lines, 0)

	sort.Ints(lines)
	lines = append(lines, lines[len(lines) - 1] + 3)
	sort.Ints(lines)

	paths := make(map[int]int)
	paths[0] = 1
	for i := 0; i < len(lines); i++ {
		var curr = lines[i]
		// Check up to 3 places forward to see how many are valid
		for j := 1; j < 4; j++ {
			if i + j < len(lines) && lines[i + j] - curr <= 3 {
				paths[i + j] += paths[i]
			}
		}
	}

	fmt.Println(paths[len(lines) - 1])
}

func readLines(path string) ([]int, error) {
	file, err := os.Open(path)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	var lines []int
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		n, _ := strconv.Atoi(scanner.Text())
		lines = append(lines, n)
	}
	return lines, scanner.Err()
}