package xdh.hongrenzhuang.web.mysql.productMysqlService;

import io.vertx.core.Vertx;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xdh.hongrenzhuang.web.mysql.SqlQuery;
import xdh.hongrenzhuang.web.mysql.UserMysqlService.UserMysqlServiceImp;

import java.util.HashMap;

/**
 * Created by ironresolve on 2017/11/19.
 */
public class ProductMysqlServiceImp implements ProductMysqlService {

    private static final Logger LOGGER =  LoggerFactory.getLogger(UserMysqlServiceImp.class);
    private Vertx vertx;
    private final HashMap<SqlQuery, String> sqlQueries;
    private AsyncSQLClient client;

    public ProductMysqlServiceImp(Vertx vertx, AsyncSQLClient client, HashMap<SqlQuery, String> sqlQueries) {
        this.vertx = vertx;
        this.client = client;
        this.sqlQueries = sqlQueries;
    }
}
