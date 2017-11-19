package xdh.hongrenzhuang.web.redis.productRedisService;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.Vertx;
import io.vertx.redis.RedisClient;
import xdh.hongrenzhuang.web.redis.UserRedisService.UserRedisService;
import xdh.hongrenzhuang.web.redis.UserRedisService.UserRedisServiceImp;
import xdh.hongrenzhuang.web.redis.UserRedisService.UserRedisServiceVertxEBProxy;

/**
 * Created by ironresolve on 2017/11/19.
 */
@ProxyGen
public interface ProductRedisService {


    static ProductRedisService create(Vertx vertx , RedisClient redis) {
        return  new ProductRedisServiceImp(vertx , redis);
    }

    static ProductRedisService createProxy(Vertx vertx, String address) {
        return new ProductRedisServiceVertxEBProxy(vertx,address);
    }
}
