package com.guidesmiths.martian_robot.web;

import com.guidesmiths.martian_robot.dto.GetInputOutputResponse;
import com.guidesmiths.martian_robot.entity.InputOutput;
import com.guidesmiths.martian_robot.repository.InputOutputRepository;
import com.guidesmiths.martian_robot.service.RobotService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/martian-robots")
public class RobotController {

    private final RobotService robotService;
    private final Integer maxSize;
    private final InputOutputRepository inputOutputRepository;

    public RobotController(RobotService robotService,
                           @Value("${size.max}") Integer maxSize,
                           InputOutputRepository inputOutputRepository) {
        this.robotService = robotService;
        this.maxSize = maxSize;
        this.inputOutputRepository = inputOutputRepository;
    }

    @PostMapping(path = "/move", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> moveRobot(@RequestBody() String body) {

        List<String> inputLines = Arrays.stream(body.split("\r\n")).collect(Collectors.toList());

        boolean validInput = validateInputLines(inputLines);

        if(!validInput) {
            return ResponseEntity.badRequest().body("Invalid input");
        }

        String responseBody = robotService.moveRobots(inputLines);

        InputOutput entity = InputOutput.builder()
                .input(body)
                .output(responseBody)
                .build();

        inputOutputRepository.save(entity);

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping(path = "/get-input-output", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetInputOutputResponse> getInputOutput(
            @RequestParam(value = "_page", required = false) String page,
            @RequestParam(value = "_size", required = false) String size
    ) {

        int realPage = getRealPage(page);
        int realSize = getRealSize(size);

        GetInputOutputResponse responseBody = robotService.getInputOutputs(realPage, realSize);

        return ResponseEntity.ok(responseBody);
    }

    private int getRealPage(String page) {

        // if page equals to null or it is not numeric
        // return default 1
        return Optional.ofNullable(page)
                .filter(p -> StringUtils.isNumeric(p))
                .map(p -> Integer.parseInt(p))
                .orElse(1);
    }

    private int getRealSize(String size) {

        // if page equals to null or it is not numeric or it is greater than maxSize defined in properties
        // return default maxSize
        return Optional.ofNullable(size)
                .filter(s -> StringUtils.isNumeric(s))
                .map(s -> Integer.parseInt(s))
                .filter(s -> s < maxSize)
                .orElse(maxSize);
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
