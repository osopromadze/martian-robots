package com.guidesmiths.martian_robot.logic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mars {

    private Integer sizeX;
    private Integer sizeY;
    private final List<Scent> scents = new ArrayList<>();
    private final List<Orientation> orientationsList = Arrays.asList(
            Orientation.N,
            Orientation.E,
            Orientation.S,
            Orientation.W
    );

    public boolean hasScentInLocationAndOrientation(Location location, Orientation orientation) {

        Scent possibleScent = Scent.builder()
                .location(location)
                .orientation(orientation)
                .build();

        return scents.stream()
                .anyMatch(scent -> scent.equals(possibleScent));
    }

}
