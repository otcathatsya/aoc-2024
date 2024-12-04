import re

with open("Day03.txt") as f:
    test = f.read()

sum = 0
skip = False
for match in re.finditer(r"mul\((\d+),(\d+)\)|do\(\)|don\'t\(\)", test):
    if match.group(0) == 'do()':
        skip = False
    elif match.group(0) == 'don\'t()':
        skip = True
    elif not skip:
        sum += int(match.group(1)) * int(match.group(2))

print(f"Sum: {sum}")
