package lambdaexpression.iterator;

import java.util.*;

import java.util.List;
import java.util.Arrays;

public class HandlingNull {

    final static List<String> friends =
            Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

/*    public static void pickName(final List<String> names, final String startingLetter) {

        String foundName = null;
        for(String name : names) {
            if(name.startsWith(startingLetter)) {
                foundName = name;
                break;
            }
        }

        System.out.print(String.format("A name starting with %s: ", startingLetter));

        if(foundName != null) {
            System.out.println(foundName);
        } else {
            System.out.println("No name found");
        }
    }*/

    public static void pickName(final List<String> names, final String startingLetter) {
        //Optional object, which is the state-appointed null deodorizer in Java.
        //It protects us NullPointerException from getting a by accident and makes it quite explicit to the
        //reader that “no result found” is a possible outcome. We can inquire if an
        //isPresent() object is present by using the method, and we can obtain the current
        //get() value using its method.
        final Optional<String> foundName = names.stream()
                .filter(name -> name.startsWith(startingLetter))
                .findFirst();
        //System.out.println(foundName.get());
        //System.out.println(foundName.isPresent());
        System.out.println(foundName.orElse(String.format("String starting with %s is not present",startingLetter)));
        foundName.ifPresent(name -> System.out.println(name));
    }

    public static void main(final String[] args) {

        pickName(friends, "N");
        pickName(friends, "Z");

    }
}