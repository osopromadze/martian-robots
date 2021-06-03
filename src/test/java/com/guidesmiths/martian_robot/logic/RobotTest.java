package com.guidesmiths.martian_robot.logic;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RobotTest {

    private Mars mars;

    @BeforeAll
    void setUp() {
        mars = Mars.builder()
                .sizeX(5)
                .sizeY(3)
                .build();
    }

    @Test
    void robot1MovementTest() {

        Robot robot = Robot.builder()
                .mars(mars)
                .location(Location.builder()
                        .x(1)
                        .y(1)
                        .build())
                .currentOrientation(Orientation.E)
                .instructions("RFRFRFRF")
                .build();

        robot.move();

        assertEquals(1, robot.getLocation().getX());
        assertEquals(1, robot.getLocation().getY());
        assertEquals(Orientation.E, robot.getCurrentOrientation());
        assertFalse(robot.isLost());
    }

    @Test
    void robot2MovementTest() {
        Mars mars = Mars.builder()
                .sizeX(5)
                .sizeY(3)
                .build();

        Robot robot = Robot.builder()
                .mars(mars)
                .location(Location.builder()
                        .x(3)
                        .y(2)
                        .build())
                .currentOrientation(Orientation.N)
                .instructions("FRRFLLFFRRFLL")
                .build();

        robot.move();

        assertEquals(3, robot.getLocation().getX());
        assertEquals(3, robot.getLocation().getY());
        assertEquals(Orientation.N, robot.getCurrentOrientation());
        assertTrue(robot.isLost());
    }

    @Test
    void robot3MovementTest() {
        Mars mars = Mars.builder()
                .sizeX(5)
                .sizeY(3)
                .build();

        Robot robot = Robot.builder()
                .mars(mars)
                .location(Location.builder()
                        .x(2)
                        .y(3)
                        .build())
                .currentOrientation(Orientation.S)
                .instructions("LLFFFLFLFL")
                .build();

        robot.move();

        assertEquals(2, robot.getLocation().getX());
        assertEquals(3, robot.getLocation().getY());
        assertEquals(Orientation.N, robot.getCurrentOrientation());
        assertTrue(robot.isLost());
    }
}