package advent_2016.day_10;

import java.util.ArrayList;
import java.util.List;

public class Bin implements Receiver {
    private final List<Integer> values;
    private final String number;

    public Bin(String number) {
        this.number = number;
        this.values = new ArrayList<>();
    }

    @Override
    public void receive(int value) {
        values.add(value);
    }

    @Override
    public String toString() {
        return "Bin{" +
            "number='" + number + "'" +
            ", values=" + values +
            '}';
    }
}
