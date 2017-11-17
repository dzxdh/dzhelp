package xdh.hongrenzhuang.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import xdh.hongrenzhuang.web.http.HttpServerVerticle;
import xdh.hongrenzhuang.web.mongodb.mongoVerticle;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Future<Void> startFuture) throws Exception {

    //部署dbVerticle
    Future<String> dbVerticleDeployment = Future.future();
    vertx.deployVerticle(new mongoVerticle(), dbVerticleDeployment.completer());
    dbVerticleDeployment.compose(res->{
      //部署httpVerticle
      Future<String> httpVerticleDeployment = Future.future();
      vertx.deployVerticle(new HttpServerVerticle(),httpVerticleDeployment.completer());
      return httpVerticleDeployment;
    }).setHandler(ar -> {
      if (ar.succeeded()) {
        startFuture.complete();
      } else {
        startFuture.fail(ar.cause());
      }
    });
  }
}
