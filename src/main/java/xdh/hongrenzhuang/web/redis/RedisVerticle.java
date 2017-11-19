package xdh.hongrenzhuang.web.redis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;
import io.vertx.serviceproxy.ServiceBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xdh.hongrenzhuang.web.redis.UserRedisService.UserRedisService;
import xdh.hongrenzhuang.web.redis.productRedisService.ProductRedisService;

/**
 * Created by ironresolve on 2017/10/30.
 */
public class RedisVerticle extends AbstractVerticle {

    private static final Logger LOGGER =  LoggerFactory.getLogger(RedisVerticle.class);
    public static final String ADDRESS_REDIS_USER = "user.redis";
    public static final String ADDRESS_REDIS_PRODUCT = "product.redis";

    private RedisClient redisClient;
    @Override
    public void start(Future<Void> startFuture) throws Exception {

        RedisOptions config = new RedisOptions()
                .setHost("127.0.0.1");
        redisClient = RedisClient.create(vertx, config);


        UserRedisService userRedisService = UserRedisService.create(vertx,redisClient);
        ProductRedisService productRedisService = ProductRedisService.create(vertx,redisClient);
        //代理注册
        ServiceBinder binder = new ServiceBinder(vertx);

        //注册product redis
        binder
                .setAddress(ADDRESS_REDIS_PRODUCT)
                .register(ProductRedisService.class,productRedisService);
        //注册user redis
        binder
                .setAddress(ADDRESS_REDIS_USER)
                .register(UserRedisService.class,userRedisService);

        super.start(startFuture);
    }
}
