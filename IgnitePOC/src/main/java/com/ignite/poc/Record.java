package com.ignite.poc;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

/**
 * Created by dreddy on 11/10/2016.
 */
public class Record implements Serializable{
    @QuerySqlField(index = true)
    private int id;
    @QuerySqlField(index = true)
    private int batchId;
    @QuerySqlField(index = true)
    private int subId;

    public Record(int id, int batchId, int subId) {
        this.id = id;
        this.batchId = batchId;
        this.subId = subId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }
}
