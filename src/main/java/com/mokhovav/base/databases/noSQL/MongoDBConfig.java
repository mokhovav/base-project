package com.mokhovav.base.databases.noSQL;

import com.mokhovav.base.databases.DatabaseSettings;
import com.mokhovav.base.logging.Logger;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;


@Configuration
@ConditionalOnProperty(
        value="project.config.MongoDBEnable",
        havingValue = "true")
public class MongoDBConfig extends AbstractMongoClientConfiguration {
    @Autowired
    @Qualifier("MongoDB")
    DatabaseSettings databaseSettings;

    @Autowired
    Logger logger;

    @Override
    protected String getDatabaseName() {
        return databaseSettings.getDataBaseName();
    }

    @Override
    public MongoClient mongoClient() {
        logger.debug("MongoDB connection string:" + databaseSettings.getConnectionString());
        return MongoClients.create(databaseSettings.getConnectionString());
    }

    @Override
    public MongoDatabaseFactory mongoDbFactory() {
        return new SimpleMongoClientDatabaseFactory(
                mongoClient(),
                databaseSettings.getDataBaseName()
        );
    }

    @Override
    public MongoTemplate mongoTemplate(MongoDatabaseFactory databaseFactory, MappingMongoConverter converter) {
        return new MongoTemplate(mongoDbFactory());
    }
}