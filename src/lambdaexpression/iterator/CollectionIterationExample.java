package lambdaexpression.iterator;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import lambdaexpression.common.Person;

public class CollectionIterationExample {

	public static void main(String[] args) {
		List<Person> people = Arrays.asList(
				new Person("Charles", "Dickens", 60),
				new Person("Lewis", "Carroll", 42),
				new Person("Thomas", "Carlyle", 51),
				new Person("Charlotte", "Bronte", 45),
				new Person("Matthew", "Arnold", 39)
				);

		System.out.println("\n++ Using for loop ++ ");
		//external iterator: U tell how the list be traversed.
		for (int i = 0; i < people.size(); i++) {
			System.out.println(people.get(i));
		}
		//external iterator: U tell how the list be traversed.
		System.out.println("\n++ Using for in loop ++");
		
		for (Person p : people) {
			System.out.println(p);
		}
		
		System.out.println("\n++ Using lambda for each loop ++");
		/*'internal' iterations: For each people instance execute the argument lambda.
        the forEach() on the friends collection and passed an anonymous
        instance of Consumer to it. The forEach() method will invoke the accept() method
        of the given Consumer for each element in the collection and let it do whatever
        it wants with it.*/
        people.forEach(new Consumer<Person>() {
            public void accept(final Person p) {
                System.out.println(p);
            }
        });

        //'internal' iterations: For each people instance execute the argument lambda.
        //people.forEach((Person p) -> System.out.println(p));
        //people.forEach((p) -> System.out.println(p));
        //people.forEach(System.out::println);
	}
}
