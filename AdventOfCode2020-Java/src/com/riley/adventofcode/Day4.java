package com.riley.adventofcode;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(
                Paths.get("/Users/cohriley/Documents/AdventOfCode/AdventOfCode2020-Java/src/com/riley/adventofcode/day4.txt"),
                StandardCharsets.UTF_8);

        String inputAsString = new String(Files.readAllBytes(
                Paths.get("/Users/cohriley/Documents/AdventOfCode/AdventOfCode2020-Java/src/com/riley/adventofcode/day4.txt")));

        System.out.println(lines.toString());


        int answer = 0;

        String[] split = inputAsString.split("\n\n");
        System.out.println(split);

        Set<String> requ = new HashSet<>();
        requ.add("byr");
        requ.add("iyr");
        requ.add("eyr");
        requ.add("hgt");
        requ.add("hcl");
        requ.add("ecl");
        requ.add("pid");

        for (String pass : split) {
            System.out.println(pass + "\n");
            pass = pass.replace("\n", " ");
            boolean valid = true;
            for (String c : requ) {

                if (!pass.contains(c)) {
                    valid = false;
                    break;
                } else {
                    try {
                        switch (c) {
                            case "byr":
                                int byr = Integer.parseInt(getBetweenStrings(pass, "byr:", " "));
                                valid = (1920 <= byr && byr <= 2002);
                                break;
                            case "iyr":
                                int iyr = Integer.parseInt(getBetweenStrings(pass, "iyr:", " "));
                                valid = (2010 <= iyr && iyr <= 2020);
                                break;
                            case "eyr":
                                int eyr = Integer.parseInt(getBetweenStrings(pass, "eyr:", " "));
                                valid = (2020 <= eyr && eyr <= 2030);
                                break;
                            case "hgt":
                                String hgt = getBetweenStrings(pass, "hgt:", " ");
                                valid = hgt.endsWith("cm") || hgt.endsWith("in");
                                if (!valid) {
                                    break;
                                } else {
                                    if (hgt.endsWith("cm")) {
                                        int h = Integer.parseInt(getBetweenStrings(pass, "hgt:", "cm"));
                                        valid = (h >= 150 && h <= 193);
                                        break;
                                    } else {
                                        int h = Integer.parseInt(getBetweenStrings(pass, "hgt:", "in"));
                                        valid = (h >= 59 && h <= 76);
                                        break;
                                    }
                                }
                            case "hcl":
                                String hcl = getBetweenStrings(pass, "hcl:", " ");
                                String regex = "#[a-z0-9]+$";
                                Pattern pattern = Pattern.compile(regex);
                                Matcher matcher = pattern.matcher(hcl);
                                valid = matcher.find() && hcl.length() == 7 ;
                                break;
                            case "ecl":
                                String ecl = getBetweenStrings(pass, "ecl:", " ");
                                valid = (ecl.equals("amb") || ecl.equals("blu") || ecl.equals("brn") ||
                                        ecl.equals("gry") || ecl.equals("grn") || ecl.equals("hzl") || ecl.equals("oth"));
                                break;
                            case "pid":
                                String pid = getBetweenStrings(pass, "pid:", " ");
                                Integer.parseInt(pid);
                                valid = pid.length() == 9;
                        }
                    } catch (Exception e) {
                        valid = false;
                    }
                }

                if (!valid) {
                    break;
                }
            }

            if (valid) {
                System.out.println("valid");
                answer++;
            }
        }

        System.out.println(answer);

    }

    public static String getBetweenStrings(
            String text,
            String textFrom,
            String textTo) {

        String result = "";

        // Cut the beginning of the text to not occasionally meet a
        // 'textTo' value in it:
        result =
                text.substring(
                        text.indexOf(textFrom) + textFrom.length(),
                        text.length());

        // Cut the excessive ending of the text:
        try {
            result =
                    result.substring(
                            0,
                            result.indexOf(textTo));
        } catch (Exception e) {
            result = result.substring(
                    0,
                    result.length());
        }


        return result;
    }
}
