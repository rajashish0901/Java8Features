package lambdaexpression.lambdafunction;
import lambdaexpression.functionalinterfaces.*;

//@FunctionalInterface
interface Addition{

    //int getStringLength(String s);//Multiple non-overriding abstract methods found in interface
    // io.javabrains.lambda function-Addition.
    int getSum(int a, int b);

    //int findInt();
    /*String getString(){
        return "";
    }*/
}

interface StrLength{
    int getStringLength(String s);
}

public class Greeter {

    void greet(Greeting g){
        g.perform();
    }

    public static void main(String[] args) {
        Greeter greeter = new Greeter();

        //Without lambda
        Greeting concreteImpl = new HelloWorldGreeting();
        concreteImpl.perform();//this call implemented interface class

        //In lambda way
        Greeting lambdaGreeting = () -> System.out.print("\nLambda greet- Hello world!");
        lambdaGreeting.perform();//this call functional implementation.No class req.

        //Anonymous Inner class
        Greeting innerClassGreeting = new Greeting() {
            public void perform() {
                System.out.print("\nAInnerClass Hello world!");
            }
        };

        greeter.greet(concreteImpl);
        greeter.greet(lambdaGreeting);
        greeter.greet(innerClassGreeting);
        greeter.greet(() -> System.out.print("\nLambda greet- Hello world!"));


        Addition add = (int x,int y) -> {
            return x+y;
        };
        System.out.print("\n Sum : "+add.getSum(2,3));


        StrLength length = (String s) -> s.length(); // return is optional
        System.out.print("\n StrLength : "+length.getStringLength("Ashish"));

    }
}
