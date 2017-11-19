package xdh.hongrenzhuang.web.mongodb.userMongoService;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.Vertx;
import io.vertx.ext.mongo.MongoClient;

/**
 * Created by ironresolve on 2017/11/19.
 */
@ProxyGen
public interface UserMongoService {

    static UserMongoService create(Vertx vertx, MongoClient client) {
        return new UserMongoServiceImpl(vertx,client);
    }

    static UserMongoService createProxy(Vertx vertx, String address) {
        return new UserMongoServiceVertxEBProxy(vertx, address);
    }

}
