package com.ignite.transaction;

import com.ignite.poc.Record;
import com.ignite.store.MongoStore;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteTransactions;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.Transaction;
import org.apache.ignite.transactions.TransactionConcurrency;
import org.apache.ignite.transactions.TransactionIsolation;

import javax.cache.configuration.FactoryBuilder;

/**
 * Created by Jai on 11/14/2016.
 */
public class HelloTransaction {

    public static void main(String[] args) {
        Ignition.setClientMode(true);
        try (Ignite ignite = Ignition.start("example-ignite.xml")) {
            CacheConfiguration<Integer, Record> cacheCfg = new CacheConfiguration<>("TEST_CACHE");
            cacheCfg.setIndexedTypes(Integer.class, Record.class);
            cacheCfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
            cacheCfg.setCacheMode(CacheMode.PARTITIONED);
            cacheCfg.setBackups(2);
            cacheCfg.setCacheStoreFactory(FactoryBuilder.factoryOf(MongoStore.class));
            cacheCfg.setReadThrough(true);
            cacheCfg.setWriteThrough(true);
            cacheCfg.setWriteBehindEnabled(true);
            cacheCfg.setWriteBehindFlushSize(5);
            cacheCfg.setWriteBehindFlushFrequency(100);
            IgniteTransactions transactions = ignite.transactions();

            try (IgniteCache<Integer, Record> cache = ignite.getOrCreateCache(cacheCfg)) {

                try (Transaction tx = transactions.txStart(TransactionConcurrency.PESSIMISTIC, TransactionIsolation.READ_COMMITTED)) {
                    Record r = null;
                    for (int i = 0; i < 10000; i++) {
                        r = new Record(i, i + 1, i + 2);
                        cache.put(i, r);
                    }
                    cache.remove(10);
                    tx.commit();
                }
            } catch (Exception e) {
                System.out.println("********************************" + e);
            }

        }
    }
}
