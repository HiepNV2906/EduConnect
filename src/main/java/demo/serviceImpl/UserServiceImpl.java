package demo.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.Enum.VaiTro;
import demo.dto.UserDTO;
import demo.entity.User;
import demo.exception.UserException;
import demo.mapper.UserMapper;
import demo.repository.ThongBaoRepository;
import demo.repository.UserRepository;
import demo.request.RegisterUserRequest;
import demo.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	@Autowired
	ThongBaoRepository thongBaoRepository;
	
	@Override
	public User addUser(RegisterUserRequest registerUserRequest) {
		if(isEmailExist(registerUserRequest.getEmail())) {
			throw new UserException("Email đã tồn tại!!!");
		}
		User u = registerUserRequest.toUserEntity();
		u.setId(null);
		u.setVaitro(VaiTro.ADMIN);
		User user = userRepository.save(u);
		
		return user;
	}

	@Override
	public User updateUser(UserDTO userDTO) {
		User u = UserMapper.update(getUserById(userDTO.getId()), userDTO);
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

}
