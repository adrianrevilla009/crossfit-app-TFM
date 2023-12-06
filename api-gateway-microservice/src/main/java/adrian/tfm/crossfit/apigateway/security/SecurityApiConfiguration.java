package adrian.tfm.crossfit.apigateway.security;

import adrian.tfm.crossfit.apigateway.config.RequestLoggingFilter;
import adrian.tfm.crossfit.apigateway.config.ResponseLoggingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

import static org.springframework.cloud.gateway.filter.factory.RewriteLocationResponseHeaderGatewayFilterFactory.StripVersion.NEVER_STRIP;

@Configuration
public class SecurityApiConfiguration {

    private static final String LOCATION_HEADER = "Location";

    @Value("${service.security}")
    private String securityUrl;

    @Bean
    public RouteLocator securityProxyRouting(RouteLocatorBuilder builder) {

        URI backendUri = URI.create(this.securityUrl);
        return builder.routes()
                .route(r -> r
                        .path("/api/auth/**")
                        .filters(f -> f
                                .prefixPath(backendUri.getPath())
                                .rewriteLocationResponseHeader(NEVER_STRIP.name(),
                                        LOCATION_HEADER,
                                        null,
                                        "http|https")
                                .rewriteResponseHeader(LOCATION_HEADER, backendUri.getPath(), "")
                                .filter(new RequestLoggingFilter())
                                .filter(new ResponseLoggingFilter()))
                        .uri(this.securityUrl))
                .route(r -> r
                        .path("/api/user/**")
                        .filters(f -> f
                                .prefixPath(backendUri.getPath())
                                .rewriteLocationResponseHeader(NEVER_STRIP.name(),
                                        LOCATION_HEADER,
                                        null,
                                        "http|https")
                                .rewriteResponseHeader(LOCATION_HEADER, backendUri.getPath(), "")
                                .filter(new RequestLoggingFilter())
                                .filter(new ResponseLoggingFilter()))
                        .uri(this.securityUrl))
                .build();
    }

}
