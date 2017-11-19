package xdh.hongrenzhuang.web.mysql.UserMysqlService;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.sql.SQLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xdh.hongrenzhuang.web.mysql.SqlQuery;

import java.util.HashMap;

/**
 * Created by ironresolve on 2017/11/19.
 */
public class UserMysqlServiceImp implements UserMysqlService {

    private static final Logger LOGGER =  LoggerFactory.getLogger(UserMysqlServiceImp.class);
    private Vertx vertx;
    private final HashMap<SqlQuery, String> sqlQueries;
    private AsyncSQLClient client;

    UserMysqlServiceImp(Vertx vertx , AsyncSQLClient client, HashMap<SqlQuery, String> sqlQueries) {
        this.vertx = vertx;
        this.client = client;
        this.sqlQueries = sqlQueries;
    }


    @Override
    public UserMysqlService create_user(Handler<AsyncResult<JsonObject>> resultHandler) {
        JsonObject response = new JsonObject();
        client.getConnection(res->{
           if (res.succeeded()){
               SQLConnection connection = res.result();
               connection.execute(sqlQueries.get(SqlQuery.CREATE_USER),resu->{
                  if (res.succeeded()){
                      response.put("result","ok");
                  }else {
                      response.put("result",resu.cause());
                  }
                   resultHandler.handle(Future.succeededFuture(response));
               });
           }
        });
        return this;
    }
}
