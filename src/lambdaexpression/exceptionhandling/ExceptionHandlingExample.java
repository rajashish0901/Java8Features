package lambdaexpression.exceptionhandling;

import java.util.function.BiConsumer;

public class ExceptionHandlingExample {

	public static void main(String[] args) {
		int [] someNumbers = { 1, 2, 3, 4 };
		int key = 2;

		//Approach#1: Cons: Operation is hardcoded implementation.
		//process(someNumbers,key);

		//Approach#2: Pros: Operation can be modified dynamically by the caller
		//process(someNumbers, key, ((v, k) -> System.out.println("\n" + (v + k))));
		//process(someNumbers, key, ((v, k) -> System.out.println("\n"+ (v * k))));

		//Lets create an exception
		//key=0;
		//process(someNumbers, key, ((v, k) -> System.out.println("\n"+ (v / k))));

		//solution is to do try catch? Certainly not an elegant way!
		process(someNumbers, key, ((v, k) -> {
			try {
				System.out.println("\n" + (v / k));
			}catch (Exception e){
				System.out.println("Exception happened!!");
			}
		}));

		//Approach#3: Pros: Wrapper Lambda for rescue
		process(someNumbers, key, wrapperLambda((v, k) -> System.out.println("\n"+ v / k)));
		//process(someNumbers, key, wrapperLambda((v, k) -> System.out.println("\n"+ v + k)));
	}

	private static void process(int[] someNumbers, int key) {
		for (int i : someNumbers) {
			System.out.println(i / key);
		}
	}

	private static void process(int[] someNumbers, int key, BiConsumer<Integer, Integer> consumer) {
		for (int i : someNumbers) {
			consumer.accept(i, key);
		}
	}

	private static BiConsumer<Integer, Integer> wrapperLambda(BiConsumer<Integer, Integer> consumer) {
		//throw way the received lambda and return the new lambda
		//return (v, k) -> System.out.println("\n"+ v + k);
		//else warap the lambda in a try catch and return that wrapper.
		return (v, k) ->  {
			try {
				consumer.accept(v, k);
			}
			catch (ArithmeticException e) {
				System.out.println("Exception caught in wrapper lambda");
			}
		};
	}

}
