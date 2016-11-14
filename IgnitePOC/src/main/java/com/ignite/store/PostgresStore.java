package com.ignite.store;

import com.ignite.poc.Record;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.lang.IgniteBiInClosure;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.sql.*;

/**
 * Created by dreddy on 11/11/2016.
 */
public class PostgresStore extends CacheStoreAdapter<Integer, Record> {

    @Override
    public Record load(Integer key) {
        try (Connection conn = connection()) {
            try (PreparedStatement st = conn.prepareStatement("select * from pulic.\"Record\" where id=?")) {
                st.setLong(1, key);

                ResultSet rs = st.executeQuery();

                return rs.next() ? new Record(rs.getInt(1), rs.getInt(2), rs.getInt(3)) : null;
            }
        } catch (SQLException e) {
            throw new CacheLoaderException("Failed to load: " + key, e);
        }
    }

    @Override
    public void write(Cache.Entry<? extends Integer, ? extends Record> entry) throws CacheWriterException {
        try (Connection conn = connection()) {
            try (PreparedStatement st = conn.prepareStatement(
                    "INSERT INTO public.\"Record\"(id, \"batchId\", \"subId\") VALUES (?, ?, ?)")) {
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

    @Override
    public void loadCache(IgniteBiInClosure<Integer, Record> clo, Object... args) {
        System.out.println("***********************8 ENtered");
        if (args == null || args.length == 0 || args[0] == null)
            throw new CacheLoaderException("Expected entry count parameter is not provided.");

        final int entryCnt = (Integer) args[0];

        try (Connection conn = connection()) {
            try (PreparedStatement st = conn.prepareStatement("select * from public.\"Record\"")) {
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

    private Connection connection() throws SQLException {
        String POSTGRES_DRIVER = "jdbc:postgresql://localhost:5432/SparkTest";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(POSTGRES_DRIVER, "postgres", "jaipal");
        conn.setAutoCommit(true);
        return conn;
    }

    public void write(Record val) throws CacheWriterException {
        try (Connection conn = connection()) {
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
            throw new CacheWriterException("Failed to write [key=" + ']', e);
        }
    }
}
