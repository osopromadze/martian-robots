package com.guidesmiths.martian_robot.logic;

public class LeftMovement extends Movement {
    @Override
    protected void move(Robot robot) {
        switch(robot.getCurrentOrientation()) {
            case N:
                robot.setCurrentOrientation(Orientation.W);
                break;
            case W:
                robot.setCurrentOrientation(Orientation.S);
                break;
            case S:
                robot.setCurrentOrientation(Orientation.E);
                break;
            case E:
                robot.setCurrentOrientation(Orientation.N);
                break;
        }
    }
}
