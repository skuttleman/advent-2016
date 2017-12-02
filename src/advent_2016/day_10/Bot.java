package advent_2016.day_10;

import java.util.ArrayList;
import java.util.List;

public class Bot implements Receiver {
    private final List<Comparison> comparisons;
    private final String number;
    private Integer storedValue;
    private Receiver low;
    private Receiver high;

    public Bot(String number) {
        this.number = number;
        this.comparisons = new ArrayList<>();
    }

    @Override
    public void receive(int value) {
        if (storedValue != null && low != null && high != null) {
            int lowValue = Math.min(value, storedValue);
            int highValue = Math.max(value, storedValue);
            low.receive(lowValue);
            high.receive(highValue);
            this.comparisons.add(new Comparison(lowValue, highValue));
            storedValue = null;
            return;
        } else if (storedValue == null) {
            storedValue = value;
            return;
        }
        throw new BotConfusedException();
    }

    public Bot instruct(Receiver low, Receiver high) {
        this.low = low;
        this.high = high;
        return this;
    }

    public List<Comparison> getComparisons() {
        return comparisons;
    }

    @Override
    public String toString() {
        return "Bot{" +
            "number='" + number + '\'' +
            ", comparisons=" + comparisons +
            '}';
    }

    public static class Comparison {
        int low;
        int high;

        private Comparison(int low, int high) {
            this.low = low;
            this.high = high;
        }

        public int getLow() {
            return low;
        }

        public int getHigh() {
            return high;
        }

        @Override
        public String toString() {
            return "Comparison{" +
                "low=" + low +
                ", high=" + high +
                '}';
        }
    }
}
