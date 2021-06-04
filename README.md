# Martian Robots

This is my solution for ```Martian Robots``` challenge.

Live version of app is deployed on azure https://guidesmiths-martian-robots.azurewebsites.net

It has 2 endpoints and postman collection fot testing can be found here https://www.getpostman.com/collections/ba79d35a972fd8859f3e. It can be imported in postman.

 - **POST** ```/martian-robots/move-robots``` which receives request body as plain text with input and after successful validations makes output of robot positions. Also saves related information in Mongo.

 - **GET** ```/martian-robots/get-input-output``` which will list all input-outputs, optionally receives query parameters "_page" and "_size" for pagination. Sample output JSON can be found [HERE](https://github.com/osopromadze/martian-robots/blob/main/files/sample-ouput.json)

### Sample POST Request

![Sample POST Request](https://raw.githubusercontent.com/osopromadze/martian-robots/main/files/POST%20Request.png?raw=true "Sample POST Request")

## Requirements
 - Java 8 or higher
 - Maven
 - Docker
### Usage

Step 1: clone repository and go inside folder
```
git clone https://github.com/gsrai/martian-robots.git

cd martian-robots
```

Step 2: Generate .jar file

```
mvn clean install
```

Step 3: run docker compose and app will be running on http://localhost:8080
```
docker-compose up
```
To run the tests
```
mvn test
```

## Problem definition

The surface of Mars can be modelled by a rectangular grid around which robots are able to move according to instructions provided from Earth. You are to write a program that determines each sequence of robot positions and reports the final position of the robot.

A robot position consists of a grid coordinate (a pair of integers: x-coordinate followed by y-coordinate) and an orientation (N, S, E, W for north, south, east, and west). A robot instruction is a string of the letters "L", "R", and "F" which represent, respectively, the instructions:

*   Left: the robot turns left 90 degrees and remains on the current grid point.
*   Right: the robot turns right 90 degrees and remains on the current grid point.
*   Forward: the robot moves forward one grid point in the direction of the current orientation and maintains the same orientation.

The direction North corresponds to the direction from grid point (x, y) to grid point (x, y+1).

There is also a possibility that additional command types may be required in the future andprovision should be made for this.

Since the grid is rectangular and bounded (...yes Mars is a strange planet), a robot that moves "off" an edge of the grid is lost forever. However, lost robots leave a robot "scent" that prohibits future robots from dropping off the world at the same grid point. The scent is left at the last grid position the robot occupied before disappearing over the edge. An instruction to move "off" the world from a grid point from which a robot has been previously lost is simply ignored by the current robot.

### The Input

The first line of input is the upper-right coordinates of the rectangular world, the lower-left coordinates are assumed to be 0, 0.

The remaining input consists of a sequence of robot positions and instructions (two lines per robot). A position consists of two integers specifying the initial coordinates of the robot and an orientation (N, S, E, W), all separated by whitespace on one line. A robot instruction is a string of the letters "L", "R", and "F" on one line.

Each robot is processed sequentially, i.e., finishes executing the robot instructions before the next robot begins execution.

The maximum value for any coordinate is 50.

All instruction strings will be less than 100 characters in length.

### The Output

For each robot position/instruction in the input, the output should indicate the final grid position and orientation of the robot. If a robot falls off the edge of the grid the word "LOST" should be printed after the position and orientation.

### Sample Input

```
5 3
1 1 E
RFRFRFRF
3 2 N
FRRFLLFFRRFLL
0 3 W
LLFFFLFLFL
```

### Sample Output

```
1 1 E
3 3 N LOST
2 3 S
```
