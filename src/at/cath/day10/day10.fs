open System

type Coordinate = int * int
type Grid = int array array

let intAdd x y = x + y

let (+) (x1, y1) (x2, y2) = (x1 + x2, y1 + y2)
let (-) (x1, y1) (x2, y2) = (x1 - x2, y1 - y2)

let directions = [| (0, 1); (1, 0); (0, -1); (-1, 0) |]

let inBounds (grid: Grid) (coord: Coordinate) =
    let x, y = coord
    x >= 0 && x < grid.Length && y >= 0 && y < grid.[0].Length

let at (grid: Grid) (coord: Coordinate) =
    let x, y = coord
    grid.[x].[y]

let findA9 (grid: Grid) (origin: Coordinate) (task2: Boolean) : int =
    let visited = System.Collections.Generic.HashSet<Coordinate>()

    let rec walk (current: Coordinate) : int =
        let currentValue = at grid current
        if currentValue = 9 then
            if visited.Contains(current) && not task2
            then 0
            else
                visited.Add(current) |> ignore
                1
        else
            directions
            |> Array.map (fun dir -> current + dir)
            |> Array.filter (inBounds grid)
            |> Array.filter (fun nextCoord -> (at grid nextCoord) = intAdd currentValue 1)
            |> Array.sumBy walk
    walk origin


[<EntryPoint>]
let main _ =
    let grid = System.IO.File.ReadAllLines("Day10.txt")
                |> Array.map (fun line -> line.ToCharArray() |> Array.map (fun c -> Char.GetNumericValue(c) |> int))

    let findZeros =
        grid
        |> Array.mapi (fun x row ->
            row |> Array.mapi (fun y value -> (x, y, value)))
        |> Array.concat
        |> Array.filter (fun (_, _, value) -> value = 0)

    printf $"Task 1: %d{findZeros |> Array.sumBy (fun (x, y, _) -> findA9 grid (x, y) false)}\n"
    printf $"Task 2: %d{findZeros |> Array.sumBy (fun (x, y, _) -> findA9 grid (x, y) true)}\n"
    0