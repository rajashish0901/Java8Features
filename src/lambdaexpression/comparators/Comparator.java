package lambdaexpression.comparators;

import lambdaexpression.common.Person;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

class Comparaor {

        static void comparatorforDataTypes(){
            //Comparator Basics:
            final List<Character> chrlist = Arrays.asList('c','b','a');

            System.out.println("Comparator Basics#1");
            Comparator<Character> byChr = new Comparator<Character>() {
                @Override
                public int compare(Character o1, Character o2) {
                    System.out.println(o1.charValue() + " " + o2.charValue());
                    return o1.compareTo(o2);
                }
            };

            chrlist.stream()
                    .sorted(byChr)
                    .forEach(p -> System.out.println(p));

            System.out.println("Comparator Basics#2");

            final Function<Character,Character> bychr = ch -> Character.valueOf(ch);
            chrlist.stream()
                    .sorted(Comparator.comparing(bychr))
                    .forEach(p -> System.out.println(p));
        }


        static void sortByName(List<Person> people){

            //people in the list in ascending order by Name.
            System.out.println("Sorted by name list");
            people.stream()
                    //.sorted((p1,p2)-> p1.getName().compareTo(p2.getName()))
                    .sorted(Person::StringComp)
                    .forEach(p -> System.out.println(p));

        }

        static void sortByAge(List<Person> people){
            //people in the list in ascending order by Age.
            System.out.println("Sorted by Age list");
            people.stream()
                    //.sorted((p1,p2)-> (p1.getAge()- p2.getAge()))
                    .sorted(Person::ageDifference)
                    .forEach(p -> System.out.println(p));

        }

        static void sortByNameAndAge(List<Person> people){

            final Function<Person,String> byname = person -> person.getName();
            final Function<Person,Integer> byage = person -> person.getAge();

            /*
            final Function<Person,String> func = new Function<Person, String>() {
                @Override
                public String apply(Person person) {
                    return person.getName();
                }
            };*/

            System.out.println("Sorted by name&age list");
            people.stream()
                    .sorted(Comparator.comparing(byname).thenComparing(byage))
                    .forEach(p -> System.out.println(p));
        }

        public static void main(String[] args){

            comparatorforDataTypes();

            //Comparator with lambda
            //Comparator with lambda
            final List<Person> people = Arrays.asList(
                    new Person("j","k", 21),
                    new Person("j","l", 20),
                    //new Person("Jane", 21),
                    new Person("a","b", 37),
                    new Person("g","n", 35));

            System.out.println("Original List" + people);

            sortByName(people);
            sortByAge(people);
            sortByNameAndAge(people);

        }
}