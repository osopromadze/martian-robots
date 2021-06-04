package com.guidesmiths.martian_robot.entity;

import com.guidesmiths.martian_robot.dto.MarsDto;
import com.guidesmiths.martian_robot.dto.RobotDto;
import com.guidesmiths.martian_robot.logic.Scent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "input_output")
@TypeAlias("Input-Output")
public class InputOutput {

    @Id
    private String id;
    private String input;
    private String output;
    private Integer robotsCount;
    private Long lostRobotsCount;
    private MarsDto mars;
    private List<RobotDto> robots;
    private List<Scent> scents;

}
