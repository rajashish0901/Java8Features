package lambdaexpression.lambdafunction;
import lambdaexpression.functionalinterfaces.*;

import java.util.concurrent.Callable;

public class HelloWorldGreeting implements Greeting {

    @Override
    public void perform() {
        System.out.print("\nImplemeted class Hello world!");
    }

    Callable<String> c = () -> "Hello-World";
}
