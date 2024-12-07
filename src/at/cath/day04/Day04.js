const fs = require('fs');

const input = fs.readFileSync('Day04.txt', 'utf8');
const grid = input.trim().split('\n').map(line => line.split(''));

const letterbox = ['M', 'A', 'S'];

const DIRECTIONS = [
    [0, 1],   // right
    [1, 0],   // down
    [0, -1],  // left
    [-1, 0],  // up
    [1, 1],   // down right
    [1, -1],  // down left
    [-1, 1],  // up right
    [-1, -1]  // up left
];

// helper method to check if x,y is in bounds of grid
function inBounds(x, y) {
    return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
}

function scanNext(grid, currentX, currentY, letterboxIndex, dir) {
    if (letterboxIndex === letterbox.length) {
        return 1;
    }

    const [dx, dy] = dir;
    let x = currentX + dx;
    let y = currentY + dy;

    if (!inBounds(x, y) || grid[x][y] !== letterbox[letterboxIndex]) {
        return 0;
    }

    return scanNext(grid, x, y, letterboxIndex + 1, dir);
}

function part1() {
    let xmasCounter = 0;
    for (let row = 0; row < grid.length; row++) {
        for (let col = 0; col < grid[row].length; col++) {
            if (grid[row][col] === 'X') {
                xmasCounter += DIRECTIONS.reduce((acc, dir) => {
                    return acc + scanNext(grid, row, col, 0, dir);
                }, 0);
            }
        }
    }
    return xmasCounter
}

function part2() {
    let xmasCounter = 0
    for (let row = 0; row < grid.length; row++) {
        for (let col = 0; col < grid[row].length; col++) {
            if (grid[row][col] !== 'A')
                continue

            if (!inBounds(row + 1, col + 1) || !inBounds(row - 1, col - 1)
                || !inBounds(row + 1, col - 1) || !inBounds(row - 1, col + 1))
                continue

            const diagA = grid[row + 1][col + 1];
            const diagB = grid[row - 1][col - 1];

            const diagC = grid[row + 1][col - 1];
            const diagD = grid[row - 1][col + 1];

            const anyAorX = [diagA, diagB, diagC, diagD].some(letter => letter === 'A' || letter === 'X')
            if (anyAorX)
                continue

            if (diagA !== diagB && diagC !== diagD)
                xmasCounter++
        }
    }
    return xmasCounter
}

function day04() {
    console.log("Part 1:", part1());
    console.log("Part 2:", part2());
}

day04()