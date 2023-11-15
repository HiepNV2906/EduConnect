package demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.entity.RefreshToken;
import demo.request.ChangePassword;
import demo.request.ForgetPassword;
import demo.request.LoginRequest;
import demo.request.RefreshTokenRequest;
import demo.response.BaseResponse;
import demo.response.JwtResponse;
import demo.response.RefreshTokenResponse;
import demo.security.CustomUserDetail;
import demo.security.JwtFilter;
import demo.security.JwtUtil;
import demo.service.RefreshTokenService;
import demo.service.UserService;

@RestController
@RequestMapping("")
public class LoginController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtFilter jwtFilter;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	UserService userService;
	@Autowired
	RefreshTokenService refreshTokenService;
	
	@PostMapping(value = "/login", produces = "application/json")
	public BaseResponse<?> login(@RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			CustomUserDetail userDetailsImpl = (CustomUserDetail) authentication.getPrincipal();
			String jwt = jwtUtil.generateToken(userDetailsImpl);
			String refresh = refreshTokenService.createRefreshToken(userDetailsImpl.getId()).getToken();
			List<String> roles = userDetailsImpl.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
			return new BaseResponse<>("Successful", 
					new JwtResponse(jwt, refresh, "Bearer", userDetailsImpl.getId(),
							userDetailsImpl.getUsername(), roles), HttpStatus.OK);
		}
		catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/logout/{userid}", produces = "application/json")
	public BaseResponse<?> logout(@PathVariable("userid") Long userid) {
		try {
			SecurityContextHolder.clearContext();
			return new BaseResponse<>("Successful",null, HttpStatus.OK);
		}
		catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/changeRefreshToken", produces = "application/json")
	public BaseResponse<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		try {
			RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequest.getRefreshtoken());
			refreshTokenService.verifyExpiration(refreshToken);
			CustomUserDetail customUserDetail = CustomUserDetail.build(userService.getUserById(refreshTokenRequest.getUserid()));
			String jwt = jwtUtil.generateToken(customUserDetail);
			RefreshTokenResponse data = new RefreshTokenResponse(jwt, refreshToken.getToken(), "Bearer");
			return new BaseResponse<>("Successful",data, HttpStatus.OK);
		}
		catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = "/changePassword/{userid}", produces = "application/json")
	public BaseResponse<?> changePassword(
			@PathVariable("userid") Long userid, 
			@RequestBody ChangePassword changePassword) {
		try {
			userService.changePassword(userid, changePassword);
			return new BaseResponse<>("Successful",null, HttpStatus.OK);
		}
		catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/forgetPassword", produces = "application/json")
	public BaseResponse<?> forgetPassword(@RequestBody ForgetPassword forgetPassword) {
		try {
			userService.forgetPassword(forgetPassword);
			return new BaseResponse<>("Successful",null, HttpStatus.OK);
		}
		catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
}
