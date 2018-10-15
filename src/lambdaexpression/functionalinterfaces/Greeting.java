package lambdaexpression.functionalinterfaces;
/*
functional interface must contain exactly one abstract method declaration. Each lambda expression of that type
will be matched to this abstract method. Since default methods are not abstract you're free to add default methods
to your functional interface.
We can use arbitrary functionalinterfaces as lambda expressions as long as the interface only contains one abstract method.
*/
@FunctionalInterface
public interface Greeting {
    public void perform();
    //int getSum(int a , int b);
    // Can't define multiple method in interface if its being used for lambda.
    //by adding the FunctionalInterface we are enforcing the rule @interface definition level.

}