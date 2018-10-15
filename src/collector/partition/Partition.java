package collector.partition;

import collector.grouping.GroupingStream;
import stream.Dish;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Partition {

    public enum CaloricLevel { DIET, NORMAL, FAT }

    public static void main(String ... args){

        partition(Dish.menu);
    }

    /*Partitioning is a special case of grouping: having a predicate (a function returning a boolean),
    called a partitioning function, as a classification function. The fact that the partitioning function
    returns a boolean means the resulting grouping Map will have a Boolean as a key type and
    therefore there can be at most two different groups—one for true and one for false*/

    static void partition(List<Dish> dishes){

        Map <Boolean, List<Dish>> partitionedMenu =
                dishes.stream().collect(Collectors
                        .partitioningBy(d -> d.isVegetarian()));
        System.out.println(partitionedMenu);

        System.out.println(partitionedMenu.get(true));


        Map <Boolean, Dish> partitionedMenuByType =
                dishes.stream().collect(
                        Collectors.partitioningBy(
                                d -> d.isVegetarian(),Collectors.collectingAndThen(
                                        Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)),
                                        Optional::get)));

        System.out.println(partitionedMenuByType);
        //System.out.println(partitionedMenuByType.get(false));

        System.out.println("--" + dishes.stream().collect(
                Collectors.partitioningBy(Dish::isVegetarian,Collectors.partitioningBy(
                        d -> d.getCalories() > 500))) + "--");

        System.out.println("--" + dishes.stream().collect(
                Collectors.partitioningBy(Dish::isVegetarian,Collectors.counting())) +
                "--");
    }
}