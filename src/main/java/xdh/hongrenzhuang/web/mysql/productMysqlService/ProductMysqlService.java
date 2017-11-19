package xdh.hongrenzhuang.web.mysql.productMysqlService;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.Vertx;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import xdh.hongrenzhuang.web.mysql.SqlQuery;
import xdh.hongrenzhuang.web.mysql.UserMysqlService.UserMysqlService;
import xdh.hongrenzhuang.web.mysql.UserMysqlService.UserMysqlServiceImp;
import xdh.hongrenzhuang.web.mysql.UserMysqlService.UserMysqlServiceVertxEBProxy;

import java.util.HashMap;

/**
 * Created by ironresolve on 2017/11/19.
 */
@ProxyGen
public interface ProductMysqlService {

    static ProductMysqlService create(Vertx vertx , AsyncSQLClient client, HashMap<SqlQuery, String> sqlQueries) {
        return  new ProductMysqlServiceImp(vertx ,client,sqlQueries);
    }
    static ProductMysqlService createProxy(Vertx vertx, String address) {
        return  new ProductMysqlServiceVertxEBProxy(vertx, address);
    }
}
