package com.jjm.cleanspring.infrastructure.config;

//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfig {
//    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
//    private final CustomAccessDeniedHandler accessDeniedHandler;
//
//    @Bean
//    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(
//                authorize -> authorize.requestMatchers("/api-ui/**", "/api-docs/**", "/swagger-ui/**")
//                                      .permitAll()
//                                      .anyRequest()
//                                      .authenticated());
//
//        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())
//                                                  .authenticationEntryPoint(authenticationEntryPoint)
//                                                  .accessDeniedHandler(accessDeniedHandler));
//
//        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        return http.build();
//    }
//}
