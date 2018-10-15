package lambdaexpression.iterator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MapReduce {
    public static void main(String[] args) {

        final List<String> friends =
                Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
        /*
        mapToInt() method, a variation of the map operation (variations
        like mapToInt(), mapToDouble(), and so on create type-specialized streams such
        as IntStream and DoubleStream) and then reduced the resulting length to the sum
        value.
        */

        final List<Integer> intlist =
                Arrays.asList(1,2,3,4);

        //BinaryOperator<Integer> adder = (n1, n2) -> n1 + n2;
        Optional<Integer> adder = intlist.stream()
                .reduce((n1, n2) -> n1 + n2);
        System.out.println(adder);

        int totalSum= friends.stream()
                .mapToInt(name -> name.length())
                .sum();
        System.out.println(totalSum);

        Optional<String> lngstName = friends.stream()
                .reduce((n1,n2)-> (n1.length() >n2.length())?n1:n2);

    }
}
