package com.ignite.basic;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import java.util.Arrays;

/**
 * Created by Jai on 11/11/2016.
 */
public class HelloIgnite {

    public static void main(String[] args) throws InterruptedException {
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
        CacheConfiguration<Integer, String> cacheConfig = new CacheConfiguration<>();
        cacheConfig.setStatisticsEnabled(true);
        cacheConfig.setName("myCacheName");
        //cacheConfig.setMemoryMode(CacheMemoryMode.OFFHEAP_TIERED);
       // cacheConfig.setOffHeapMaxMemory(1 * 1024L * 1024L * 1024L);
        cacheConfig.setCacheMode(CacheMode.PARTITIONED);
        // set the discovery spi to ignite configuration
        cfg.setDiscoverySpi(spi);
        cfg.setCacheConfiguration(cacheConfig);
        // Start ignite
        Ignite ignite = Ignition.start(cfg);

        // get or create cache
        IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCacheName");
        // put some cache elements
        for (int i = 1; i <= 100000; i++) {
            cache.put(i, Integer.toString(i));
        }
        // get them from the cache and write to the console
        for (int i = 1; i <= 100; i++) {
            System.out.println("Cache get:" + cache.get(i));
        }
        Thread.sleep(10000);
        System.out.println("Off Heap count:" + cache.metrics(ignite.cluster()).getOffHeapEntriesCount());
        System.out.println("Cache Hits:" + cache.metrics(ignite.cluster()).getCacheHitPercentage());
        Thread.sleep(10000);

        // close ignite instance
        ignite.close();
    }
}
