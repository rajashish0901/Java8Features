package lambdaexpression.iterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransformingList {

	public static void main(String[] args) {


        final List<String> friends =
                Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");

        final List<String> uppercaseNames = new ArrayList<String>();

        System.out.println("\nfriendsList:: " + friends);

        //Non functionl approach
        for(String s : friends){
            uppercaseNames.add(s.toUpperCase());
        }
        //System.out.println("Uppercase:: " + uppercaseNames);

        uppercaseNames.clear();
        friends.forEach((name)-> uppercaseNames.add(name.toUpperCase()));
        System.out.println("\nUppercase:: " + uppercaseNames);

        /*A Stream is much like an iterator on a collection of
        objects and provides some nice fluent functions. Using the methods of this
        interface, we can compose a sequence of calls so that the code reads and
        flows in the same way we’d state problems, making it easier to read

        map() method is quite useful to map or transform an input collection into
        a new output collection.It collects the result of running the lambda expression
        and returns the result collection
        Unlike  forEach() method, which simply runs the block in the context of each element
        in the collection.
        */

        uppercaseNames.clear();

        friends.stream()
                .map(name -> name.toUpperCase())
                .forEach(name-> uppercaseNames.add(name));

        //Note:element types in the input don’t have to match the element types in the output collection
        friends.stream()
                .map(name -> name.length())
                .forEach(i -> System.out.print(i));

        System.out.println("\nUppercase:: " + uppercaseNames);
    }
}
