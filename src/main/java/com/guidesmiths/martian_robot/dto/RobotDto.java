package com.guidesmiths.martian_robot.dto;

import com.guidesmiths.martian_robot.logic.Location;
import com.guidesmiths.martian_robot.logic.Orientation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RobotDto {
    private Location location;
    private Orientation currentOrientation;
    private boolean isLost;
    private String instructions;
}
