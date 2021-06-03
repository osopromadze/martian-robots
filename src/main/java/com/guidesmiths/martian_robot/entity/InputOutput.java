package com.guidesmiths.martian_robot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

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

}
