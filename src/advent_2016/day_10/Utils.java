package advent_2016.day_10;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Utils {
    public static Stream<String> readFile(String filename) {
        try {
            return Files.lines(FileSystems.getDefault().getPath("../resources", filename).toAbsolutePath())
                .map(String::trim)
                .filter(complement(String::isEmpty));
        } catch (IOException e) {
            return Stream.empty();
        }
    }

    public static <T> Predicate<T> complement(Predicate<T> pred) {
        return value -> !pred.test(value);
    }
}
