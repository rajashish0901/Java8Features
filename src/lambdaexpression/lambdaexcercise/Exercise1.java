package lambdaexpression.lambdaexcercise;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import lambdaexpression.common.Person;

interface Condition {
	boolean test(Person p);
}


public class Exercise1 {

	public static void main(String[] args) {
		List<Person> people = Arrays.asList(
				new Person("Charles", "Dickens", 60),
				new Person("Lewis", "Carroll", 42),
				new Person("Thomas", "Carlyle", 51),
				new Person("Charlotte", "Bronte", 45),
				new Person("Matthew", "Arnold", 39)
				);
		
		// Step 1: Sort list by last name
		Collections.sort(people,
				new Comparator<Person>() {
			@Override
			public int compare(Person o1, Person o2) {
				return o1.getLastName().compareTo(o2.getLastName());
			}
		});

		Collections.sort(people,
				(p1, p2) -> p1.getLastName().compareTo(p2.getLastName()));


		// Step 2: Create a method that prints all elements in the list
		System.out.println("Printing all persons");
/*		printAll(people, new Condition() {
			@Override
			public boolean test(Person p) {
				return true;
			}
		});
		printAll(people, new Predicate<Person>() {
			@Override
			public boolean test(Person p) {
				return true;
			}
		});*/

		printAll(people,
				(Person p) -> true,
				(Person p) -> System.out.println(p)
		);

		// Step 3: Create a method that prints all people that have last name beginning with C
		System.out.println("\n\nPrinting all people with LN 'C' ");
		/*printAll(people, new Condition() {
			@Override
			public boolean test(Person p) {
				return (p.getLastName().startsWith("C"));
			}
		});

		printAll(people, new Predicate<Person>() {
			@Override
			public boolean test(Person p) {
				return (p.getLastName().startsWith("C"));
			}
		});

		//lambda version
		printAll(people,
				(Person p) -> p.getLastName().startsWith("C"));
*/

		printAll(people,
				(Person p) -> p.getLastName().startsWith("C"),
				(Person p) -> System.out.println(p)
		);
	}

//	private static void printAll(List <Person> people, Condition c){
 	private static void printAll(List <Person> people, Predicate <Person> condition){

		for (Person p : people) {
			if(condition.test(p))
			System.out.println(p);
		}
	}

	private static void printAll(List <Person> people, Predicate <Person> condition, Consumer<Person> printperson){

		for (Person p : people) {
			if(condition.test(p))
				printperson.accept(p);
		}
	}

}
