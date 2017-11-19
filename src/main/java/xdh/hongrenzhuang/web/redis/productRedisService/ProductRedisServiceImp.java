package xdh.hongrenzhuang.web.redis.productRedisService;

import io.vertx.core.Vertx;
import io.vertx.redis.RedisClient;

/**
 * Created by ironresolve on 2017/11/19.
 */
public class ProductRedisServiceImp implements ProductRedisService {

    private RedisClient redisClient;
    private Vertx vertx;

    public ProductRedisServiceImp(Vertx vertx, RedisClient redis) {
        this.vertx = vertx;
        this.redisClient = redisClient;
    }
}
