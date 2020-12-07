package com.riley.adventofcode;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day5 {

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(
                Paths.get("/Users/cohriley/Documents/AdventOfCode/AdventOfCode2020-Java/src/com/riley/adventofcode/day5.txt"),
                StandardCharsets.UTF_8);

        String inputAsString = new String(Files.readAllBytes(
                Paths.get("/Users/cohriley/Documents/AdventOfCode/AdventOfCode2020-Java/src/com/riley/adventofcode/day5.txt")));

        System.out.println(lines.toString());


        int highestSeat = 0;
        int row = 0;
        for (String line: lines) {
            int bottom = 0;
            int top = 127;
            for (int i = 0; i < 7; i++) {

                if (line.charAt(i) == 'F') {
                    top = Math.floorDiv(top + bottom, 2);
                } else if (line.charAt(i) == 'B') {
                    bottom =  Math.floorDiv(top + bottom, 2);
                }

                if (i == 6) {
                    if (line.charAt(i) == 'F') {
                        row = top;
                    } else if (line.charAt(i) == 'B') {
                        row = bottom;
                    }

                    break;
                }
            }

            int left = 0;
            int right = 7;
            int column = 0;
            for (int j = 7; j < 10; j++) {
                if (j == 9) {
                    if (line.charAt(j) == 'L') {
                        column = left;
                    } else if (line.charAt(j) == 'R') {
                        column = right;
                    }
                    break;
                }

                if (line.charAt(j) == 'R') {
                    left =  Math.floorDiv(left + right, 2) + 1;
                } else if (line.charAt(j) == 'L') {
                    right =  Math.floorDiv(left + right, 2);
                }


            }

            int id = ((row * 8) + column);
            if (id > highestSeat) {
                highestSeat = id;
            }
            System.out.println("Seat iD: " + ((row * 8) + column));
        }


        System.out.println("Answer: " + highestSeat);

    }
}
