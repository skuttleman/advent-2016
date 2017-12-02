package advent_2016.day_10;

public class UnknownInstructionException extends RuntimeException {
    public UnknownInstructionException(String instruction) {
        super("An instruction was received but could not be processed: " + instruction);
    }
}
