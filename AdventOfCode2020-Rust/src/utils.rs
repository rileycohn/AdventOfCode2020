use std::path::Path;
use std::fs::File;
use std::io::{BufReader, BufRead};

pub struct Utils {
}

impl Utils {
    pub fn lines_from_file(filename: impl AsRef<Path>) -> Vec<String> {
        let file = File::open(filename).expect("no such file");
        let buf = BufReader::new(file);
        buf.lines()
            .map(|l| l.expect("Could not parse line"))
            .collect()
    }
}