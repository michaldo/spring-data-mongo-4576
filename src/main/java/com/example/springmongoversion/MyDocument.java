package com.example.springmongoversion;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MyDocument {

    @Id
    private String id;


    @Version
    private int version;

    public int getVersion() {
        return version;
    }



}
