package demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import demo.security.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig{
	
	@Autowired
	JwtFilter jwtFilter;
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
	
	@Bean
    public SecurityFilterChain formLoginFilterChain (HttpSecurity http) throws Exception{ 
		http.cors().and()
			.csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	        	.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/uploads/images/*")).permitAll()
	        	.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/login")).permitAll()
	        	.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/forgetPassword")).permitAll()
	        	.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/chude")).permitAll()
	        	.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/chude/*")).permitAll()
	        	.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/giasu/*")).permitAll()
	        	.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/giasu")).permitAll()
	        	.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hocvien")).permitAll()
	        	.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/lop/*")).permitAll()
	        	.anyRequest().authenticated()
	        )
	        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .httpBasic(Customizer.withDefaults())
	        ;
		
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return  http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{ 
    	return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("http://127.0.0.1:5500");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
