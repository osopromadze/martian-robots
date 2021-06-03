package com.guidesmiths.martian_robot.logic;

public enum Instruction {
    R,
    L,
    F;

    public static boolean contains(String instruction) {

        for(Instruction i : Instruction.values()) {
            if(i.name().equals(instruction)) {
                return true;
            }
        }

        return false;
    }
}
