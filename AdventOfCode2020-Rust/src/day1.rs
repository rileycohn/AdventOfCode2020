use crate::utils::Utils;

pub struct Day1 {
}

const INPUT_FILE: &str = "src/inputs/day1.txt";

impl Day1 {
    pub(crate) fn solve(&self) -> String {
        let lines = Utils::lines_from_file(INPUT_FILE);
        for x in 0..lines.len() {
            for y in 0..lines.len() {
                for z in 0..lines.len() {
                    let x_int: i32 = lines[x].trim().parse().unwrap();
                    let y_int: i32 = lines[y].trim().parse().unwrap();
                    let z_int: i32 = lines[z].trim().parse().unwrap();
                    if x_int + y_int + z_int == 2020 {
                        return (x_int * y_int * z_int).to_string();
                    }
                }
            }
        }
        return "No Solution".to_string();
    }
}