interface Process{
  void doProcess(int i);
};

public class Thisreference {

    void foo(int i ,Process p){
        System.out.println("foo::value of this::" + this);
        System.out.println("\nfoo::Inside Foo()");
        p.doProcess(i);
    }

    public String toString(){
        return "This is main class";
    }

    void execute(int a){
        foo(a, (i)-> {
            System.out.println("execute::value of this::" + this);
            System.out.println("execute::Print value of i:: " + i);
        });

    }

    public static void main(String[] args){

        Thisreference thisreference = new Thisreference();

        //Java 7 way
        thisreference.foo(10, new Process() {
            @Override
            public void doProcess(int i) {
                System.out.println("doProcess::value of this::" + this);
                System.out.println("doProcess::Print value of i:: " + i);
            }

            public String toString(){
                return "This is anonymous class";
            }
        });

        //Java 8 way-Lambda way
        thisreference.foo(12, (i)-> {

            //System.out.println("value of this::" + this);
            //Error:'io.javabrains.This reference.this' cannot be referenced from a static context
            //Unlike Java 7, Lambda doesn't allow u to use "this" reference from an inner/ anonymous class from a static.
            //method.
            System.out.println("lamda::Print value of i:: " + i);
        });

        //So what's the solution? Move the lambda expression in a non static method.
        thisreference.execute(13);

    }
}
