package collector.reducingandsummarizing;

import stream.Dish;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class ReduceAndSummarize {

    public enum CaloricLevel { DIET, NORMAL, FAT }

    public static void main(String ... args){

        //Reduce and summarizer
        reduce(Dish.menu);

        summarization(Dish.menu);

        stringJoining(Dish.menu);
     }


    static void reduce(List<Dish> dishes){

        /*collectors (the parameters to the Stream method collect) are typically used
        in cases where it’s necessary to reorganize the stream’s items into a collection. But more
        generally, they can be used every time you want to combine all the items in the stream into a
        single result.*/
        //how to count the items in the list

        //Approach#1:
        System.out.println(dishes.stream().map(d -> 1)
                .reduce(0, (a, b) -> (a + b)).toString());

        //Approach#2:
        System.out.println(dishes.stream().count());

        //Approach#3:
        System.out.println(dishes.stream().collect(Collectors.counting()));

        //Finding maximum and minimum in a stream of values
        System.out.println(dishes.stream().max(Comparator.comparingInt(Dish::getCalories)));
    }

    static void summarization(List<Dish> dishes){

        /*Collectors class provides a specific factory method for summing: Collectors .summingInt. It
        accepts a function that maps an object into the int that has to be summed and returns a collector
        that, when passed to the usual collect method, performs the requested summarization*/

        //Summing the element in a list
        /*While traversing the stream
        each dish is mapped into its number of calories, and this number is added to an accumulator
        starting from an initial value (in this case the value is 0).*/
        System.out.println(dishes.stream().collect(Collectors.summingInt(Dish::getCalories)).toString());
        System.out.println(dishes.stream().collect(Collectors.averagingInt(Dish::getCalories)).toString());

        /*you may want to retrieve two or more of these results, and possibly you’d like to do it in a single operation.
        IntSummaryStatistics{count=9, sum=4300, min=120, average=477.777778, max=800}.
        These are used when the property to be collected is a primitive-type long or a double.*/
        System.out.println(dishes.stream().collect(Collectors.summarizingInt(Dish::getCalories)));

    }

    static void stringJoining(List<Dish> dishes){
        System.out.println(dishes.stream().map(Dish::getName).collect(joining(",")));

        System.out.println(dishes.stream().map(d -> d.getName()+"-" + d.getCountry()).collect(joining(",")));
    }
}