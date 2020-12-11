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

	var oneJolt = 0
	var threeJolts = 0

	sort.Ints(lines)

	var prev = 0
	for _, line := range lines {
		if line - prev == 1 {
			oneJolt++
		} else {
			threeJolts++
		}
		prev = line
	}

	// Adapter can do 3 more jolts
	threeJolts++

	fmt.Println(oneJolt)
	fmt.Println(threeJolts)
	fmt.Println(oneJolt * threeJolts)
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