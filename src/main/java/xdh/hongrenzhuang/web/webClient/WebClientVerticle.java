package xdh.hongrenzhuang.web.webClient;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.client.WebClient;
import io.vertx.serviceproxy.ServiceBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xdh.hongrenzhuang.web.webClient.WxWebClientService.WxWebClientService;

/**
 * Created by ironresolve on 2017/11/19.
 */
public class WebClientVerticle extends AbstractVerticle {

    public static final String ADDRESS_WEBCLIENT_WX = "wx.webclient";

    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientVerticle.class);
    @Override
    public void start(Future<Void> startFuture) throws Exception {

        WebClient client = WebClient.create(vertx);
        WxWebClientService wxWebClientService = WxWebClientService.create(vertx,client);

        ServiceBinder binder = new ServiceBinder(vertx);
        binder
                .setAddress(ADDRESS_WEBCLIENT_WX)
                .register(WxWebClientService.class,wxWebClientService);
        super.start(startFuture);
    }
}
