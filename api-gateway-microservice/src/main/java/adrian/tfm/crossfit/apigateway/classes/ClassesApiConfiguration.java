package adrian.tfm.crossfit.apigateway.classes;

import adrian.tfm.crossfit.apigateway.config.RequestLoggingFilter;
import adrian.tfm.crossfit.apigateway.config.ResponseLoggingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.cloud.gateway.filter.factory.RewriteLocationResponseHeaderGatewayFilterFactory.StripVersion.NEVER_STRIP;

import java.net.URI;

@Configuration
public class ClassesApiConfiguration {

    private static final String LOCATION_HEADER = "Location";

    @Value("${service.classes}")
    private String classesUrl;

    @Bean
    public RouteLocator classesProxyRouting(RouteLocatorBuilder builder) {

        URI backendUri = URI.create(this.classesUrl);
        return builder.routes()
                .route(r -> r
                        .path("/api/classes/**")
                        .filters(f -> f
                                .prefixPath(backendUri.getPath())
                                .rewriteLocationResponseHeader(NEVER_STRIP.name(),
                                        LOCATION_HEADER,
                                        null,
                                        "http|https")
                                .rewriteResponseHeader(LOCATION_HEADER, backendUri.getPath(), "")
                                .filter(new RequestLoggingFilter())
                                .filter(new ResponseLoggingFilter()))
                        .uri(this.classesUrl))
                .build();
    }

}
