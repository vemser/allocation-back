package br.com.allocation.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final TokenService tokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers()
                .frameOptions().disable()
                .and().cors()
                .and().csrf().disable()
                .authorizeHttpRequests((authz) ->
                        authz.antMatchers("/auth", "/auth/atualizar-senha", "/auth/register", "/auth/recuperar-senha/**", "/auth/upload/").permitAll()
                                .antMatchers( "/auth/logged").hasAnyRole("ADMINISTRADOR", "GESTOR", "GESTAO_DE_PESSOAS", "INSTRUTOR")
                                .antMatchers("/tecnologia/**").hasAnyRole("ADMINISTRADOR", "GESTOR", "GESTAO_DE_PESSOAS", "INSTRUTOR")
                                .antMatchers("/aluno/**").hasAnyRole("ADMINISTRADOR", "GESTOR", "GESTAO_DE_PESSOAS", "INSTRUTOR")

                                .antMatchers(HttpMethod.POST,"/reserva-alocacao/**").hasAnyRole("ADMINISTRADOR", "GESTOR", "GESTAO_DE_PESSOAS")
                                .antMatchers(HttpMethod.PUT,"/reserva-alocacao/**").hasAnyRole("ADMINISTRADOR", "GESTOR", "GESTAO_DE_PESSOAS")
                                .antMatchers(HttpMethod.DELETE,"/reserva-alocacao/**").hasAnyRole("ADMINISTRADOR", "GESTOR", "GESTAO_DE_PESSOAS")
                                .antMatchers(HttpMethod.GET,"/reserva-alocacao/**").hasAnyRole("ADMINISTRADOR", "GESTOR", "GESTAO_DE_PESSOAS", "INSTRUTOR")

                                .antMatchers(HttpMethod.POST, "/cliente/**").hasAnyRole("ADMINISTRADOR", "GESTOR")
                                .antMatchers(HttpMethod.PUT, "/cliente/**").hasAnyRole("ADMINISTRADOR", "GESTOR")
                                .antMatchers(HttpMethod.DELETE, "/cliente/**").hasAnyRole("ADMINISTRADOR", "GESTOR")
                                .antMatchers(HttpMethod.GET, "/cliente/**").hasAnyRole("ADMINISTRADOR", "GESTOR", "GESTAO_DE_PESSOAS", "INSTRUTOR")

                                .antMatchers(HttpMethod.POST,"/avaliacao/**").hasAnyRole("ADMINISTRADOR", "GESTAO_DE_PESSOAS", "INSTRUTOR")
                                .antMatchers(HttpMethod.PUT,"/avaliacao/**").hasAnyRole("ADMINISTRADOR", "GESTAO_DE_PESSOAS", "INSTRUTOR")
                                .antMatchers(HttpMethod.DELETE,"/avaliacao/**").hasAnyRole("ADMINISTRADOR", "GESTAO_DE_PESSOAS", "INSTRUTOR")
                                .antMatchers(HttpMethod.GET,"/avaliacao/**").hasAnyRole("ADMINISTRADOR", "GESTOR", "GESTAO_DE_PESSOAS", "INSTRUTOR")

                                .antMatchers(HttpMethod.POST,"/vaga/**").hasAnyRole("ADMINISTRADOR", "GESTOR", "GESTAO_DE_PESSOAS")
                                .antMatchers(HttpMethod.PUT,"/vaga/**").hasAnyRole("ADMINISTRADOR", "GESTOR", "GESTAO_DE_PESSOAS")
                                .antMatchers(HttpMethod.DELETE,"/vaga/**").hasAnyRole("ADMINISTRADOR", "GESTOR", "GESTAO_DE_PESSOAS")
                                .antMatchers(HttpMethod.GET,"/vaga/**").hasAnyRole("ADMINISTRADOR", "GESTOR", "GESTAO_DE_PESSOAS", "INSTRUTOR")

                                .antMatchers(HttpMethod.POST,"/usuario/**").hasAnyRole("ADMINISTRADOR")
                                .antMatchers(HttpMethod.PUT,"/usuario/**").hasAnyRole("ADMINISTRADOR")
                                .antMatchers(HttpMethod.DELETE,"/usuario/**").hasAnyRole("ADMINISTRADOR")
                                .antMatchers(HttpMethod.GET,"/usuario/**").hasAnyRole("ADMINISTRADOR", "GESTOR", "GESTAO_DE_PESSOAS", "INSTRUTOR")

                                .antMatchers("/programa/**").hasAnyRole("ADMINISTRADOR", "GESTOR", "GESTAO_DE_PESSOAS", "INSTRUTOR")
                                .antMatchers("/cargo/**").hasAnyRole("ADMINISTRADOR")
                                .anyRequest().authenticated()
                );
        http.addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .exposedHeaders("Authorization");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

}
