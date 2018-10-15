package stream.parallelprocessing;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/*A parallel stream is a stream that
splits its elements into multiple chunks, processing each chunk with a different thread. Thus,
you can automatically partition the workload of a given operation on all the cores of your
multi-core processor and keep all of them equally busy. Let’s experiment with this idea by using a
simple example.*/
public class ParallelExec {

    public static void main(String... args) {

        System.out.println("Sequential sum done in: " +
                measureSumPerf(ParallelExec::sequentialSum, 1_000_000) + " msecs");

        System.out.println("Parallel sum done in: " +
                measureSumPerf(ParallelExec::parallelSum, 1_000_000) + " msecs");

        System.out.println("Optimized Parallel sum done in: " +
                measureSumPerf(ParallelExec::optimizedParallelSum, 1_000_000) + " msecs");
    }

    static Long sequentialSum(Long limit){
        System.out.println("sequentialSum: " + limit);

           return Stream.iterate(1L, (a) -> a+1L)
                .limit(limit)
                .reduce(0L, Long::sum);
    }

    static Long parallelSum(Long limit){

        System.out.println("parallelSum: " + limit);

        /*in reality, calling the method parallel on a sequential stream doesn’t imply any
        concrete transformation on the stream itself. Internally, a boolean flag is set to signal that you
        want to run in parallel all the operations that follow the invocation to parallel.

        Similarly, you can turn a parallel stream into a sequential one by just invoking the method sequential on it.

        the last call to parallel or sequential wins and affects the pipeline globally.
        */
        return Stream.iterate(1L, (a) -> a+1L)
                .limit(limit)
                .parallel()
                .reduce(0L, Long::sum);

       /* you can change the size of this pool using the system property
        java.util.concurrent.ForkJoinPool.common.parallelism, as in the following example:
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "12");
        This is a global setting, so it will affect all the parallel streams in your code. Conversely, it
        currently isn’t possible to specify this value for a single parallel stream. In general, having the
        size of the ForkJoinPool equal to the number of processors on your machine is a meaningful
        default, and we strongly suggest that you not modify it unless you have a very good reason for
        doing so.*/

       /*There are actually two issues mixed together:
         iterate generates boxed objects, which have to be unboxed to numbers before they can be added.
         iterate is difficult to divide into independent chunks to execute in parallel.

        the whole list of numbers isn’t available at the beginning of the reduction process, making it
        impossible to efficiently partition the stream in chunks to be processed in parallel. By flagging
        the stream as parallel, you’re just adding to the sequential processing the overhead of allocating
        each sum operation on a different thread.*/
    }

    static Long optimizedParallelSum(Long limit) {
    /*  The numeric stream is much faster than the earlier sequential version, generated with the iterate
    factory method, because the numeric stream avoids all the overhead caused by all the
    unnecessary autoboxing and unboxing operations performed by the nonspecialized stream. This
    is evidence that choosing the right data structures is often more important than parallelizing the
    algorithm that uses them.*/

        Long longstream = LongStream.rangeClosed(1,limit)
                .parallel()
                .reduce(0L, Long::sum);

        return longstream;
    }

        public static long measureSumPerf(Function<Long, Long> adder, long n) {

        long fastest = Long.MAX_VALUE;

        for (int i = 0; i < 10; i++) {

            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest) fastest = duration;

        }
        return fastest;
    }
}