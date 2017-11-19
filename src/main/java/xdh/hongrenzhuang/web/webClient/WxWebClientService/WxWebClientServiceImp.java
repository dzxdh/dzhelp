package xdh.hongrenzhuang.web.webClient.WxWebClientService;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;

/**
 * Created by ironresolve on 2017/11/19.
 */
public class WxWebClientServiceImp implements WxWebClientService {
    private Vertx vertx;
    private WebClient webClient;

    public WxWebClientServiceImp(Vertx vertx, WebClient webClient) {
        this.vertx = vertx;
        this.webClient = webClient;
    }

}
