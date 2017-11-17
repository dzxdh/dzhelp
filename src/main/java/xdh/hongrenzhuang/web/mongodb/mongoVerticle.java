package xdh.hongrenzhuang.web.mongodb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.serviceproxy.ServiceBinder;

/**
 * Created by ironresolve on 2017/11/17.
 */
public class mongoVerticle extends AbstractVerticle{

    private MongoClient client;
    @Override
    public void start(Future<Void> startFuture) throws Exception {

        JsonObject mongoClientConfig = new JsonObject()
                .put("host", "127.0.0.1")
                .put("port", 27017);
//                .put("replicaSet","foo")
//                .put("username","username")
//                .put("password","password");
        client = MongoClient.createShared(vertx, mongoClientConfig, "MyPoolName");

        SomeDatabaseServiceImpl service = SomeDatabaseService.create(vertx, client);
        ServiceBinder binder = new ServiceBinder(vertx);
        binder
                .setAddress("database-service-address")
                .register(SomeDatabaseService.class, service);
        super.start(startFuture);
    }
}
