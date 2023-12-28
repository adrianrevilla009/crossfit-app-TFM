package adrian.tfm.crossfit.apigateway.documents;

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
public class DocumentsApiConfiguration {

    private static final String LOCATION_HEADER = "Location";

    @Value("${service.documents}")
    private String documentsUrl;

    @Bean
    public RouteLocator documentsProxyRouting(RouteLocatorBuilder builder) {

        URI backendUri = URI.create(this.documentsUrl);
        return builder.routes()
                .route(r -> r
                        .path("/api/documents/**")
                        .filters(f -> f
                                .prefixPath(backendUri.getPath())
                                .rewriteLocationResponseHeader(NEVER_STRIP.name(),
                                        LOCATION_HEADER,
                                        null,
                                        "http|https")
                                .rewriteResponseHeader(LOCATION_HEADER, backendUri.getPath(), "")
                                .filter(new RequestLoggingFilter())
                                .filter(new ResponseLoggingFilter()))
                        .uri(this.documentsUrl))
                .build();
    }

}