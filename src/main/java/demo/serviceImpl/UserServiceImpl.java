package demo.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import demo.Enum.VaiTro;
import demo.entity.User;
import demo.exception.StorageException;
import demo.exception.UserException;
import demo.mapper.UserMapper;
import demo.repository.ThongBaoRepository;
import demo.repository.UserRepository;
import demo.request.ChangePassword;
import demo.request.ForgetPassword;
import demo.request.RegisterUserRequest;
import demo.service.EmailService;
import demo.service.StorageService;
import demo.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	@Autowired
	ThongBaoRepository thongBaoRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	EmailService emailService;
	@Autowired
	StorageService storageService;
	
	@Override
	public User addUser(RegisterUserRequest registerUserRequest) throws StorageException {
		if(isEmailExist(registerUserRequest.getEmail())) {
			throw new UserException("Email đã tồn tại!!!");
		}
		User u = registerUserRequest.toUserEntity();
		u.setId(null);
		u.setNgaytao(new Date());
		u.setPassword(bCryptPasswordEncoder.encode(registerUserRequest.getPassword()));
		u.setVaitro(VaiTro.ADMIN);
		
		if(!registerUserRequest.getAvata().isEmpty()) {
			UUID uuid1=UUID.randomUUID();
			String uuString1=uuid1.toString();
			u.setAvata(storageService.getStoredFilename(registerUserRequest.getAvata(), uuString1));
			storageService.store(registerUserRequest.getAvata(), u.getAvata());
		}
		else {
			throw new StorageException("File trống");
		}
		
		User user = userRepository.save(u);
		
		return user;
	}

	@Override
	public User updateUser(RegisterUserRequest registerUserRequest) throws StorageException {
		User u = UserMapper.update(getUserById(registerUserRequest.getId()), registerUserRequest);
		
		if(!registerUserRequest.getAvata().isEmpty()) {
			u.setAvata(storageService.getStoredFilename(registerUserRequest.getAvata(), u.getAvata()));
			storageService.store(registerUserRequest.getAvata(), u.getAvata());
		}
		
		User user = userRepository.save(u);
		return user;
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User getUserById(Long id) {
		User u = userRepository.findById(id).get();
		return u;
	}

	@Override
	public List<User> getListUser() {
		List<User> list = userRepository.findAll();
		return list;
	}

	@Override
	public Page<User> getListUser(Pageable pageable) {
		Page<User> page = userRepository.findAll(pageable);
		return page;
	}

	@Override
	public User findByEmail(String email) {
		User u = userRepository.findByEmail(email).get();
		return u;
	}

	@Override
	public boolean isEmailExist(String email) {
		Optional<User> u = userRepository.findByEmail(email);
		return u.isPresent();
	}

	@Override
	public List<User> findBySdt(String sdt) {
		List<User> list = userRepository.findAll();
		return list;
	}

	@Override
	public Page<User> findBySdt(String sdt, Pageable pageable) {
		Page<User> page = userRepository.findBySdt(sdt, pageable);
		return page;
	}

	@Override
	public List<User> findByVaitro(VaiTro vaiTro) {
		List<User> list = userRepository.findByVaitro(vaiTro);
		return list;
	}

	@Override
	public Page<User> findByVaitro(VaiTro vaiTro, Pageable pageable) {
		Page<User> page = userRepository.findByVaitro(vaiTro, pageable);
		return page;
	}

	@Override
	public List<User> findByKey(String key) {
		List<User> list = userRepository.findByHotenContainingOrEmailContainingOrSdtContaining(key, key, key);
		return list;
	}

	@Override
	public Page<User> findByKey(String key, Pageable pageable) {
		Page<User> page = userRepository.findByHotenContainingOrEmailContainingOrSdtContaining(key, key, key, pageable);
		return page;
	}

	@Override
	public User changePassword(String username, ChangePassword changePassword) {
		if (!changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {
			throw new UserException("Nhập lại mật khẩu không trùng khớp");
		}
		Optional<User> userOption = userRepository.findByEmail(username);
		if(!userOption.isPresent()) {
			throw new UserException("Email không tồn tại");
		}
		User user = userOption.get();
		user.setPassword(bCryptPasswordEncoder.encode(changePassword.getNewPassword()));
		user = userRepository.save(user);
		return user;
	}

	@Override
	public User forgetPassword(ForgetPassword forgetPassword) {
		Optional<User> user = userRepository.findByEmail(forgetPassword.getEmail());
		if (!user.isPresent()) {
			throw new UserException("Email không tồn tại");
		}
		User p = user.get();
		String randomPassword = RandomStringUtils.random(10, true, true);
		String mailSubject = "Khôi phục mật khẩu";
		String mailContent = "<h2>Mật khẩu mới của bạn</h2>"
				+ "<h3>Email: " + p.getEmail() +  "<br/> Mật khẩu mới: " + randomPassword + "</h3>";
			
		emailService.sendSimpleMail(new demo.response.EmailDetails(p.getEmail(), mailContent, mailSubject, null));
		p.setPassword(bCryptPasswordEncoder.encode(randomPassword));
		p = userRepository.save(p);
		return p;
	}

}
