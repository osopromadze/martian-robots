package com.guidesmiths.martian_robot.service;

import com.guidesmiths.martian_robot.dto.DataDto;
import com.guidesmiths.martian_robot.dto.GetInputOutputResponse;
import com.guidesmiths.martian_robot.dto.HRef;
import com.guidesmiths.martian_robot.dto.InputOutputDto;
import com.guidesmiths.martian_robot.dto.Links;
import com.guidesmiths.martian_robot.entity.InputOutput;
import com.guidesmiths.martian_robot.exception.AppException;
import com.guidesmiths.martian_robot.logic.Instruction;
import com.guidesmiths.martian_robot.logic.Location;
import com.guidesmiths.martian_robot.logic.Mars;
import com.guidesmiths.martian_robot.logic.Orientation;
import com.guidesmiths.martian_robot.logic.Robot;
import com.guidesmiths.martian_robot.repository.InputOutputRepository;
import com.guidesmiths.martian_robot.web.RobotController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class RobotService {

    private final InputOutputRepository inputOutputRepository;

    public RobotService(InputOutputRepository inputOutputRepository) {
        this.inputOutputRepository = inputOutputRepository;
    }

    public String moveRobots(List<String> inputLines) {
        List<Robot> robotList = createRobotList(inputLines);

        StringBuilder output = new StringBuilder();

        for(Robot robot : robotList) {
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
            robotLocationY = Integer.parseInt(robotLocation[1]);
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
            marsSizeY = Integer.parseInt(marsSizeArray[1]);
        } catch (NumberFormatException e) {
            throw new AppException("Invalid mars coordinates - " + Arrays.toString(marsSizeArray));
        }

        return Mars.builder()
                .sizeX(marsSizeX)
                .sizeY(marsSizeY)
                .build();
    }

    public GetInputOutputResponse getInputOutputs(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<InputOutput> all = inputOutputRepository.findAll(pageable);

        if(all.getNumberOfElements() == 0) {
            throw new AppException("No results found", HttpStatus.NOT_FOUND);
        }

        List<InputOutputDto> inputOutputList = all.stream()
                .map(d -> InputOutputDto.builder()
                        .input(d.getInput())
                        .output(d.getOutput())
                        .build())
                .collect(Collectors.toList());

        Links links = getLinks(page, size, all.getTotalPages());

        return GetInputOutputResponse.builder()
                .data(DataDto.builder()
                        .inputOutputList(inputOutputList)
                        .build())
                .links(links)
                .build();
    }

    private Links getLinks(int page, int size, int totalPages) {
        String uriFirst = buildUri(1, size);
        HRef first = HRef.builder().href(uriFirst).build();

        String uriLast = buildUri(totalPages, size);
        HRef last = HRef.builder().href(uriLast).build();

        HRef next = null;
        HRef prev = null;

        if(page > 1) {
            String uriPrev = buildUri(page - 1, size);
            prev = HRef.builder().href(uriPrev).build();
        }

        if(page != totalPages) {
            String uriNext = buildUri(page + 1, size);
            next = HRef.builder().href(uriNext).build();
        }

        return Links.builder()
                .first(first)
                .next(next)
                .prev(prev)
                .last(last)
                .build();
    }

    private String buildUri(int page, int size) {
        return linkTo(
                methodOn(RobotController.class)
                        .getInputOutput(String.valueOf(page), String.valueOf(size))
        ).toUriComponentsBuilder().toUriString();
    }
}
