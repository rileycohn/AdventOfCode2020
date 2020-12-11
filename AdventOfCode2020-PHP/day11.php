<?php
$seats = array();
$handle = fopen("day11.txt", "r");
if ($handle) {
    $i=0;
    while (($line = fgets($handle)) !== false) {
        $line = trim($line);
        $chars = str_split($line);
        $seats[$i] = $chars;
        $i++;
    }
    fclose($handle);

    // Now we have the seats in a 2d array

    // Loop
    while(true) {
        // Create copy
        $new_copy = $seats;
        $did_change = false;

        for ($x = 0; $x <= count($seats); $x++) {
            for ($y = 0; $y <= count($seats[$x]); $y++) {
                $curr_seat = $seats[$x][$y];
                // ignore floor seats
                if ($curr_seat == 'L') {
                    // Check for adjacent seats.
                    // If any are occupied, do nothing
                    // If they are all open, change to #
                    $all_free = true;
                    for ($dx = -1; $dx <= 1; $dx++) {
                        for ($dy = -1; $dy <= 1; $dy++) {
                            if ($seats[$x + $dx][$y + $dy] == '#') {
                                $all_free = false;
                                break;
                            }
                        }

                        if (!$all_free) {
                            break;
                        }
                    }

                    if ($all_free) {
                        $new_copy[$x][$y] = '#';
                        $did_change = true;
                    }
                } else if ($curr_seat == '#') {
                    // If there are 4 L's adjacent, change to L
                    $total_occupied = 0;
                    for ($dx = -1; $dx <= 1; ++$dx) {
                        for ($dy = -1; $dy <= 1; ++$dy) {
                            if ($dx != 0 || $dy != 0) {
                                if ($seats[$x + $dx][$y + $dy] == '#') {
                                    $total_occupied++;
                                }

                            }
                        }
                    }

                    if ($total_occupied > 3) {
                        $new_copy[$x][$y] = 'L';
                        $did_change = true;
                    }
                }
            }
        }

        if (!$did_change) {
            break;
        }

        $seats = $new_copy;
    }

    // Now iterate seats one more time and count open seats
    $open_seats = 0;
    for ($x = 0; $x <= count($seats); $x++) {
        for ($y = 0; $y <= count($seats[$x]); $y++) {
            if ($seats[$x][$y] == '#') {
                $open_seats++;
            }
        }
    }

    echo $open_seats;
}