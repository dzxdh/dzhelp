package xdh.hongrenzhuang.web.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.serviceproxy.ServiceProxyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xdh.hongrenzhuang.web.mongodb.userMongoService.UserMongoService;
import xdh.hongrenzhuang.web.service.ProductService;
import xdh.hongrenzhuang.web.service.UserService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ironresolve on 2017/11/17.
 */
public class HttpServerVerticle extends AbstractVerticle {
    //redis的address
    public static final String ADDRESS_REDIS_USER = "user.redis";
    public static final String ADDRESS_REDIS_PRODUCT = "product.redis";
    //webclient 的 address
    public static final String ADDRESS_WEBCLIENT_WX = "wx.webclient";
    //mysql 的address
    public static final String ADDRESS_MYSQL_USER = "user.mysql";
    public static final String ADDRESS_MYSQL_PRODUCT = "product.mysql";
    //mongo 的address
    public static final String ADDRESS_MONGO_USER = "user.mongo";



    public static final String CONFIG_HTTP_SERVER_PORT = "http.server.port";
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServerVerticle.class);
    private HttpServer server;
    private Router router;
    @Override
    public void start(Future<Void> startFuture) throws Exception {

        //service 的类
        UserService userService = new UserService(vertx);
        ProductService productService = new ProductService(vertx);

        //httpServer
        HttpServer server = vertx.createHttpServer();

        router = Router.router(vertx);
        Set<String> allowHeaders = new HashSet<>();
        allowHeaders.add("x-requested-with");
        allowHeaders.add("Access-Control-Allow-Origin");
        allowHeaders.add("origin");
        allowHeaders.add("Content-Type");
        allowHeaders.add("accept");
        Set<HttpMethod> allowMethods = new HashSet<>();
        allowMethods.add(HttpMethod.GET);
        allowMethods.add(HttpMethod.POST);
        allowMethods.add(HttpMethod.DELETE);
        allowMethods.add(HttpMethod.PATCH);
        router.route().handler(CorsHandler.create("*").allowedHeaders(allowHeaders).allowedMethods(allowMethods));
        router.route().handler(BodyHandler.create());
        //静态资源
        router.route("/static/*").handler(StaticHandler.create());
        //路由
        router.route("/").handler(this::indexHandler);
        router.route("/save").handler(userService::saveHandler);
        router.route("/create").handler(userService::createHandler);



        int portNumber = config().getInteger(CONFIG_HTTP_SERVER_PORT, 8080);
        server
                .requestHandler(router::accept)
                .listen(portNumber, ar -> {
                    if (ar.succeeded()) {
                        System.out.println("success");
                        LOGGER.info("HTTP server running on port " + portNumber);
                        startFuture.complete();
                    } else {
                        LOGGER.error("Could not start a HTTP server", ar.cause());
                        startFuture.fail(ar.cause());
                    }
                });
    }
    private void indexHandler(RoutingContext routingContext) {
        routingContext.response().end("hello");
    }
}
