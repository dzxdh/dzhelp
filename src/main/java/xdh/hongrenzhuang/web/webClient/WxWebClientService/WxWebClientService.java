package xdh.hongrenzhuang.web.webClient.WxWebClientService;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;

/**
 * Created by ironresolve on 2017/11/19.
 */
@ProxyGen
public interface WxWebClientService {

    static WxWebClientService create(Vertx vertx , WebClient webClient) {
        return  new WxWebClientServiceImp(vertx , webClient);
    }
    static WxWebClientService createProxy(Vertx vertx, String address) {
        return new WxWebClientServiceVertxEBProxy(vertx,address);
    }


}
