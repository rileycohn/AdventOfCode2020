import groovy.transform.EqualsAndHashCode

def lines = new File("src/Day17.txt").collect {it}

@EqualsAndHashCode
class Position {
    Position(row, col, z, w) {
        this.row = row
        this.col = col
        this.z = z
        this.w = w
    }
    int row
    int col
    int z
    int w
}

// Get the grid of active positions
Set activeCells = new HashSet<Position>()
for (int row = 0; row < lines.size(); row++) {
    for (int col = 0; col < lines[row].size(); col++) {
        if (lines[row][col] == "#") {
            activeCells.add(new Position(row, col, 0, 0))
        }

    }
}

// Perform 6 boot cycles
for (int i in 1..6) {
    // Used for tracking which cells are active after this iteration
    Set nextActiveSet = new HashSet<Position>()

    // Need to check currently active cells neighbors
    Set neighborsToCheck = new HashSet<Position>()
    // Check if active cells should remain active
    for (cell in activeCells) {
        int activeCount = 0
        for(int dx in -1..1) {
            for (int dy in -1..1) {
                for (int dz in -1..1) {
                    for (int dw in -1..1) {
                        // Don't count itself
                        if (dx == 0 && dy == 0 && dz == 0 && dw == 0) {
                            continue
                        }
                        Position p = new Position(cell.row + dx, cell.col + dy, cell.z + dz, cell.w + dw);
                        if (activeCells.contains(p)) {
                            activeCount++
                        } else {
                            neighborsToCheck.add(p)
                        }
                    }
                }
            }
        }
        if (activeCount == 2 || activeCount == 3) {
            nextActiveSet.add(new Position(cell.row, cell.col, cell.z, cell.w))
        }
    }

    // Now check the neighbors
    for (cell in neighborsToCheck) {
        int activeCount = 0
        for(int dx in -1..1) {
            for (int dy in -1..1) {
                for (int dz in -1..1) {
                    for (int dw in -1..1) {
                        // Don't count itself
                        if (dx == 0 && dy == 0 && dz == 0 && dw == 0) {
                            continue
                        }
                        Position p = new Position(cell.row + dx, cell.col + dy, cell.z + dz, cell.w + dw)
                        if (activeCells.contains(p)) {
                            activeCount++
                        }
                    }
                }
            }
        }
        if (activeCount == 3) {
            nextActiveSet.add(new Position(cell.row, cell.col, cell.z, cell.w))
        }
    }

    activeCells = nextActiveSet
}

println "Answer: " + activeCells.size()
