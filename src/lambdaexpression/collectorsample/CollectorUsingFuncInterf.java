package lambdaexpression.collectorsample;

import lambdaexpression.common.Person;

import java.util.*;
import java.util.stream.Collectors;

public class CollectorUsingFuncInterf {

    static void collectorSamples(List<Person> people){

        /*ToList collector can be used for collecting all Stream elements into a List instance. The important thing to
        * remember is the fact that we can’t assume any particular List implementation with this method.
        * */
        List<Person> p1 = people.stream()
                .collect(Collectors.toList());

        System.out.println("People#1 list" + p1);

        /*when using toSet and toList collectors, you can’t make any assumptions of their implementations.
        * If you want to use a custom implementation, you will need to use the toCollection collector with a provided
        * collection of your choice.
        * */
        List<Person> p2 = people.stream()
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println("People#2 list" + p2);

        /*ToMap collector can be used to collect Stream elements into a Map instance. To do this, you need to provide
        two functions:
            keyMapper
            valueMapper
        keyMapper will be used for extracting a Map key from a Stream element, and valueMapper will be used for
        extracting a value associated with a given key.
        * */
       /* Map<Integer, String> p3 = people.stream()
                .collect(Collectors.toMap(Person::getAge, Person::getName));
        System.out.println("People#3 list" + p3);
        Throws exception for duplicate keys
        */
        Map<Integer, List<Person>> p4 = people.stream()
                .collect(
                        Collectors.groupingBy(Person::getAge));
        System.out.println("People#4 list" + p4);

        Map<Integer, List<String>> p5 = people.stream()
                .collect(
                        Collectors.groupingBy(Person::getAge,
                                Collectors.mapping(Person::getName,Collectors.toList())));
        System.out.println("People#5 list" + p5);
    }

    public static void main(String[] args){

        //Comparator with lambda
        final List<Person> people = Arrays.asList(
                new Person("j","k", 21),
                new Person("j","l", 20),
                //new Person("Jane", 21),
                new Person("a","b", 37),
                new Person("e","b", 37),
                new Person("g","n", 35));

        System.out.println("Original People list" + people);

        collectorSamples(people);

/*        Predicate <Person> p = new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return false;
            }
        };

        final List<Person> peopleAbove20 = people.stream()
                .filter(person -> (person.getAge()>20))
                .collect(Collectors.toList());
                //.forEach(person -> peopleAbove20.add(person));

        System.out.println("\n\nPeople above 20" + peopleAbove20);*/
    }
}
