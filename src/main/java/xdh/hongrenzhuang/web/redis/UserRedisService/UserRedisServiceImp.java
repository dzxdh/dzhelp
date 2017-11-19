package xdh.hongrenzhuang.web.redis.UserRedisService;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.redis.RedisClient;

import java.util.UUID;

/**
 * Created by ironresolve on 2017/10/30.
 */
public class UserRedisServiceImp implements UserRedisService {

    private RedisClient redisClient;
    private Vertx vertx;

    public UserRedisServiceImp(Vertx vertx, RedisClient redisClient) {
        this.vertx = vertx;
        this.redisClient = redisClient;
    }

    @Override
    public UserRedisService Add(String id, Handler<AsyncResult<String>> resultHandler) {
        String guid = UUID.randomUUID().toString().replace("-", "");
        redisClient.setex(guid,7200,id,res->{
            if (res.succeeded()){
                System.out.println(res.result());
                resultHandler.handle(Future.succeededFuture(guid));
            }else {
                System.out.println("fail"+res.result());
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
        return this;
    }
}
