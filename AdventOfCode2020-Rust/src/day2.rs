use crate::utils::Utils;

pub struct Day2 {
}

const INPUT_FILE: &str = "src/inputs/day2.txt";

impl Day2 {
    pub(crate) fn solve(&self) -> String {
        let mut total: i32 = 0;
        let lines = Utils::lines_from_file(INPUT_FILE);
        for x in 0..lines.len() {
            let mut split_space = lines[x].split(" ");
            let range = split_space.next();
            let letter = split_space.next();
            let pass = split_space.next();
            println!("{} {} {}", range, letter, pass);
        }

        return total.to_string();
    }
}