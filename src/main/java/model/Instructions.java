package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to parse the string instructions into List
 */
public class Instructions {
    private Instructions() {
    }

    public static List<Instruction> retrieveInstructionsList(String instructions) {
        List<Instruction> instructionList = new ArrayList<>();

        for (char c : instructions.toCharArray()) {
            Instruction instruction = Instruction.fromInstruction(String.valueOf(c));
            if (instruction == null) {
                throw new IllegalArgumentException("Unsupported instruction type found.");
            }

            instructionList.add(instruction);
        }

        return instructionList;
    }
}
