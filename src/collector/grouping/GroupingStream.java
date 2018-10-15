package collector.grouping;

import stream.Dish ;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class GroupingStream {

    public enum CaloricLevel { DIET, NORMAL, FAT }

    public static void main(String ... args){

        grouping(Dish.menu);
    }

    static void grouping(List<Dish> dishes){
        Map <Dish.Type, List<Dish>> dishByType =
                dishes.stream().collect(Collectors.groupingBy(Dish::getType));
        System.out.println(dishByType);

        dishByType = dishes.stream().collect(Collectors
                .groupingBy( dish -> {
                            return dish.getType();
                            }));
        System.out.println(dishByType);

        Map <CaloricLevel, List<Dish>> dishByCalorie =
                dishes.stream().collect(Collectors
                        .groupingBy(dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.NORMAL;
                            else if(dish.getCalories() <= 700) return CaloricLevel.DIET;
                            else return  CaloricLevel.FAT;
                }));
        System.out.println(dishByCalorie);

        /**/
        Map <Dish.Type, Map <CaloricLevel, List<Dish>>> dishByTypeCaloryLevel =
                dishes.stream().collect(Collectors.groupingBy(Dish::getType,
                        Collectors.groupingBy(dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.NORMAL;
                            else if(dish.getCalories() <= 700) return CaloricLevel.DIET;
                            else return  CaloricLevel.FAT;
                        })));
        System.out.println(dishByTypeCaloryLevel);

        Map <Dish.Type, Long> typeCount =
                dishes.stream().collect(Collectors.groupingBy(Dish::getType,Collectors.counting()));
        System.out.println(typeCount);

        Map <Dish.Type, Optional<Dish>> mostCaloricByType =
                dishes.stream()
                        .collect(Collectors
                                .groupingBy(Dish::getType,Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println(mostCaloricByType);

        //Adapts a Collector to perform an additional finishing transformation. For example, one could adapt the
        // toList() collector to always produce an immutable list with:
        Map <Dish.Type, Dish> mostCaloricByType1 =
                dishes.stream()
                        .collect(Collectors.groupingBy(Dish::getType,
                                        Collectors.collectingAndThen(
                                                Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)),
                                                Optional::get)));
        System.out.println(mostCaloricByType1);

    }
}