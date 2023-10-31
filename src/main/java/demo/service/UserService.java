package demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.Enum.VaiTro;
import demo.dto.UserDTO;
import demo.entity.User;
import demo.request.RegisterUserRequest;

public interface UserService {
	public User addUser(RegisterUserRequest registerUserRequest);
	public User updateUser(UserDTO userDTO);
	public void deleteUser(Long id);
	public User getUserById(Long id);
	public List<User> getListUser();
	public Page<User> getListUser(Pageable pageable);
	public User findByEmail(String email);
	public boolean isEmailExist(String email);
	public List<User> findBySdt(String sdt);
	public Page<User> findBySdt(String sdt, Pageable pageable);
	public List<User> findByVaitro(VaiTro vaiTro);
	public Page<User> findByVaitro(VaiTro vaiTro, Pageable pageable);
	public List<User> findByKey(String key);
	public Page<User> findByKey(String key, Pageable pageable);
}
