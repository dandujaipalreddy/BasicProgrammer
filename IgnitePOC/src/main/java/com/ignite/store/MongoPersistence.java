package com.ignite.store;

import com.google.gson.Gson;
import com.ignite.poc.Record;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class MongoPersistence<K,V> {
    private static MongoClient client = new MongoClient();
    private static MongoDatabase db = client.getDatabase("ignite");
    private static MongoCollection<Document> coll = db.getCollection("sample");
    private static Logger logger = Logger.getLogger(MongoPersistence.class);


    public void insert(Record r) {
        Document doc = Document.parse(new Gson().toJson(r));
        coll.updateOne(
                new Document("id", r.getId()).append("batchId", r.getBatchId()),
                new Document("$addToSet", new Document("subId", r.getSubId())),
                new UpdateOptions().upsert(true));
    }

    public Record get(int id) {
        Document doc = coll.find(new Document("id", id)).first();
        return new Gson().fromJson(doc.toJson(), Record.class);
    }

    public List<Record> getAll() {
        List<Record> rs = new ArrayList<>();
        List<Document> resultSet = coll.find().into(new ArrayList<Document>());
        resultSet.forEach(document -> {
            List<Integer> list= (List<Integer>) document.get("subId");
            for(Integer i:list)
            rs.add(new Record(document.getInteger("id"),document.getInteger("batchId"),i));
        });
        System.out.println("********************"+rs+"****************"+rs.size());

        return rs;
    }

    public void delete(int id) {
        coll.deleteOne(new Document("id", id));
    }
}
