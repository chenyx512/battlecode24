// https://github.com/awesomelemonade/Battlecode2023/blob/master/src/finalBot/util/LambdaUtil.java
package waterpatch.fast;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

// Workarounds to be not as bytecode excessive
public class LambdaUtil {
    // Replacement for Arrays.stream(array).max(comparator)
    public static <T> Optional<T> arraysStreamMax(T[] array, Comparator<T> comparator) {
        if (array.length == 0) {
            return Optional.empty();
        }
        int len_1 = array.length - 1;
        T best = array[len_1];
        for (int i = len_1; --i >= 0; ) {
            T item = array[i];
            if (comparator.compare(item, best) > 0) {
                best = item;
            }
        }
        return Optional.of(best);
    }

    // Replacement for Arrays.stream(array).min(comparator)
    public static <T> Optional<T> arraysStreamMin(T[] array, Comparator<T> comparator) {
        if (array.length == 0) {
            return Optional.empty();
        }
        int len_1 = array.length - 1;
        T best = array[len_1];
        for (int i = len_1; --i >= 0; ) {
            T item = array[i];
            if (comparator.compare(item, best) < 0) {
                best = item;
            }
        }
        return Optional.of(best);
    }

    // Replacement for Arrays.stream(array).filter(predicate).min(comparator)
    public static <T> Optional<T> arraysStreamMin(T[] array, Predicate<T> predicate, Comparator<T> comparator) {
        T best = null;
        for (int i = array.length; --i >= 0; ) {
            T item = array[i];
            if (predicate.test(item) && (best == null || comparator.compare(item, best) < 0)) {
                best = item;
            }
        }
        return Optional.ofNullable(best);
    }

    // Replacement for Arrays.stream(array).filter(predicate).min(comparator)
    public static <T> Optional<T> arraysStreamMin(T[] array, T[] array2, Predicate<T> predicate, Comparator<T> comparator) {
        T best = null;
        for (int i = array.length; --i >= 0; ) {
            T item = array[i];
            if (predicate.test(item) && (best == null || comparator.compare(item, best) < 0)) {
                best = item;
            }
        }
        for (int i = array2.length; --i >= 0; ) {
            T item = array2[i];
            if (predicate.test(item) && (best == null || comparator.compare(item, best) < 0)) {
                best = item;
            }
        }
        return Optional.ofNullable(best);
    }

    // Replacement for Arrays.stream(array).filter(predicate).min(comparator)
    public static <T> Optional<T> arraysStreamMin(T[] array, T[] array2, Comparator<T> comparator) {
        T best = null;
        for (int i = array.length; --i >= 0; ) {
            T item = array[i];
            if (best == null || comparator.compare(item, best) < 0) {
                best = item;
            }
        }
        for (int i = array2.length; --i >= 0; ) {
            T item = array2[i];
            if (best == null || comparator.compare(item, best) < 0) {
                best = item;
            }
        }
        return Optional.ofNullable(best);
    }

    // Replacement for Arrays.stream(array).filter(predicate).map(mapper).min()
    public static <T> Optional<Integer> arraysStreamMin(T[] array, Predicate<T> predicate, ToIntFunction<T> mapper) {
        int min = Integer.MAX_VALUE;
        for (int i = array.length; --i >= 0; ) {
            T item = array[i];
            if (predicate.test(item)) {
                min = Math.min(min, mapper.applyAsInt(item));
            }
        }
        return min == Integer.MAX_VALUE ? Optional.empty() : Optional.of(min);
    }

    // Replacement for Arrays.stream(array).filter(predicate).map(mapper).min()
    //                      .map(x -> Math.min(x, upperBound)).orElse(upperBound);
    public static <T> int arraysStreamMin(T[] array, Predicate<T> predicate, ToIntFunction<T> mapper, int upperBound) {
        int min = upperBound;
        for (int i = array.length; --i >= 0; ) {
            T item = array[i];
            if (predicate.test(item)) {
                min = Math.min(min, mapper.applyAsInt(item));
            }
        }
        return min;
    }

    // Replacement for Arrays.stream(array).filter(predicate).mapToInt(mapper).sum()
    public static <T> int arraysStreamSum(T[] array, Predicate<T> predicate, ToIntFunction<T> mapper) {
        int sum = 0;
        for (int i = array.length; --i >= 0; ) {
            T item = array[i];
            if (predicate.test(item)) {
                sum += mapper.applyAsInt(item);
            }
        }
        return sum;
    }

    // Replacement for Arrays.stream(array).filter(predicate).count()
    public static <T> int arraysStreamSum(T[] array, Predicate<T> predicate) {
        int sum = 0;
        for (int i = array.length; --i >= 0; ) {
            T item = array[i];
            if (predicate.test(item)) {
                sum++;
            }
        }
        return sum;
    }

    // Replacement for Arrays.stream(array).anyMatch(predicate)
    public static <T> boolean arraysAnyMatch(T[] array, Predicate<T> predicate) {
        for (int i = array.length; --i >= 0; ) {
            if (predicate.test(array[i])) {
                return true;
            }
        }
        return false;
    }

    // Replacement for Arrays.stream(array).allMatch(predicate)
    public static <T> boolean arraysAllMatch(T[] array, Predicate<T> predicate) {
        for (int i = array.length; --i >= 0; ) {
            if (!predicate.test(array[i])) {
                return false;
            }
        }
        return true;
    }

    // Java 8 doesn't have Optional.or()
    @SafeVarargs
    public static <T> Optional<T> or(Supplier<Optional<T>>... suppliers) {
        for (Supplier<Optional<T>> supplier : suppliers) {
            Optional<T> opt = supplier.get();
            if (opt.isPresent()) {
                return opt;
            }
        }
        return Optional.empty();
    }

    public static <T> Optional<T> or(Optional<T> optional, Supplier<Optional<T>> supplier) {
        if (optional.isPresent()) {
            return optional;
        } else {
            return supplier.get();
        }
    }

    public static <T> boolean arraysHasAtLeast(T[] array, Predicate<T> predicate, int count) {
        if (count == 0) {
            return true;
        }
        for (int i = array.length; --i >= 0; ) {
            if (predicate.test(array[i])) {
                if (--count <= 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
