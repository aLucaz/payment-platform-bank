package pe.client.custom.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import pe.client.custom.app.util.constant.Api;
import pe.client.custom.app.util.constant.Authority;

@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .mvcMatchers(HttpMethod.POST, Api.API_TRANSFER_PATH).hasAnyAuthority(Authority.BANK_ALL_AUTH)
            .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt();

        return http.build();
    }
}
