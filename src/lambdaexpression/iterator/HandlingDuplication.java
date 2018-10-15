package lambdaexpression.iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HandlingDuplication {

    //Rather than duplicate the lambda expression several times, we created it once
    //and stored it in a reference named startsWithN of type Predicate.
    public static Predicate <String> findNameStartsWith(String s){
        return name -> name.startsWith(s);
    }

	public static void main(String[] args) {


        final List<String> friends =
                Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

        final List<String> uppercaseNames = new ArrayList<String>();

        System.out.println("\nfriendsList:: " + friends);

        /*The filter() method expects a lambda expression that returns a boolean result.
        If the lambda expression returns a true, the element in context while executing
        that lambda expression is added to a result collection; it’s skipped otherwise.
        Finally the method returns a Stream with only elements for which the lambda
        expression yielded a true.*/
        List filteredlist = friends.stream()
                .filter(findNameStartsWith("N"))
                .collect(Collectors.toList());

        //What is collect?

        System.out.println("\nfilteredlist:: " + filteredlist);

        filteredlist.clear();
        final List<String> editors =
                Arrays.asList("Brian", "Jackie", "John", "Mike");

        filteredlist = friends.stream()
                .filter(findNameStartsWith("B"))
                .collect(Collectors.toList());

        System.out.println("\nfilteredlist:: " + filteredlist);
    }
}
