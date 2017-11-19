package xdh.hongrenzhuang.web.mysql;

import  io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.serviceproxy.ServiceBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xdh.hongrenzhuang.web.mysql.UserMysqlService.UserMysqlService;
import xdh.hongrenzhuang.web.mysql.productMysqlService.ProductMysqlService;
import xdh.hongrenzhuang.web.redis.productRedisService.ProductRedisService;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class MysqlVerticle extends AbstractVerticle {

    private static final Logger LOGGER =  LoggerFactory.getLogger(MysqlVerticle.class);

    private AsyncSQLClient client;
    public static final String ADDRESS_MYSQL_USER = "user.mysql";
    public static final String ADDRESS_MYSQL_PRODUCT = "product.mysql";
    @Override
    public void start(Future<Void> startFuture) throws Exception {

        HashMap<SqlQuery, String> sqlQueries = loadSqlQueries();
        //执行数据库
        JsonObject mySQLClientConfig = new JsonObject()
                .put("host", "localhost")
                .put("port", 3306)
                .put("maxPoolSize", 20)
                .put("username", "root")
                .put("password", "ironfei")
                .put("database", "taobao");
        client = MySQLClient.createShared(vertx, mySQLClientConfig);

        //mysql的服务
        UserMysqlService userMysqlService = UserMysqlService.create(vertx,client ,sqlQueries);
        ProductMysqlService productMysqlService = ProductMysqlService.create(vertx,client,sqlQueries);

        //给eventbus上注册服务
        ServiceBinder binder = new ServiceBinder(vertx);

        binder
                .setAddress(ADDRESS_MYSQL_USER)
                .register(UserMysqlService.class,userMysqlService);
        binder
                .setAddress(ADDRESS_MYSQL_PRODUCT)
                .register(ProductMysqlService.class,productMysqlService);

        super.start(startFuture);
    }


    private HashMap<SqlQuery, String> loadSqlQueries() throws IOException {

        //获取数据库文件信息
        InputStream queryInputStream = getClass().getResourceAsStream("/SQLProperties.properties");
        Properties properties = new Properties();
        try {
            //把获取的文件信息放入新建的文件对象
            properties.load(queryInputStream);
            queryInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<SqlQuery, String> sqlQueries = new HashMap<>();
        sqlQueries.put(SqlQuery.CREATE_USER,properties.getProperty("CREATE_USER"));

        return sqlQueries;
    }
}
