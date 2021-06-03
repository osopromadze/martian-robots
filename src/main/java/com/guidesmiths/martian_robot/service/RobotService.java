package com.guidesmiths.martian_robot.service;

import com.guidesmiths.martian_robot.exception.AppException;
import com.guidesmiths.martian_robot.logic.Instruction;
import com.guidesmiths.martian_robot.logic.Location;
import com.guidesmiths.martian_robot.logic.Mars;
import com.guidesmiths.martian_robot.logic.Orientation;
import com.guidesmiths.martian_robot.logic.Robot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RobotService {

    public String moveRobots(List<String> inputLines) {
        List<Robot> robotList = createRobotList(inputLines);

        StringBuilder output = new StringBuilder();

        for(Robot robot: robotList) {
            robot.move();

            output.append(robot.getLocation().getX());
            output.append(" ");
            output.append(robot.getLocation().getY());
            output.append(" ");
            output.append(robot.getCurrentOrientation());

            if(robot.isLost()) {
                output.append(" ");
                output.append("LOST");
            }

            output.append("\n");
        }

        return output.toString();
    }

    private List<Robot> createRobotList(List<String> inputLines) {

        List<Robot> robotList = new ArrayList<>();

        Mars mars = createMars(inputLines.get(0));

        for(int i = 1; i < inputLines.size(); i += 2) {
            String[] robotLocation = inputLines.get(i).split(" ");


            Location location = createLocation(robotLocation);
            Orientation orientation = createOrientation(robotLocation);

            String instructions = inputLines.get(i + 1);
            validateInstructions(instructions);

            Robot robot = Robot.builder()
                    .mars(mars)
                    .location(location)
                    .currentOrientation(orientation)
                    .instructions(instructions)
                    .build();

            robotList.add(robot);
        }

        return robotList;
    }

    private void validateInstructions(String instructions) {
        Arrays.stream(instructions.split(""))
                .forEach(i -> {
                    if(!Instruction.contains(i)) {
                        throw new AppException("Invalid instructions - " + instructions);
                    }
                });
    }

    private Location createLocation(String[] robotLocation) {

        int robotLocationX;
        int robotLocationY;

        try {
            robotLocationX = Integer.parseInt(robotLocation[0]);
            robotLocationY= Integer.parseInt(robotLocation[1]);
        } catch (NumberFormatException e) {
            throw new AppException("Invalid robot positions - " + Arrays.toString(robotLocation));
        }

        return Location.builder()
                .x(robotLocationX)
                .y(robotLocationY)
                .build();
    }

    private Orientation createOrientation(String[] robotLocation) {
        try {
            return Orientation.valueOf(robotLocation[2]);
        } catch (IllegalArgumentException e) {
            throw new AppException("Invalid robot orientation - " + Arrays.toString(robotLocation));
        }
    }


    private Mars createMars(String size) {
        String[] marsSizeArray = size.split(" ");

        int marsSizeX;
        int marsSizeY;

        try {
            marsSizeX = Integer.parseInt(marsSizeArray[0]);
            marsSizeY= Integer.parseInt(marsSizeArray[1]);
        } catch (NumberFormatException e) {
            throw new AppException("Invalid mars coordinates - " + Arrays.toString(marsSizeArray));
        }

        return Mars.builder()
                .sizeX(marsSizeX)
                .sizeY(marsSizeY)
                .build();
    }
}
