package stream;
import java.util.*;

public class Dish {

    private final String name;
    private final String country;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish(String name, String country,boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.country = country;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    public enum Type { MEAT, FISH, OTHER }

    @Override
    public String toString() {
        return name;
    }

    public static final List<Dish> menu =
            Arrays.asList( new Dish("pork","USA", false, 800, Type.MEAT),
                           new Dish("beef","USA" ,false, 700, Type.MEAT),
                           new Dish("chicken", "India",false, 400, Type.MEAT),
                           new Dish("french fries","India", true, 530, Type.OTHER),
                           new Dish("rice", "India",true, 350, Type.OTHER),
                           new Dish("season fruit", "India",true, 120, Type.OTHER),
                           new Dish("pizza", "Italy",true, 550, Type.OTHER),
                           new Dish("prawns", "Columbia",false, 400, Type.FISH),
                           new Dish("salmon", "USA",false, 450, Type.FISH));
}