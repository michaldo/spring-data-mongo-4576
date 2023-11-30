package com.example.springmongoversion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.dao.DataAccessResourceFailureException;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
@Testcontainers
class SpringMongoVersionApplicationTests {

    @Container
    @ServiceConnection
    static MongoDBContainer MONGO =  new MongoDBContainer(DockerImageName.parse("mongo:latest"));


    @Test
    void verifyUpdateAfterReconnection(@Autowired MyRepository myRepository) {
        MyDocument myDocument = myRepository.save(new MyDocument());
        int versionAfterInsert = myDocument.getVersion();
        MONGO.stop();
        try {
            myRepository.save(myDocument);
            fail("Save must fail because database is down");
        } catch (DataAccessResourceFailureException ex) {
            int versionAfterFailedUpdate = myDocument.getVersion();
            assertEquals(versionAfterInsert, versionAfterFailedUpdate,
                    "Version after failure should not change to be able to save again when connection is back");
        }
    }

}
