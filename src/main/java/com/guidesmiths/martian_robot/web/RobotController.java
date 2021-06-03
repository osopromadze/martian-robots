package com.guidesmiths.martian_robot.web;

import com.guidesmiths.martian_robot.service.RobotService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/martian-robots")
public class RobotController {

    private final RobotService robotService;

    public RobotController(RobotService robotService) {
        this.robotService = robotService;
    }

    @PostMapping(path = "/move", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> moveRobot(@RequestBody() String body) {

        List<String> inputLines = Arrays.stream(body.split("\r\n")).collect(Collectors.toList());

        boolean validInput = validateInputLines(inputLines);

        if(!validInput) {
            return ResponseEntity.badRequest().body("Invalid input");
        }

        String responseBody = robotService.moveRobots(inputLines);

        return ResponseEntity.ok(responseBody);
    }

    private boolean validateInputLines(List<String> inputLines) {

        // if input lines count is not odd or less than 3
        if(inputLines.size() % 2 == 0 || inputLines.size() < 3) {
            return false;
        }

        // first line must be mars upper-right coordinates
        // it is invalid input if we have more than 2 values on line, because we need only X and Y
        if(inputLines.get(0).split(" ").length != 2) {
            return false;
        }

        // every second line must be robot positions
        // it is invalid input if we have more than 2 values, because we need only X, Y and orientation
        for(int i = 1; i < inputLines.size(); i++) {
            if(i % 2 != 0 && inputLines.get(i).split(" ").length != 3) {
                return false;
            }
        }

        return true;
    }
}
