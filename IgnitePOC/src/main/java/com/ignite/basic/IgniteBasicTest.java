package com.ignite.basic;

import com.ignite.poc.Record;
import com.ignite.store.MongoStore;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.log4j.Logger;

import javax.cache.configuration.FactoryBuilder;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class IgniteBasicTest implements Serializable {
    private static final String cache = "POCCACHE";
    // private static final Ignite ignite = Ignition.start("example-ignite.xml");
    private static Logger logger = Logger.getLogger(IgniteBasicTest.class);
    private static IgniteBasicTest cacheCreation;

    private IgniteBasicTest() {

    }

    public static IgniteBasicTest getInstance() {
        if (cacheCreation == null)
            cacheCreation = new IgniteBasicTest();
        return cacheCreation;
    }

    public static void main(String[] args) throws ParseException, SQLException {
        try (Ignite ignite = Ignition.start("example-ignite.xml")) {
            Ignition.setClientMode(true);
            CacheConfiguration<Integer, Record> cacheCfg = new CacheConfiguration<>("POCCACHE");
            cacheCfg.setIndexedTypes(Integer.class, Record.class);
            // Set atomicity as transaction, since we are showing transactions in example.
            cacheCfg.setAtomicityMode(CacheAtomicityMode.ATOMIC);
            // Configure JDBC store.
            cacheCfg.setCacheStoreFactory(FactoryBuilder.factoryOf(MongoStore.class));

            // Configure JDBC session listener.
//            cacheCfg.setCacheStoreSessionListenerFactories(new Factory<CacheStoreSessionListener>() {
//                @Override public CacheStoreSessionListener create() {
//
//                    CacheJdbcStoreSessionListener lsnr = new CacheJdbcStoreSessionListener();
//                    lsnr.setDataSource(JdbcConnectionPool.create("jdbc:postgresql://localhost:5432/SparkTest","postgres","jaipal"));
//                    return lsnr;
//                }
//            });

            cacheCfg.setReadThrough(true);
            cacheCfg.setWriteThrough(true);
            cacheCfg.setWriteBehindEnabled(true);
            cacheCfg.setWriteBehindFlushSize(5);
            cacheCfg.setWriteBehindFlushFrequency(100);


            // Auto-close cache at the end of the example.
            try (IgniteCache<Integer, Record> cache = ignite.getOrCreateCache(cacheCfg)) {

//                cache.loadCache(null,100_000);
                //cache.load
                System.out.println("Cache " + cache.getName());
                for (int i = 0; i < 10; i++) cache.put(i, new Record(i, i % 2, i + 1));
                //for (int i = 0; i < 10; i++) cache.put(i + 10, new Record(i, i % 2, i + 10));
                SqlFieldsQuery sql = new SqlFieldsQuery("select * from Record where batchId=0");
                System.out.println(" Size ********************* " + cache.size());
                try (QueryCursor<List<?>> cursor = cache.query(sql)) {
                    for (List row : cursor) {
                        System.out.println(row);
                    }
                }
                while (true)
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            }

        }

    }

    public IgniteCache<Integer, Record> getCache() throws ParseException, SQLException {
        CacheConfiguration<Integer, Record> biDataNormCacheConfig = new CacheConfiguration<Integer, Record>(cache);
        biDataNormCacheConfig.setIndexedTypes(Integer.class, Record.class);
        //IgniteCache<Integer, Record> bidataNormCache = ignite.getOrCreateCache(biDataNormCacheConfig);
        return null;
    }

    public void putCache(Integer id, Record r) {
        try {
            this.getCache().put(id, r);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}