package xdh.hongrenzhuang.web.service;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import xdh.hongrenzhuang.web.mongodb.SomeDatabaseService;

/**
 * Created by ironresolve on 2017/11/17.
 */
public class SomeService {

    private SomeDatabaseService dbService;
    private Vertx vertx;
    public SomeService(Vertx vertx, SomeDatabaseService dbService){
        this.vertx = vertx;
        this.dbService = dbService;
    }

    public void saveHandler(RoutingContext routingContext) {

        dbService.saveBooks(res->{
           if (res.succeeded()){
               JsonObject result = res.result();
               System.out.println(result);
               routingContext.response().end(result.encode());
           }
        });
    }

    public void findHandler(RoutingContext routingContext) {
        dbService.findBooks(res->{
            if (res.succeeded()){
                JsonObject result = res.result();
                System.out.println(result);
                routingContext.response().end(result.encode());
            }
        });
    }
}
