package com.guidesmiths.martian_robot.dto;

import com.guidesmiths.martian_robot.logic.Scent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InputOutputDto {
    private String input;
    private String output;
    private Integer robotsCount;
    private Long lostRobotsCount;
    private MarsDto mars;
    private List<RobotDto> robots;
    private List<Scent> scents;
}
