package lambdaexpression.typeChecking;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Supplier;

public class TypeChecking {


    private Integer ii =0;
    private String ss="default";

    TypeChecking(){
    }

    TypeChecking(Integer a , String s){
        ii=a;
        ss=s;
    }

    TypeChecking(Integer a ){
        ii=a;
    }

    String getValue(){
        return ii.toString() + " " + ss;
    }

    static void print(String s){
        System.out.println(s);
    }

    static void lambdamethodReference(){
         /*1. A method reference to a static method (for example, the method parseInt of Integer, written
            Integer::parseInt)
        2. A method reference to an instance method of an ""arbitrary type"" (for example, the method
        length of a String, written String::length)
        3. A method reference to an instance method of an """existing object""" (for example, suppose you
        have a local variable expensiveTransaction that holds an object of type Transaction, which
        supports an instance method getValue; you can write expensiveTransaction::getValue)
        */

        List<String> str = Arrays.asList("a","b","A","B");
        str.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
        str.sort(String::compareToIgnoreCase);

        Function<String, Integer> stringToInteger = (String s) -> Integer.parseInt(s);
        Function<String, Integer> strToInt = Integer::parseInt;

        BiPredicate<List<String>, String> contains = (list, element) -> list.contains(element);
        BiPredicate<List<String>, String> contains1 = List::contains;
    }

    //java.util.functional interface var = ClassName::new;
    //To make this work, you need a functional interface that will match the signature of that constructor
    //reference.
    static void constructorReference(){

        //TypeChecking t = new TypeChecking(2,"apple");
        //TypeChecking t1 = TypeChecking::new; TypeChecking is not s functional interface so this will not work
        //suppose there’s a zero-argument constructor. This fits the signature () -> Apple of Supplier
        Supplier<TypeChecking> t = TypeChecking::new;
        Supplier<TypeChecking> t1 = () -> new TypeChecking();
        Supplier<TypeChecking> t2 = new Supplier<TypeChecking>() {
            @Override
            public TypeChecking get() {
                return new TypeChecking();
            }
        };

        System.out.println(t1.get().getValue());

        //If you have a constructor with signature Apple(Integer weight), it fits the signature of the
        //Function interface
        BiFunction<Integer,String,TypeChecking> f = (i, s) -> {
            return new TypeChecking(i,s);
        };

        BiFunction<Integer,String,TypeChecking> f1 = new BiFunction<Integer, String, TypeChecking>() {
            @Override
            public TypeChecking apply(Integer integer, String s) {
                return new TypeChecking(integer,s);
            }
        };
        System.out.println(f1.apply(10,"Apple").getValue());


        //capability of referring to a constructor without instantiating it enables interesting
        //applications. For example, you can use a Map to associate constructors with a string value. You
        //can then create a method giveMeFruit that, given a String and an Integer, can create different
        //types of fruits with different weights

        Function <Integer,TypeChecking> funtypchk = new Function<Integer, TypeChecking>() {
            @Override
            public TypeChecking apply(Integer integer) {
                return new TypeChecking(integer);
            }
        };
        Function <Integer,TypeChecking> funtypchk1 = TypeChecking::new;
        print(funtypchk1.apply(55).getValue());

        Map<String, Function<Integer,TypeChecking>> map = new HashMap<>();
        map.put("#1", TypeChecking::new);
        map.put("#2", TypeChecking::new);
        map.put("#3", TypeChecking::new);

        print(map.get("#1").apply(10).getValue());
        print(map.get("#2").apply(20).getValue());
        print(map.get("#3").apply(30).getValue());
    }


    static void lambdaVariableScopeAndAccess(){
         /*Lambdas are allowed to capture (that is, to reference in their bodies) instance
          variables and static variables without restrictions. But local variables have to be explicitly declared final
          or are effectively final.

        Instance variables are stored on the heap, whereas local variables live on the stack. If a lambda could
        access the local variable directly and the lambda were used in a thread, then the thread using the
        lambda could try to access the variable after the thread that allocated the variable had
        deallocated it. Hence, Java implements access to a free local variable as access to a copy of it
        rather than access to the original variable. This makes no difference if the local variable is
        assigned to only once—hence the restriction. */
        //TODO: Need more clarity
        final String value = "Lambda accessing variable#1";
        Runnable r2 = ()-> System.out.println("Tricky example" + value);
        r2.run();
    }

    public static void main(String[] args) {

        /*diamond collector (<>) => A given class instance expression can appear in two or more different contexts,
        and the appropriate type argument will be inferred as exemplified here:*/

        List<String> listOfStrings = new ArrayList<>();
        List<Integer> listOfIntegers = new ArrayList<>();

        //a function descriptor () -> void
        //The context of the lambda expression is Object (the target type). But Object isn’t a functional interface.
        //Object o = () -> {System.out.println("Tricky example"); };
        //To fix this you can change the target type to Runnable
        Runnable r = ()-> System.out.println("Tricky example");
        r.run();

        new Runnable() {
            @Override
            public void run() {
                System.out.println("Tricky example");
            }
        }.run();

        lambdaVariableScopeAndAccess();

        lambdamethodReference();

        //Constructor references
        constructorReference();
    }
}