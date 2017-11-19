package xdh.hongrenzhuang.web.mongodb.userMongoService;

import io.vertx.core.Vertx;
import io.vertx.ext.mongo.MongoClient;

/**
 * Created by ironresolve on 2017/11/19.
 */
public class UserMongoServiceImpl implements UserMongoService {
    private Vertx vertx;
    private MongoClient mongoClient;
    public UserMongoServiceImpl(Vertx vertx, MongoClient mongoClient) {
        this.vertx = vertx;
        this.mongoClient = mongoClient;
    }
}
