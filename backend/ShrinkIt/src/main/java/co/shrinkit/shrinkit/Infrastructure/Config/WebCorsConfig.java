package co.shrinkit.shrinkit.Infrastructure.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebCorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        // Defines a specific CORS configuration
        CorsConfiguration config = new CorsConfiguration();

        // Add the frontend domain's or IP
        config.addAllowedOrigin("http://localhost:5173");

        // Allow the credentials
        config.setAllowCredentials(true);

        // Allow necessary HTTP methods
        config.addAllowedMethod("*");

        // Allow necessary headers for the requests
        config.addAllowedHeader("*");

        // Set the origin in the URL to apply the configuration in all the endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
