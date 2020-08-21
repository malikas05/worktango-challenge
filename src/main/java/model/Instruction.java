package model;

/**
 * This class is used for single instruction
 */
public enum Instruction {
    LEFT("L"),
    RIGHT("R"),
    MOVE("M");

    private String instruction;

    Instruction(String instruction) {
        this.instruction = instruction;
    }

    public static Instruction fromInstruction(String strValue) {
        return fromInstruction(strValue, null);
    }

    private static Instruction fromInstruction(String strValue, Instruction defaultEnumValue) {
        if (strValue == null) {
            return defaultEnumValue;
        }

        for (Instruction i : values()) {
            if (i.instruction.equals(strValue)) {
                return i;
            }
        }

        return defaultEnumValue;
    }
}
