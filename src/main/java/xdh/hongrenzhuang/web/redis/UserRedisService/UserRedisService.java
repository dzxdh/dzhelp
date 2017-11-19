package xdh.hongrenzhuang.web.redis.UserRedisService;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.redis.RedisClient;

/**
 * Created by ironresolve on 2017/10/30.
 */
@ProxyGen
public interface UserRedisService {

    static UserRedisService create(Vertx vertx , RedisClient redis) {
        return  new UserRedisServiceImp(vertx , redis);
    }

    static UserRedisService createProxy(Vertx vertx, String address) {
        return new UserRedisServiceVertxEBProxy(vertx,address);
    }

    @Fluent
    UserRedisService Add(String id, Handler<AsyncResult<String>> resultHandler);

}
