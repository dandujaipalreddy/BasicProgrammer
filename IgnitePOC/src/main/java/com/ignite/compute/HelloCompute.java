package com.ignite.compute;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteCallable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dreddy on 11/11/2016.
 */
public class HelloCompute {
    public static void main(String[] args) throws InterruptedException {

//
    }

    public static void igniteComputationExample() throws InterruptedException {
        try (Ignite ignite = Ignition.start("example-ignite.xml")) {
            // Limit broadcast to remote nodes only.
            //  IgniteCompute compute = ignite.compute(ignite.cluster().forRemotes());
            IgniteCompute compute = ignite.compute();
            // Print out hello message on remote nodes in the cluster group.
            compute.broadcast(() -> System.out.println("Hello Node: " + ignite.cluster().localNode().id()));

            Collection<IgniteCallable<Integer>> calls = new ArrayList<>();

// Iterate through all words in the sentence and create callable jobs.
            for (String word : "How many characters".split(" "))
                calls.add(word::length);
           Thread.sleep(10000);
// Execute collection of callables on the cluster.
            Collection<Integer> res = ignite.compute().call(calls);

// Add all the word lengths received from cluster nodes.
            int total = res.stream().mapToInt(Integer::intValue).sum();
            System.out.println(total);
        }
    }
}
