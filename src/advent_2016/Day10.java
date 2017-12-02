package advent_2016;

import advent_2016.day_10.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day10 {
    private static Map<String, Bot> bots = new HashMap<>();
    private static Map<String, Bin> bins = new HashMap<>();

    private static void step1() {
        bots.values().stream()
            .filter(bot -> bot.getComparisons().stream()
                .anyMatch(comparison -> comparison.getLow() == 17 && comparison.getHigh() == 61))
            .map(Bot::toString)
            .forEach(System.out::println);
    }

    private static void step2() {
        System.out.println(bins.get("0").getValues().get(0) *
            bins.get("1").getValues().get(0) *
            bins.get("2").getValues().get(0));
    }

    public static void main(String[] args) {
        Utils.readFile("day10.txt")
            .sorted()
            .forEach(Day10::matchInstruction);
        step1();
        step2();
    }

    private static void matchInstruction(String instruction) {

        Matcher split = Pattern.compile("bot (\\d+) gives low to ([a-z]+) (\\d+) and high to ([a-z]+) (\\d+)", Pattern.CASE_INSENSITIVE)
            .matcher(instruction);

        if (split.matches()) {
            String number = split.group(1);
            Bot bot = getOrPut(bots, number, new Bot(number));
            bot.instruct(getReceiver(split.group(2), split.group(3)), getReceiver(split.group(4), split.group(5)));

            return;
        }

        Matcher value = Pattern.compile("value (\\d+) goes to ([a-z]+) (\\d+)", Pattern.CASE_INSENSITIVE)
            .matcher(instruction);

        if (value.matches()) {
            Receiver receiver = getReceiver(value.group(2), value.group(3));
            receiver.receive(Integer.parseInt(value.group(1)));

            return;
        }

        throw new UnknownInstructionException(instruction);
    }

    private static Receiver getReceiver(String type, String number) {
        switch (type) {
            case "bot":
                return getOrPut(bots, number, new Bot(number));
            case "output":
                return getOrPut(bins, number, new Bin(number));
            default:
                return null;
        }
    }

    private static <T> T getOrPut(Map<String, T> map, String key, T value) {
        T existing = map.get(key);
        if (existing == null) {
            map.put(key, value);
            return value;
        }
        return existing;
    }
}
