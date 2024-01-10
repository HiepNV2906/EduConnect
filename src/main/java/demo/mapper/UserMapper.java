package demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import demo.dto.ThongBaoDTO;
import demo.dto.UserDTO;
import demo.entity.User;
import demo.request.RegisterUserRequest;

public class UserMapper {
	
	public static UserDTO toDTO(User user) {
		UserDTO u = new UserDTO();
		u.setId(user.getId());
		u.setHoten(user.getHoten());
		u.setSdt(user.getSdt());
		u.setEmail(user.getEmail());
		u.setAvata(user.getAvata());
		u.setGioitinh(user.getGioitinh());
		u.setNgaysinh(user.getNgaysinh());
		u.setNgaytao(user.getNgaytao());
		List<ThongBaoDTO> list = ThongBaoMapper.toListDTO(user.getDsthongbao());
		u.setDsthongbao(list);
		return u;
	}
	
	public static User toEntity(UserDTO userDTO) {
		User u = new User();
		u.setId(userDTO.getId());
		u.setHoten(userDTO.getHoten());
		u.setSdt(userDTO.getSdt());
		u.setEmail(userDTO.getEmail());
		u.setAvata(userDTO.getAvata());
		u.setGioitinh(userDTO.getGioitinh());
		u.setNgaysinh(userDTO.getNgaysinh());
		u.setNgaytao(userDTO.getNgaytao());
		return u;
	}
	
	public static User update(User user, RegisterUserRequest registerUserRequest) {
		user.setEmail(registerUserRequest.getEmail());
		user.setHoten(registerUserRequest.getHoten());
		user.setSdt(registerUserRequest.getSdt());
		user.setGioitinh(registerUserRequest.getGioitinh());
		user.setNgaysinh(registerUserRequest.getNgaysinh());
		return user;
	}
	
	public static List<UserDTO> toListDTO(List<User> list){
		if(list == null)
			return null;
		return list.stream().map(u -> toDTO(u)).collect(Collectors.toList());
	}
	
	public static Page<UserDTO> toPageDTO(Page<User> page){
		if(page == null)
			return null;
		List<UserDTO> pageDTO = page.stream().map(u -> toDTO(u)).collect(Collectors.toList());
		return new PageImpl<>(pageDTO, page.getPageable(), page.getSize());
	}
}
