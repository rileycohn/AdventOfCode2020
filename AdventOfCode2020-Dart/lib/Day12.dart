import 'dart:io';

var compass = ['N', 'E', 'S', 'W'];

// Starting positions
var currX = 0;
var currY = 0;
var wayPoint = Coords(10, 1);
var currDir = 'E';

Future<int> calculate() async {
  var file = File('lib/Day12.txt');

  // Read file
  var directions = await file.readAsLinesSync();

  // Loop through instructions
  for (var i = 0; i < directions.length; i++) {
    print(directions[i]);
    var instruction = directions[i];
    var action = instruction[0];
    var number = int.parse(instruction.substring(1));
    getNewCoords(action, number);

    print('CurrX: ${currX}. CurrY: ${currY}');

  }

  // Find Manhattan distance
  var manX = currX.abs();
  var manY = currY.abs();

  return manX + manY;
}

Coords getNewCoords(action, number) {
  switch (action) {
    case 'N':
      wayPoint.y = wayPoint.y + number;
      break;
    case 'S':
      wayPoint.y = wayPoint.y - number;
      break;
    case 'E':
      wayPoint.x = wayPoint.x + number;
      break;
    case 'W':
      wayPoint.x = wayPoint.x - number;
      break;
    case 'L':
      getNewDir('L', number);
      break;
    case 'R':
      getNewDir('R', number);
      break;
    case 'F':
      currX = currX + (wayPoint.x * number);
      currY = currY + (wayPoint.y * number);
      break;
  }

  return Coords(currX, currY);
}

class Coords {
  int x;
  int y;

  Coords(x, y) {
    this.x = x;
    this.y = y;
  }
}

void getNewDir(turnDir, degrees) {
  var prevX = wayPoint.x;
  var prevY = wayPoint.y;
  switch (turnDir) {
    case 'R':
      switch (degrees) {
        case 90:
          wayPoint.x = prevY;
          wayPoint.y = prevX * -1;
          break;
        case 180:
          wayPoint.x = prevX * -1;
          wayPoint.y = prevY * -1;
          break;
        case 270:
          wayPoint.x = prevY * -1;
          wayPoint.y = prevX;
          break;
      }
      break;
    case 'L':
      switch (degrees) {
        case 90:
          wayPoint.x = prevY * -1;
          wayPoint.y = prevX;
          break;
        case 180:
          wayPoint.x = prevX * -1;
          wayPoint.y = prevY * -1;
          break;
        case 270:
          wayPoint.x = prevY;
          wayPoint.y = prevX * -1;
          break;
      }
      break;
  }
}