package lambdaexpression.lambdarunnable;

public class lambdarunnable {

    public static void main(String[] args){
    Thread myThread = new Thread(new Runnable() {

        @Override
        public void run() {
            System.out.println("Printed inside Runnable");

        }

    });
    myThread.start();

    Thread myLambdaThread = new Thread(
            () -> System.out.println("Printed inside Lambda Runnable")
    );
    myLambdaThread.start();
}
}


