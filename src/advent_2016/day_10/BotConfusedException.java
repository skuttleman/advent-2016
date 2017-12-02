package advent_2016.day_10;

public class BotConfusedException extends RuntimeException {
    public BotConfusedException() {
        super("A bot was given a second value with no knowledge of what to do about it!");
    }
}
