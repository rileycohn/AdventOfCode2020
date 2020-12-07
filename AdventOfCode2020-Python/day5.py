import math

input = open("day5.txt", "r")
lines = input.readlines()

highestId = 0
ids = []
for line in lines:
    front = 0
    back = 127
    left = 0
    right = 7
    count = 0
    row = 0
    column = 0
    for char in line:
        if count == 6:
            if char == 'F':
                row = front
            elif char == 'B':
                row = back
        elif count == 9:
            if char == 'L':
                column = left
            elif char == 'R':
                column = right
        else:
            if char == 'F':
                back = int(math.floor((front + back) / 2))
            elif char == 'B':
                front = int(math.ceil((front + back) / 2))
            elif char == 'L':
                right = int(math.floor((left + right) / 2))
            elif char == 'R':
                left = int(math.ceil((left + right) / 2))
        count = count + 1
    id = (row * 8) + column
    ids.append(id)

# iterate the ids list and find the missing id
ids.sort()
prev = 0
for id in ids:
    if prev == 0:
        prev = id
    else:
        if id != (prev + 1):
            print("id: " + str(id - 1))
            break
        else:
            prev = id
