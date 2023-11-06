package demo.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import demo.exception.JwtException;

//import com.example.demo.exception.JwtException;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.gson.GsonFactory;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class JwtFilter extends OncePerRequestFilter{

//	@Value("${spring.security.oauth2.client.registration.google.client-id}")
//    private String CLIENT_ID;
	
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	CustomUserDetailService jwtDetailServiceImpl;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String headerAuth = request.getHeader("Authorization");
            System.out.println("---*---");
            String username = extractUsername(headerAuth);
            UserDetails userDetails = jwtDetailServiceImpl.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
	}
    
//    public String parseJwt(HttpServletRequest request) {
//        String headerAuth = request.getHeader("Authorization");
//        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
//            return headerAuth.substring(7, headerAuth.length());
//        }
//        return null;
//    }
    
    public String parseJwt(String headerAuth) {
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
    
//    public String decodeIdToken(String idTokenString) {
//        try {
//        	NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
//        	GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
//        	GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
//        		    .setAudience(Collections.singletonList(CLIENT_ID))
//        		    .build();
//        	GoogleIdToken idToken = verifier.verify(idTokenString);
//        	if (idToken != null) {
//        		  Payload payload = idToken.getPayload();
//        		  return payload.getEmail();
//        	}
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    
    public String extractUsername(String headerAuth) {
    	if (!StringUtils.hasText(headerAuth)) {
    		throw new JwtException("JWT claims string is empty");
        }
    	if (headerAuth.startsWith("Bearer ")) {
            String jwt = headerAuth.substring(7, headerAuth.length());
            if (jwt != null && jwtUtil.validateToken(jwt)) {
                String username = jwtUtil.extractUsername(jwt);
                return username;
            }
        }
//        if (headerAuth.startsWith("OAuth2 ")) {
//            String idTokenString = headerAuth.substring(7, headerAuth.length());
//            if (idTokenString != null) {
//            	try {
//                	NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
//                	GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
//                	GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
//                		    .setAudience(Collections.singletonList(CLIENT_ID))
//                		    .build();
//                	GoogleIdToken idToken = verifier.verify(idTokenString);
//                	if (idToken != null) {
//                		  Payload payload = idToken.getPayload();
//                		  return payload.getEmail();
//                	}
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        throw new JwtException("Invalid JWT token");
    }

}
