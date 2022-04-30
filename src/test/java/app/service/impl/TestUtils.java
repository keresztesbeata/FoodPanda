package app.service.impl;

import app.dto.UserDto;
import app.mapper.UserMapper;
import app.model.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestUtils {

    public static String generateRandomName(int length) {
        return (new Random())
                .ints(length, 26,51 )
                .asLongStream()
                .mapToObj(value -> String.valueOf(Character.toChars((int) (value % 255))))
                .collect(Collectors.joining());
    }

    public static String generateRandomNumericalCode() {
        return (new Random(0))
                .ints(6,1,9)
                .asLongStream()
                .mapToObj(value -> String.valueOf((char) (value + (int)'0')))
                .collect(Collectors.joining());
    }

    public static int getRandomInt() {
        return (new Random()).nextInt(Integer.MAX_VALUE);
    }

    public static int getRandomInt(int range) {
        return (new Random()).nextInt(range);
    }

    public static double getRandomDouble() {
        return (new Random()).nextDouble();
    }

    public static double getRandomDouble(int range) {
        return (new Random()).nextDouble(range);
    }
}
