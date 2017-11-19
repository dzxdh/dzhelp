package xdh.hongrenzhuang.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xdh.hongrenzhuang.web.mongodb.MongoVerticle;
import xdh.hongrenzhuang.web.mysql.MysqlVerticle;
import xdh.hongrenzhuang.web.redis.RedisVerticle;
import xdh.hongrenzhuang.web.webClient.WebClientVerticle;

/**
 * 项目主入口
 *
 * Created by ironresolve on 2017/11/17.
 */
public class MainVerticle extends AbstractVerticle {

  private static final Logger LOGGER =  LoggerFactory.getLogger(MainVerticle.class);
  @Override
  public void start(Future<Void> startFuture) throws Exception {
    //mysql部署
    Future<String> mysqlVerticleDeployment = Future.future();
    vertx.deployVerticle(new MysqlVerticle(),
            mysqlVerticleDeployment.completer());
    mysqlVerticleDeployment.compose(res->{
      //redis部署
      Future<String> redisVerticleDeployment = Future.future();
      vertx.deployVerticle(new RedisVerticle(),
              redisVerticleDeployment.completer());
      return redisVerticleDeployment;
    }).compose(res->{
      //mongo部署
      Future<String> mongoVerticleDeployment = Future.future();
      vertx.deployVerticle(new MongoVerticle(),
              mongoVerticleDeployment.completer());
      return mongoVerticleDeployment;
    }).compose(res->{
      //webclient部署
      Future<String> webClientVerticleDeployment = Future.future();
      vertx.deployVerticle(new WebClientVerticle(),
              webClientVerticleDeployment.completer());
      return  webClientVerticleDeployment;
    }).compose(id -> {
      //http部署
      Future<String> httpVerticleDeployment = Future.future();
      vertx.deployVerticle( "xdh.hongrenzhuang.web.http.HttpServerVerticle",
              new DeploymentOptions().setInstances(1),
              httpVerticleDeployment.completer());
      return httpVerticleDeployment;
    }).setHandler(ar -> {
      //全部部署完成
      if (ar.succeeded()) {
        LOGGER.info("全部部署完成");
        startFuture.complete();
      } else {
        LOGGER.error("部署失败",ar.cause());
        startFuture.fail(ar.cause());
      }
    });

  }
}
