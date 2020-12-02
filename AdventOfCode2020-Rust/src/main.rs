use crate::day1::Day1;
use std::io;
use crate::day2::Day2;

mod utils;
mod day1;

fn main() {

    let day = get_day();

    match day {
        1 => {
            let day1 = Day1 {};
            println!("Solution: {}", day1.solve());
        }
        _ => {}
    }
}

fn get_day() -> i32 {
    let mut day = String::new();

    io::stdin()
        .read_line(&mut day)
        .expect("Failed to read day");

    return day.trim().parse().unwrap();
}
