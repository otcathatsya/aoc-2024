package at.cath.day07;

import java.util.Arrays;
import java.util.stream.LongStream;

import static at.cath.UtilsKt.readInput;


public class Day07 {

    enum Op {
        ADD(Long::sum),
        MUL((x, y) -> x * y),
        CONCAT((x, y) -> Long.parseLong(x + "" + y));

        private final BiLongOperator op;

        Op(BiLongOperator op) {
            this.op = op;
        }

        public long apply(long x, long y) {
            return op.apply(x, y);
        }

        @FunctionalInterface
        private interface BiLongOperator {
            long apply(long x, long y);
        }
    }

    public static String padZerosLeft(String s, long n) {
        return String.format("%" + n + "s", s).replace(' ', '0');
    }

    public static void main(String[] args) {
        long total = 0;
        for (String line : readInput("Day07")) {
            var split = line.split(" ");
            var solution = Long.parseLong(split[0].substring(0, split[0].length() - 1));
            var numbers = Arrays.stream(Arrays.copyOfRange(split, 1, split.length)).map(Long::parseLong).toList();

            var operatorSlotCount = numbers.size() - 1;
            var operatorCount = Op.values().length;

            for (long perm = 0; perm < Math.pow(operatorCount, operatorSlotCount); perm++) {
                // create a base n=len(ops) representation and then map it to ops
                var paddedString = padZerosLeft(Long.toString(perm, operatorCount), operatorSlotCount);
                var operations = paddedString.chars().mapToObj(it -> Op.values()[it - '0']).toList();

                // you could probably do some crazy optimization here to be binary search-ish
                var result = LongStream.range(0, operations.size())
                        .reduce(numbers.getFirst(), (acc, i) -> operations.get((int) i).apply(acc, numbers.get((int) (i + 1))));
                if (result == solution) {
                    total += result;
                    break;
                }
            }
        }

        System.out.println("Result: " + total);
    }
}