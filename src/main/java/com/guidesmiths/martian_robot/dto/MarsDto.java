package com.guidesmiths.martian_robot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarsDto {
    private Integer sizeX;
    private Integer sizeY;
}
