package com.ignite.basic;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jai on 11/11/2016.
 */
public class IgniteServerStart {

    public static void main(String[] args) {

        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(new Runnable() {
            @Override
            public void run() {

                System.out.println("Hello Ignite");
                // create a new instance of TCP Discovery SPI
                TcpDiscoverySpi spi = new TcpDiscoverySpi();
                // create a new instance of tcp discovery multicast ip finder
                TcpDiscoveryMulticastIpFinder tcMp = new TcpDiscoveryMulticastIpFinder();
                tcMp.setAddresses(Arrays.asList("localhost")); // change your IP address here
                // set the multi cast ip finder for spi
                spi.setIpFinder(tcMp);
                // create new ignite configuration
                IgniteConfiguration cfg = new IgniteConfiguration();
                cfg.setClientMode(false);
                Ignite ignite = Ignition.start(cfg);


            }
        });
    }
}
