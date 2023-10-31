package demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.Enum.VaiTro;
import demo.dto.UserDTO;
import demo.entity.User;
import demo.mapper.UserMapper;
import demo.request.Status;
import demo.request.RegisterUserRequest;
import demo.response.BaseResponse;
import demo.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final int sizeOfPage = 10;
	
	@Autowired
	UserService userService;
	
	@PostMapping(value = "", produces = "application/json")
	public BaseResponse<?> addUser(@RequestBody RegisterUserRequest registerUserRequest){
		try {
			User u = userService.addUser(registerUserRequest);
			UserDTO data = UserMapper.toDTO(u);
			return new BaseResponse<>("Successful!", data, HttpStatus.CREATED);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> updateUser(
			@RequestBody UserDTO userDTO,
			@PathVariable("id") Long id){
		try {
			userDTO.setId(id);
			User u = userService.updateUser(userDTO);
			UserDTO data = UserMapper.toDTO(u);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> deleteUser(
			@PathVariable("id") Long id){
		try {
			userService.deleteUser(id);
			return new BaseResponse<>("Successful!", null, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> getUserById(
			@PathVariable("id") Long id){
		try {
			User u = userService.getUserById(id);
			UserDTO data = UserMapper.toDTO(u);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "", produces = "application/json")
	public BaseResponse<?> getListUser(
			@RequestParam(name="page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<User> pageUser = userService.getListUser(PageRequest.of(page.orElse(0), sizeOfPage));
				Page<UserDTO> data = UserMapper.toPageDTO(pageUser);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<User> listUser = userService.getListUser();
				List<UserDTO> data = UserMapper.toListDTO(listUser);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/role", produces = "application/json")
	public BaseResponse<?> getListUserByVaiTro(
			@RequestBody Status status,
			@RequestParam(name="page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<User> pageUser = userService.findByVaitro(VaiTro.valueOf(status.getStatus()), PageRequest.of(page.orElse(0), sizeOfPage));
				Page<UserDTO> data = UserMapper.toPageDTO(pageUser);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<User> listUser = userService.findByVaitro(VaiTro.valueOf(status.getStatus()));
				List<UserDTO> data = UserMapper.toListDTO(listUser);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/search", produces = "application/json")
	public BaseResponse<?> getListUserByKey(
			@RequestParam(name="key") String key,
			@RequestParam(name="page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<User> pageUser = userService.findByKey(key, PageRequest.of(page.orElse(0), sizeOfPage));
				Page<UserDTO> data = UserMapper.toPageDTO(pageUser);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<User> listUser = userService.findByKey(key);
				List<UserDTO> data = UserMapper.toListDTO(listUser);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
}
