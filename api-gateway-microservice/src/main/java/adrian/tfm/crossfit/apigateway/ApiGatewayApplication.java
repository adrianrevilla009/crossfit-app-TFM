package adrian.tfm.crossfit.apigateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ApiGatewayApplication {

    @Value("${service.classes}")
    private String classesUrl; // http://localhost:8080/

    @Value("${service.documents}")
    private String documentsUrl; // http://localhost:8081/

    @Value("${service.security}")
    private String securityUrl; // http://localhost:8082/
	
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("classes_route", r -> r
                        .path("/api/classes/**")
                        .filters(f -> f.rewritePath("/api/classes/(?<segment>.*)", "/${segment}")
                                .addRequestHeader("Authorization", "Bearer {token}"))
                        .uri("http://localhost:8080/"))
                .route("documents_route", r -> r
                        .path("/api/documents/**")
                        .filters(f -> f.rewritePath("/api/documents/(?<segment>.*)", "/${segment}")
                                .addRequestHeader("Authorization", "Bearer {token}"))
                        .uri("http://localhost:8081/"))
                .route("auth_route", r -> r
                        .path("/api/auth/**")
                        .filters(f -> f.rewritePath("/api/auth/(?<segment>.*)", "/${segment}"))
                        .uri("http://localhost:8082/"))
                .route("users_route", r -> r
                        .path("/api/users/**")
                        .filters(f -> f.rewritePath("/api/users/(?<segment>.*)", "/${segment}")
                                .addRequestHeader("Authorization", "Bearer {token}"))
                        .uri("http://localhost:8082/"))
                .build();
    }

    @Bean
    public WebClient.Builder webClient(){
        return WebClient.builder();
    }
}
