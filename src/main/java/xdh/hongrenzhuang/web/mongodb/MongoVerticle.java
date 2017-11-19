package xdh.hongrenzhuang.web.mongodb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.serviceproxy.ServiceBinder;
import xdh.hongrenzhuang.web.mongodb.userMongoService.UserMongoService;
import xdh.hongrenzhuang.web.mysql.UserMysqlService.UserMysqlService;

/**
 * Created by ironresolve on 2017/11/17.
 */
public class MongoVerticle extends AbstractVerticle{

    public static final String ADDRESS_MONGO_USER = "user.mongo";
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

        //mongo的服务
        UserMongoService userMongoService = UserMongoService.create(vertx,client );
        //给eventbus上注册服务
        ServiceBinder binder = new ServiceBinder(vertx);
        binder
                .setAddress("database-service-address")
                .register(UserMongoService.class, userMongoService);

        super.start(startFuture);
    }
}
