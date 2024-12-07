#include <vector>
#include <iostream>
#include <fstream>

using Coordinate = std::pair<int, int>;
using Grid = std::vector<std::vector<char>>;

inline char &at(Grid &grid, Coordinate loc) {
    return grid[loc.first][loc.second];
}

Coordinate operator+(Coordinate a, Coordinate b) {
    return {a.first + b.first, a.second + b.second};
}

constexpr Coordinate DIRS[4] = {
        {-1, 0},  // Up
        {0,  1},   // Right
        {1,  0},   // Down
        {0,  -1}   // Left
};

bool inBounds(const Grid &grid, Coordinate loc) {
    return loc.first >= 0 && loc.first < grid.size() &&
           loc.second >= 0 && loc.second < grid[loc.first].size();
}

int part1(Grid &grid, Coordinate loc, int dirIndex, int steps) {
    at(grid, loc) = 'X';
    Coordinate nextLoc = loc + DIRS[dirIndex];

    if (!inBounds(grid, nextLoc)) return steps;  // Walked off-map
    if (at(grid, nextLoc) == '#') return part1(grid, loc, (dirIndex + 1) % 4, steps);  // Hit obstacle
    if (at(grid, nextLoc) != 'X') steps++;
    return part1(grid, nextLoc, dirIndex, steps);
}

int checkLoop(Grid &grid, Coordinate loc, int dirIndex) {
    Coordinate nextLoc = loc + DIRS[dirIndex];
    if (!inBounds(grid, nextLoc)) return 0;  // Walked off-map

    char &cell = at(grid, loc);
    if (at(grid, nextLoc) == '#') {
        // we only need to check the infinite loop conditions when a corner is hit
        if (cell >= 1 && cell < 5) cell++;
        else if (cell == 5) return 1;
        else cell = 1;

        return checkLoop(grid, loc, (dirIndex + 1) % 4); // Hit obstacle
    }
    return checkLoop(grid, nextLoc, dirIndex);
}

int part2(Grid &grid, Coordinate loc, int dirIndex, int obstacles, bool firstStep) {
    at(grid, loc) = 'X';
    Coordinate nextLoc = loc + DIRS[dirIndex];

    if (!inBounds(grid, nextLoc)) return obstacles;  // Walked off-map
    if (at(grid, nextLoc) == '#') return part2(grid, loc, (dirIndex + 1) % 4, obstacles, false);  // Hit obstacle

    if (!firstStep && at(grid, nextLoc) != 'X') {
        Grid gridCopy = grid;
        at(gridCopy, nextLoc) = '#';
        obstacles += checkLoop(gridCopy, loc, (dirIndex + 1) % 4);
    }
    return part2(grid, nextLoc, dirIndex, obstacles, false);
}

int main() {
    std::ifstream file("Day06.txt");
    Grid grid;
    Coordinate startLoc;
    std::string line;

    for (int row = 0; std::getline(file, line); ++row) {
        std::vector<char> rowVec;
        for (int col = 0; col < line.size(); ++col) {
            if (line[col] == '^') startLoc = {row, col};
            rowVec.push_back(line[col]);
        }
        grid.push_back(rowVec);
    }

    Grid grid2 = grid;  // yeah
    std::cout << "Steps: " << part1(grid, startLoc, 0, 1) << "\n";
    std::cout << "Loops: " << part2(grid2, startLoc, 0, 0, true) << "\n";
    return 0;
}
