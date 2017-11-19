package xdh.hongrenzhuang.web.service;

import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import io.vertx.serviceproxy.ServiceProxyBuilder;
import xdh.hongrenzhuang.web.mongodb.userMongoService.UserMongoService;
import xdh.hongrenzhuang.web.mysql.UserMysqlService.UserMysqlService;
import xdh.hongrenzhuang.web.redis.UserRedisService.UserRedisService;
import xdh.hongrenzhuang.web.webClient.WxWebClientService.WxWebClientService;

/**
 * Created by ironresolve on 2017/11/19.
 */
public class UserService {
    public static final String ADDRESS_REDIS_USER = "user.redis";
    public static final String ADDRESS_MYSQL_USER = "user.mysql";
    public static final String ADDRESS_MONGO_USER = "user.mongo";
    public static final String ADDRESS_WEBCLIENT_WX = "wx.webclient";
    private Vertx vertx;
    private UserMysqlService userMysqlService;
    private UserRedisService userRedisService;
    private UserMongoService userMongoService;
    private WxWebClientService wxWebClientService;
    public UserService(Vertx vertx) {
        this.vertx = vertx;
        userRedisService = new ServiceProxyBuilder(vertx).setAddress(ADDRESS_REDIS_USER).build(UserRedisService.class);
        userMongoService = new ServiceProxyBuilder(vertx).setAddress(ADDRESS_MONGO_USER).build(UserMongoService.class);
        userMysqlService = new ServiceProxyBuilder(vertx).setAddress(ADDRESS_MYSQL_USER).build(UserMysqlService.class);
        wxWebClientService = new ServiceProxyBuilder(vertx).setAddress(ADDRESS_WEBCLIENT_WX).build(WxWebClientService.class);
    }

    public void saveHandler(RoutingContext routingContext) {
        @Nullable String id = routingContext.request().getParam("id");
        if (id != null){
            userRedisService.Add(id,res->{
                if (res.succeeded()){
                    routingContext.response().end(res.result());
                }else {
                    routingContext.response().end("插入失败了");
                }
            });
        }else {
            routingContext.response().setStatusCode(400)
                    .putHeader("content-type", "application/json")
                    .end(new JsonObject().put("error", new IllegalStateException("Negative increase")
                            .getMessage()).encodePrettily());
        }
    }

    public void createHandler(RoutingContext routingContext) {
        userMysqlService.create_user(res->{
           if (res.succeeded()){
               JsonObject result = res.result();
               routingContext.response().end(result.encode());
           }else {
               routingContext.response().setStatusCode(400)
                       .putHeader("content-type", "application/json")
                       .end(new JsonObject().put("error", new IllegalStateException("Negative increase")
                               .getMessage()).encodePrettily());
           }
        });
    }
}
