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

    // Directions array
    $directions = array();
    array_push($directions, array(-1, -1));
    array_push($directions, array(0, -1));
    array_push($directions, array(1, 1));
    array_push($directions, array(-1, 0));
    array_push($directions, array(1, 0));
    array_push($directions, array(-1, 1));
    array_push($directions, array(0, 1));
    array_push($directions, array(1, -1));



    // Loop
    while(true) {
        // Create copy
        $new_copy = $seats;
        $did_change = false;

        for ($row = 0; $row < count($seats); $row++) {
            for ($column = 0; $column < count($seats[$row]); $column++) {
                $curr_seat = $seats[$row][$column];
                // ignore floor seats
                if ($curr_seat == 'L') {
                    // Check for adjacent seats.
                    // If any are occupied, do nothing
                    // If they are all open, change to #
                    $all_free = true;
                    foreach ($directions as $dir) {
                        $dirx = $dir[0];
                        $diry = $dir[1];
                        while (true) {
                            $val = $seats[$row + $dirx][$column + $diry];
                            if ($val && $val != '.') {
                                if ($val == '#') {
                                    $all_free = false;
                                }

                                break;
                            } else {
                                if ($dirx != 0) {
                                    if ($dirx < 0) {
                                        $dirx--;
                                    } else {
                                        $dirx++;
                                    }
                                    if ($row + $dirx >= count($seats) || $row + $dirx < 0) {
                                        break;
                                    }
                                }
                                if ($diry != 0) {
                                    if ($diry < 0) {
                                        $diry--;
                                    } else {
                                        $diry++;
                                    }
                                    if ($column + $diry >= count($seats[$row]) || $column + $diry < 0) {
                                        break;
                                    }
                                }
                            }
                        }

                        if (!$all_free) {
                            break;
                        }
                    }

                    if ($all_free) {
                        $new_copy[$row][$column] = '#';
                        $did_change = true;
                    }
                } else if ($curr_seat == '#') {
                    // If there are 5 #'s adjacent, change to L
                    $total_occ = 0;
                    foreach ($directions as $dir) {
                        $dirx = $dir[0];
                        $diry = $dir[1];
                        while (true) {
                            $val = $seats[$row + $dirx][$column + $diry];
                            if ($val && $val != '.') {
                                if ($val == '#') {
                                    $total_occ++;
                                }
                                break;
                            } else {
                                if ($dirx != 0) {
                                    if ($dirx < 0) {
                                        $dirx--;
                                    } else {
                                        $dirx++;
                                    }
                                    if ($row + $dirx >= count($seats) || $row + $dirx < 0) {
                                        break;
                                    }
                                }
                                if ($diry != 0) {
                                    if ($diry < 0) {
                                        $diry--;
                                    } else {
                                        $diry++;
                                    }
                                    if ($column + $diry >= count($seats[$row]) || $column + $diry < 0) {
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if ($total_occ > 4) {
                        $new_copy[$row][$column] = 'L';
                        $did_change = true;
                    }
                }
            }
        }

        if (!$did_change) {
            break;
        }

        $seats = $new_copy;

        for ($x = 0; $x <= count($seats); $x++) {
            for ($y = 0; $y <= count($seats[$x]); $y++) {
                echo $seats[$x][$y];
            }
            echo "\n";
        }

        echo "\n";
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