package com.ignite.store;

import com.ignite.poc.Record;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.lang.IgniteBiInClosure;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.io.Serializable;
import java.sql.*;

/**
 * Created by dreddy on 11/10/2016.
 */
public class OracleStore extends CacheStoreAdapter<Integer, Record> implements Serializable {

    static{
        System.out.print("*********************************************************************************************** loaded");
    }
    // This mehtod is called whenever "get(...)" methods are called on IgniteCache.
    @Override
    public Record load(Integer key) {
        try (Connection conn = connection()) {
            try (PreparedStatement st = conn.prepareStatement("select * from Record where id=?")) {
                st.setLong(1, key);

                ResultSet rs = st.executeQuery();

                return rs.next() ? new Record(rs.getInt(1), rs.getInt(2), rs.getInt(3)) : null;
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load: " + key, e);
        }
    }

    // This mehtod is called whenever "put(...)" methods are called on IgniteCache.
    @Override
    public void write(Cache.Entry<? extends Integer, ? extends Record> entry) throws CacheWriterException {
        try (Connection conn = connection()) {
            // Syntax of MERGE statement is database specific and should be adopted for your database.
            // If your database does not support MERGE statement then use sequentially update, insert statements.
            try (PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO record(id, batchId, subId) VALUES (?, ?, ?)")) {
                // for (Cache.Entry<Integer, Record> entry : entries) {
                Record val = entry.getValue();
                st.setInt(1, val.getId());
                st.setInt(2, val.getId());
                st.setInt(3, val.getSubId());
                st.executeUpdate();
                // }
            }
        } catch (SQLException e) {
            throw new CacheWriterException("Failed to write [key=" + entry.getKey() + ", val=" + entry.getValue() + ']', e);
        }
    }


    // This mehtod is called whenever "remove(...)" methods are called on IgniteCache.
    @Override
    public void delete(Object key) {
        try (Connection conn = connection()) {
            try (PreparedStatement st = conn.prepareStatement("delete from Record where id=?")) {
                st.setLong(1, (Long) key);
                st.executeUpdate();
            }
        } catch (SQLException e) {
            throw new CacheWriterException("Failed to delete: " + key, e);
        }
    }

    // This mehtod is called whenever "loadCache()" and "localLoadCache()"
    // methods are called on IgniteCache. It is used for bulk-loading the cache.
    // If you don't need to bulk-load the cache, skip this method.
    @Override
    public void loadCache(IgniteBiInClosure<Integer, Record> clo, Object... args) {
        System.out.println("***********************8 ENtered");
        if (args == null || args.length == 0 || args[0] == null)
            throw new CacheLoaderException("Expected entry count parameter is not provided.");

        final int entryCnt = (Integer) args[0];

        try (Connection conn = connection()) {
            try (PreparedStatement st = conn.prepareStatement("select * from record")) {
                try (ResultSet rs = st.executeQuery()) {
                    int cnt = 0;

                    while (cnt < entryCnt && rs.next()) {
                        Record person = new Record(rs.getInt(1), rs.getInt(2), rs.getInt(3));
                        clo.apply(person.getId(), person);
                        cnt++;
                    }
                }
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load values from cache store.", e);
        }
    }

    // Open JDBC connection.
    private Connection connection() throws SQLException {
        // Open connection to your RDBMS systems (Oracle, MySQL, Postgres, DB2, Microsoft SQL, etc.)
        // In this example we use H2 Database for simplification.
        String ORACLE_DRIVER="jdbc:oracle:thin:@//fn10vir2221.ivycomptech.co.in:1521/real";
        String POSTGRES_DRIVER="jdbc:postgresql://localhost:5432/SparkTest";
        try {
           // Class.forName("org.postgresql.Driver");
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       // System.out.println("Connection");
        //
        Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/real", "mca_user", "mca123");
        conn.setAutoCommit(true);
        return conn;
    }

    // This mehtod is called whenever "put(...)" methods are called on IgniteCache.

    public void write(Record val) throws CacheWriterException {
        try (Connection conn = connection()) {
            // Syntax of MERGE statement is database specific and should be adopted for your database.
            // If your database does not support MERGE statement then use sequentially update, insert statements.
            try (PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO public.\"Record\"(id, \"batchId\", \"subId\") VALUES (?, ?, ?)")) {
                // for (Cache.Entry<Integer, Record> entry : entries) {
                st.setInt(1, val.getId());
                st.setInt(2, val.getId());
                st.setInt(3, val.getSubId());
                st.executeUpdate();
                // }
            }
        } catch (SQLException e) {
            throw new CacheWriterException("Failed to write [key=" +  ']', e);
        }
    }

    public static void main(String[] args) {

        new OracleStore().write(new Record(-1,-1,-1));
    }
}
