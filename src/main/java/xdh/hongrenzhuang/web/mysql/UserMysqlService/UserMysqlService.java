package xdh.hongrenzhuang.web.mysql.UserMysqlService;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import xdh.hongrenzhuang.web.mysql.SqlQuery;

import java.util.HashMap;

/**
 * Created by ironresolve on 2017/11/19.
 */
@ProxyGen
public interface UserMysqlService {

    static UserMysqlService create(Vertx vertx ,AsyncSQLClient client, HashMap<SqlQuery, String> sqlQueries) {
        return  new UserMysqlServiceImp(vertx ,client,sqlQueries);
    }
    static UserMysqlService createProxy(Vertx vertx, String address) {
        return new UserMysqlServiceVertxEBProxy(vertx, address);
    }

    @Fluent
    UserMysqlService create_user(Handler<AsyncResult<JsonObject>> resultHandler);


}
