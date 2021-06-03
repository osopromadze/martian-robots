package com.guidesmiths.martian_robot.logic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Scent {

    private Location location;
    private Orientation orientation;
}
