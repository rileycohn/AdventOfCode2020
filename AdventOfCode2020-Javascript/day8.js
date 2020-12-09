const fs = require("fs");

const text = fs.readFileSync("./day8.txt", "utf-8");
const lines = text.split("\n");

let whichActionToSwitch = 0;

while (true) {
    let acc = 0;
    let visited = new Set();
    let howManySeen = 0;
    let isWinner = true;

    for (let i = 0; i < lines.length; i++) {
        // This signifies an infinite loop
        // So we failed, move on.
        if (visited.has(i)) {
            isWinner = false;
            break;
        }
        visited.add(i);

        let line = lines[i];
        // Break string into 3 parts. Action, +/-, number
        let split = line.split(" ")
        let action = split[0];
        let number = parseInt(split[1]);

        if (whichActionToSwitch === howManySeen) {
            if (action === "nop") {
                action = "jmp";
            } else if (action === "jmp") {
                action = "nop";
            }
        }

        switch (action) {
            case "acc":
                acc += number;
                break;
            case "nop":
                howManySeen++;
                continue;
            case "jmp":
                howManySeen++;
                i = i + (number - 1);
                break;
        }
    }

    if (isWinner) {
        console.log("Answer: " + acc);
        break;
    }

    whichActionToSwitch++;
}


