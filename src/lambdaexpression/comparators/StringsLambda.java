package lambdaexpression.comparators;

public class StringsLambda {

    private static void printChar(int aChar) {
        System.out.println((char)(aChar));
    }

    public static void main(String[] args){

        final String str = "w00t";
        str.chars()
                .forEach(c -> System.out.println(c));

        str.chars()
                .forEach(System.out::println);

        str.chars()
                .forEach(c -> StringsLambda.printChar(c));

        str.chars()
                .mapToObj(ch -> Character.valueOf((char)ch))
                .forEach(System.out::println);

        str.chars()
                .filter(ch -> Character.isDigit(ch))
                .forEach(c -> StringsLambda.printChar(c));
    }
}