package stream.introduction;

import stream.Dish;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class StreamBasic {

    public static void main(String... args) {

        /*
        // Java 7
        getLowCaloricDishesNamesInJava7(Dish.menu).forEach(System.out::println);
        System.out.println("---");
        getLowCaloricDishesNamesInJava8(Dish.menu).forEach(System.out::println);
        streamOperation(Dish.menu);
        */
        //reduceStream(Dish.menu);

        //numericStream(Dish.menu);

        //streamFromValue();

        functionStream();
    }

    public static List<String> getLowCaloricDishesNamesInJava7(List<Dish> dishes) {

        List<Dish> lowCaloricDishes = new ArrayList<>();

        for (Dish d : dishes) {
            if (d.getCalories() < 400) {
                lowCaloricDishes.add(d);
            }
        }

        List<String> lowCaloricDishesName = new ArrayList<>();
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            public int compare(Dish d1, Dish d2) {
                return Integer.compare(d1.getCalories(), d2.getCalories());
            }
        });

        for (Dish d : lowCaloricDishes) {
            lowCaloricDishesName.add(d.getName());
        }

        return lowCaloricDishesName;
    }

    /*Java 8- Stream vs Collection::
       the difference between collections and streams has to do with when things are
       computed. A collection is an in-memory data structure that holds all the values the data
       structure currently has—every element in the collection has to be computed before it can be
       added to the collection*/
    public static List<String> getLowCaloricDishesNamesInJava8(List<Dish> dishes) {
        /*   return dishes.stream()
                .filter(d -> d.getCalories() < 400)
                .sorted(comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());*/

        return dishes.stream()
                .filter((d) -> {
                    System.out.println("In filter");
                    return (d.getCalories() < 400);
                })
                .sorted(Comparator.comparing((d -> {
                    System.out.println("In the sorted");
                    return d.getCalories();
                })))
                .map(d -> {
                 /*function is applied to each element, mapping it into a new element (the word mapping is used because
                 it has a meaning similar to transforming but with the nuance of “creating a new version of” rather than
                 “modifying”). */
                    System.out.println("In the map");
                    return d.getName();
                }) // Returns another copy of streams
                .collect(Collectors.toList());//Collect that stream into a list
    }

    /*Stream Vs iterator::
      a stream can be traversed only once. After that a stream is said
      to be consumed. You can get a new stream from the initial data source to traverse it again just
      like for an iterator (assuming it’s a repeatable source like a collection; if it’s an I/O channel,
      you’re out of luck).

      Collection interface requires iteration to be done by the user (for example, using
      for-each); this is called external iteration. The Streams library by contrast uses internal
      iteration—it does the iteration for you and takes care of storing the resulting stream value
      somewhere; you merely provide a function saying what’s to be done
      */
    static void streamVsIterator() {

        Stream s1 = Dish.menu.stream();
        s1.forEach(System.out::println);
        //Exception in thread "main" java.lang.IllegalStateException: stream has already been operated upon or closed
        //s1.forEach(System.out::println);
    }

    /*You can see two groups of operations::
          intermediate operations -> filter, map, and limit can be connected together to form a pipeline.
          terminal operations -> collect causes the pipeline to be executed and closes it.

          intermediate operations don’t perform any processing until a terminal operation is invoked on the stream
          pipeline—they’re lazy. This is because intermediate operations can usually be merged and
          processed into a single pass by the terminal operation.*/
    static void streamOperation(List<Dish> dishes) {
        //map();
        //flatmap();
        findElement(dishes);
    }

    static void map() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squares = numbers.stream()
                .map(n -> n * n)
                .collect(toList());
        squares.stream().forEach(System.out::println);
    }

    /*How flatMap() works :
    { {1,2}, {3,4}, {5,6} } -> flatMap -> {1,2,3,4,5,6}
    { {'a','b'}, {'c','d'}, {'e','f'} } -> flatMap -> {'a','b','c','d','e','f'}
    */
    static void flatmap() {

        System.out.println("---- :flatmap:Example#1 -----");

        String[][] data = new String[][]{
                {"a", "b"},
                {"c", "d"},
                {"e", "f"}
        };

        //Stream<String[]>
        Stream<String[]> temp = Arrays.stream(data);

        //Stream<String>, GOOD!
        Stream<String> stringStream = temp.flatMap(x -> {
            System.out.println("String[][] Stream:: " + Arrays.deepToString(x));
            return Arrays.stream(x);
        });
        stringStream.forEach(s -> System.out.println("flatMapStream:: " + s));

        Arrays.stream(data)
                .flatMap(x -> Arrays.stream(x))
                .filter(x -> "a".equals(x.toString()))
                .forEach(System.out::println);

        System.out.println("---- :flatmap:Example#2 -----");

        //Example#2:
        /*Given two lists of numbers, how would you return all pairs of numbers? For example, given a
        list [1, 2, 3] and a list [3, 4] you should return [(1, 3), (1, 4), (2, 3), (2, 4), (3, 3), (3, 4)].*/
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);

        //List <int[]> l =
        numbers1.stream()
                .flatMap(
                        i -> {
                            return numbers2.stream()
                                    .filter(j -> (i + j) % 3 == 0)
                                    .map(k -> new int[]{i, k});
                        }
                )
                .collect(toList())
                .forEach(s -> System.out.println(Arrays.toString(s)));
    }

    /*In relation to streams, certain operations such as allMatch, noneMatch, findFirst, and findAny
    don’t need to process the whole stream to produce a result. As soon as an element is found, a
    result can be produced. Similarly, limit is also a short-circuiting operation: the operation only
    needs to create a stream of a given size without processing all the elements in the stream. Such
    operations are useful, for example, when you need to deal with streams of infinite size*/

    static void findElement(List<Dish> dishes) {

        //question “Is there an element in the stream
        //matching the given predicate?”
        if (dishes.stream().anyMatch(d -> d.getName().equals("DesiMeal"))) {

        } else {
            System.out.println("Dish not found!!");
        }

        //In the previous code, it’s possible that findAny doesn’t find any element.
        //Instead of returning null, which is well known for being error prone, the Java 8 library designers
        //introduced Optional<T>.Optional<T> class (java.util.Optional) is a container class to represent
        // the existence or absence of a value. An optional comes with ifPresent()
        dishes.stream().filter(d -> d.getName().equals("DesiMeal")).
                findAny().ifPresent(d -> System.out.println("Dish found" + d.getName()));
    }

    /*Such queries combine all the elements in the stream repeatedly to produce a single value such as an Integer.
    These queries can be classified as reduction operations (a stream is reduced to a value). In functional
    programming-language jargon, this is referred to as a fold because you can view this operation
    as repeatedly folding a long piece of paper (your stream) until it forms a small square, which is
    the result of the fold operation.*/
    static void reduceStream(List<Dish> dishes) {

        String mealName = "pork";
        Integer totalCalories = dishes.stream().map(d -> d.getCalories()).reduce(0, (a, b) -> (a + b));
        System.out.println(totalCalories.toString());

        /*There’s also an overloaded variant of reduce that doesn’t take an initial value, but it returns an
        Optional object:*/
        Optional<Integer> sum = dishes.stream()
                .filter(d -> d.getName().equals(mealName))
                .map(Dish::getCalories)
                .reduce((a, b) -> (a + b));
        sum.ifPresent(d -> System.out.println("Total calories for dish " + mealName + "is= " + sum));

        /*How would you count the number of dishes in a stream using the map and reduce methods?
        The problem with this code is that there’s an insidious boxing cost. Behind the scenes each
        Integer needs to be unboxed to a primitive before performing the summation.
        Look for NumericStream to reduce the stream efficiently. */

        Integer dishesCount = dishes.stream().map(d -> 1).reduce(0, (a, b) -> (a + b));
        System.out.println("Total dishes are " + dishesCount.toString());
    }

     /*
        There is no Sum method in stream.Why?
        Say you had only a Stream<Dish> like the menu; it wouldn’t make any
        sense to be able to sum dishes. But don’t worry; the Streams API also supplies primitive stream
        specializations that support specialized methods to work with streams of numbers

        Solution::
        three primitive specialized stream interfaces to tackle this issue, IntStream,
        DoubleStream, and LongStream, that respectively specialize the elements of a stream to be int,
        long, and double—and thereby avoid hidden boxing costs. Each of these interfaces brings new
        methods to perform common numeric reductions such as sum to calculate the sum of a numeric
        stream and max to find the maximum element.*/

    static void numericStream(List<Dish> dishes) {

        System.out.println("Total calories for dish " + dishes.stream().map(Dish::getCalories).mapToInt(s -> s).sum());

        System.out.println("Total calories for dish " + dishes.stream().mapToInt(s -> s.getCalories()).sum());

        /*stream were empty, sum would return 0 by default. IntStream also supports other convenience
        methods such as max, min, and average.*/
        System.out.println("Total calories for dish " + dishes.stream()
                .filter(s -> s.getCalories() > 800)
                .mapToInt(s -> s.getCalories()).sum());


        System.out.println("Total calories for dish " + dishes.stream()
                .filter(s -> s.getCalories() > 800)
                .mapToInt(s -> s.getCalories()).max().orElse(-1));

        //For purely numerical operations, you can use the Int/LongStream.
        IntStream.rangeClosed(1, 20).filter(n -> n % 2 == 0).forEach(System.out::println);
    }

    static void streamFromValue() {
        Stream<String> stream = Stream.of("Java 8 ", "Lambdas ", "In ", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        int i[] = {1,2,3,4,5,6};
        Arrays.stream(i).forEach(System.out::println);
    }

    /*The Streams API provides two static methods to generate a stream from a function:
    Stream.iterate and Stream.generate. These two operations let you create what we call an infinite
    stream: a stream that doesn’t have a fixed size like when you create a stream from a fixed
    collection.*/
    static void functionStream(){

        /*The iterate() method takes two arguments: a seed and a function.
        A seed is the first element of the stream. The second element is generated by applying the function to the
        first element. The third element is generated by applying the function on the second element.*/
        Stream.iterate(0, new UnaryOperator<Integer>() {
            @Override
            //n -> n + 2
            public Integer apply(Integer integer) {
                return (integer.intValue()+1);
            }
        }).limit(10).filter(n -> n%2 != 0).forEach(System.out::println);

        Stream.iterate(new int[]{0,1}, t -> new int[]{t[1],t[0]+t[1]})
                .limit(10)
                .forEach( t-> System.out.println(Arrays.toString(t)));


        Stream <int[]> fibonaciStream = Stream.iterate(new int[]{0, 1},
                new UnaryOperator<int[]>() {
            @Override
            public int[] apply(int[] ints) {
                return new int[]{ints[1],ints[0]+ints[1]};
            }
        });

        fibonaciStream.limit(10)
                //.forEach( t-> System.out.println(Arrays.toString(t)));//prints the array stream
                .mapToInt(arr -> arr[0])
                .forEach( t-> System.out.println(t));//prints the int stream
    }
}