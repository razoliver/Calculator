import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class CalculatorTest {

    @ParameterizedTest
    @MethodSource("provideValidStringEquations")
    public void testValidEquations(String s, double i) {
        var calculator = new Calculator();
        double res = calculator.calculate(s);
        assertEquals(res, i);
    }

    private static Stream<Arguments> provideValidStringEquations() {
        return Stream.of(
                Arguments.of("3 + 4", 7),
                Arguments.of("5 + 7", 12),
                Arguments.of("(1 + 5)", 6),
                Arguments.of("3 + 4 * (5+6) - 10/5 * (3+4)", 33),
                Arguments.of("((4 - 4/2 + 1) * 2 * ( 3 *  3 + 4 *4) / 2)", 75),
                Arguments.of("3*(0-2)", -6),
                Arguments.of("3/2", 1.5)
        );
    }


    @ParameterizedTest
    @MethodSource("provideInValidStringEquations")
    public void testInValidEquations(String s) {
        var calculator = new Calculator();

        assertThrows(RuntimeException.class, () -> {
            calculator.calculate(s);
        });
    }

    private static Stream<Arguments> provideInValidStringEquations() {
        return Stream.of(
                Arguments.of("3 + 4)"),
                Arguments.of("(3+4))"),
                Arguments.of("3 + 4a"),
                Arguments.of((Object) null),
                Arguments.of(" 5 + 6 + "),
                Arguments.of("5 +- 6"),
                Arguments.of("((((5 + 6 ) ) ) ) )"),
                Arguments.of("5 + 1 + a")
        );
    }
}