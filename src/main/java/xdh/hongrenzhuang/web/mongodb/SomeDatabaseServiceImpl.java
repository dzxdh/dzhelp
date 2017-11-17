package xdh.hongrenzhuang.web.mongodb;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;

/**
 * Created by ironresolve on 2017/11/17.
 */
public class SomeDatabaseServiceImpl implements SomeDatabaseService{

    private Vertx vertx;
    private MongoClient mongoClient;
    public SomeDatabaseServiceImpl(Vertx vertx ,MongoClient mongoClient) {
        this.vertx = vertx;
        this.mongoClient = mongoClient;
    }

    @Override
    public SomeDatabaseService saveBooks(Handler<AsyncResult<JsonObject>> resultHandler) {
        JsonObject document = new JsonObject().put("title", "The Hobbit");
        JsonObject response = new JsonObject();
        mongoClient.save("books", document, res -> {
            if (res.succeeded()) {
                String result = res.result();
                System.out.println("Saved book with id " + result);
                response.put("result",result);
            } else {
                res.cause().printStackTrace();
            }
            resultHandler.handle(Future.succeededFuture(response));
        });
        return this;
    }

    @Override
    public SomeDatabaseService findBooks( Handler<AsyncResult<JsonObject>> resultHandler) {
        JsonObject query = new JsonObject();
        JsonObject response = new JsonObject();
        mongoClient.find("books", query, res -> {
            if (res.succeeded()) {
                for (JsonObject json : res.result()) {
                    System.out.println(json.encodePrettily());
                }
                List<JsonObject> result = res.result();
                response.put("result",result);
            } else {
                res.cause().printStackTrace();
            }
            resultHandler.handle(Future.succeededFuture(response));
        });
        return this;
    }

}
