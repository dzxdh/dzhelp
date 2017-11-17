package xdh.hongrenzhuang.web.mongodb;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

/**
 * Created by ironresolve on 2017/11/17.
 */
@ProxyGen
public interface SomeDatabaseService {

    static SomeDatabaseServiceImpl create(Vertx vertx, MongoClient client) {
        return new SomeDatabaseServiceImpl(vertx,client);
    }

    static SomeDatabaseService createProxy(Vertx vertx, String address) {
        return new SomeDatabaseServiceVertxEBProxy(vertx, address);
    }

    @Fluent
    SomeDatabaseService saveBooks(Handler<AsyncResult<JsonObject>> resultHandler);
    @Fluent
    SomeDatabaseService findBooks(Handler<AsyncResult<JsonObject>> resultHandler);


}
