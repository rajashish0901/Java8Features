package stream.parallelprocessing;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class MaximumFinder_ForkJoinTask extends RecursiveTask<Integer> {

        private static final int SEQUENTIAL_THRESHOLD = 10;

        private final int[] data;
        private final int start;
        private final int end;

        //Private ticket to constructor to create the sub-task recursively from the main task.
        private MaximumFinder_ForkJoinTask(int[] data, int start, int end) {
            this.data = data;
            this.start = start;
            this.end = end;
        }

        //Public constructor to create the main task
        public MaximumFinder_ForkJoinTask(int[] data) {
            this(data, 0, data.length);
        }


        @Override
        /*This method defines both the logic of splitting the task at hand into subtasks and the algorithm
        to produce the result of a single subtask when it’s no longer possible or convenient to further
        divide it.*/
        protected Integer compute() {

            final int length = end - start;

            //Once you reach the threshold point fork no further and compute the result
            if (length < SEQUENTIAL_THRESHOLD) {
                return computeDirectly();
            }

            final int split = length / 2;
            final MaximumFinder_ForkJoinTask left = new MaximumFinder_ForkJoinTask(data, start, start + split);


            //A task only splits itself up into subtasks if the work the task was
            // given is large enough for this to make sense. There is an overhead to splitting up a task into subtasks,
            // so for small amounts of work this overhead may be greater than the speedup achieved by executing subtasks
            // concurrently.The limit for when it makes sense to fork a task into subtasks is also called a threshold.
            // It is up to each task to decide on a sensible threshold. It depends very much on the kind of work being
            // done.
            //A task that uses the fork and join principle can fork (split) itself into smaller subtasks
            //which can be executed concurrently/asynchronously using ForkJoinPool
            left.fork();

            //Create the sub-task to compute the right side of the array.
            final MaximumFinder_ForkJoinTask right = new MaximumFinder_ForkJoinTask(data, start + split, end);

            //Allow further recursive splits calling the compute method synchronously.
            Integer rightresult = right.compute();

            //Read the result previous sub-task or wait till its ready
            Integer leftresult = left.join();

            return Math.max(leftresult,rightresult);

            //return Math.max(right.compute(), left.join());
        }

        private Integer computeDirectly() {

            System.out.println(Thread.currentThread() + " computing: " + start
                    + " to " + end);

            int max = Integer.MIN_VALUE;
            for (int i = start; i < end; i++) {
                if (data[i] > max) {
                    max = data[i];
                }
            }

            return max;
        }

        public static void main(String[] args) {

            // create a random data set
            final int[] data = new int[100];
            final Random random = new Random();

            for (int index = 0; index < data.length; index++) {
                data[index] = random.nextInt(100);
            }

            /*The ForkJoinPool is a special thread pool which is designed to work well with fork-and-join task splitting*/
            // submit the task to the pool
            final ForkJoinPool pool = new ForkJoinPool(4);

            /*A RecursiveTask is a task that returns a result. It may split its work up into smaller tasks, and merge
            the result of these smaller tasks into a collective result. The splitting and merging may take place on
            several levels.*/
            final MaximumFinder_ForkJoinTask finder = new MaximumFinder_ForkJoinTask(data);

            //schedule a RecursiveTask
            System.out.println(pool.invoke(finder));
        }
}