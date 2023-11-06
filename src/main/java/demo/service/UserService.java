package demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.Enum.VaiTro;
import demo.entity.User;
import demo.exception.StorageException;
import demo.request.ChangePassword;
import demo.request.ForgetPassword;
import demo.request.RegisterUserRequest;

public interface UserService {
	public User addUser(RegisterUserRequest registerUserRequest) throws StorageException;
	public User updateUser(RegisterUserRequest registerUserRequest) throws StorageException;
	public User changePassword(String username, ChangePassword changePassword);
	public User forgetPassword(ForgetPassword forgetPassword);
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
