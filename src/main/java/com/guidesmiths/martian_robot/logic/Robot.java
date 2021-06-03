package com.guidesmiths.martian_robot.logic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Robot {

    private Mars mars;
    private Location location;
    private Orientation currentOrientation;
    private boolean isLost;
    private String instructions;

    private final Map<Instruction, Movement> movementMap = new HashMap<Instruction, Movement>() {{
        put(Instruction.R, new RightMovement());
        put(Instruction.L, new LeftMovement());
        put(Instruction.F, new ForwardMovement());
    }};

    public void move() {

        // create list of instructions to loop on them later
        List<Instruction> instructions = Arrays.stream(this.instructions.split(""))
                .map(i -> Instruction.valueOf(i))
                .collect(Collectors.toList());

        // for every instruction get movement handler from map depending on its key and run it
        for(Instruction instruction : instructions) {

            // if robot is lost stop any next movement, add scent to mars, later it will be used by other robots
            if(isLost) {
                Scent scent = Scent.builder()
                        .location(location)
                        .orientation(currentOrientation)
                        .build();

                mars.getScents().add(scent);


                // if robot is lost and location points are out of mars surface, set them to maximum mars size for X or Y
                if(location.getY() > mars.getSizeY()) {
                    location.setY(mars.getSizeY());
                }

                if(location.getX() > mars.getSizeX()) {
                    location.setX(mars.getSizeX());
                }

                break;
            }

            movementMap.get(instruction).move(this);

            checkIfRobotLost();
        }
    }

    private void checkIfRobotLost() {
        isLost = location.getX() > mars.getSizeX()
                || location.getX() < 0
                || location.getY() > mars.getSizeY()
                || location.getY() < 0;
    }
}
