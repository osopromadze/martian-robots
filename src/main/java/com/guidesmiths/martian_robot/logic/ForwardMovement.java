package com.guidesmiths.martian_robot.logic;

public class ForwardMovement extends Movement {
    @Override
    protected void move(Robot robot) {

        boolean isScentInCurrentLocation = robot.getMars()
                .hasScentInLocationAndOrientation(robot.getLocation(), robot.getCurrentOrientation());

        // if there is scent on current location, ignore movement
        if(isScentInCurrentLocation) {
            return;
        }

        switch(robot.getCurrentOrientation()) {
            case N:
                robot.getLocation().incrementY();
                break;

            case E:
                robot.getLocation().incrementX();
                break;

            case S:
                robot.getLocation().decrementY();
                break;

            case W:
                robot.getLocation().decrementX();
                break;
        }

        robot.checkIfRobotLost();
    }
}
