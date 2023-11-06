package demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
		http
			.csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
//	        	.requestMatchers(AntPathRequestMatcher.antMatcher("/api/**")).authenticated()
	        	.requestMatchers(AntPathRequestMatcher.antMatcher("/changePassword")).authenticated()
	        	.anyRequest().permitAll()
	        )
	        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .httpBasic(Customizer.withDefaults())
//	        .oauth2Login().successHandler(new CustomOAuth2AuthenticationSuccessHandler())
	        ;
		
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return  http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{ 
    	return authenticationConfiguration.getAuthenticationManager();
    }
    

}
