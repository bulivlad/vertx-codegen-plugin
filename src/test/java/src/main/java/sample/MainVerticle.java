package src.main.java.sample;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

/**
 * @author vladclaudiubulimac on 2019-04-25.
 */
public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.get("/health").handler(context -> context.response().end("UP!"));
        router.get("/shutdown").handler(context -> {
            context.response().end("Bye!");
            vertx.close();
        });

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080);
    }
}
