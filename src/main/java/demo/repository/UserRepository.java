package demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.Enum.VaiTro;
import demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	public Optional<User> findByEmail(String email);
	public List<User> findBySdt(String sdt);
	public Page<User> findBySdt(String sdt, Pageable pageable);
	public List<User> findByVaitro(VaiTro vaitro);
	public Page<User> findByVaitro(VaiTro vaitro, Pageable pageable);	
	public List<User> findByHotenContainingOrEmailContainingOrSdtContaining(String key1, String key2, String key3);
	public Page<User> findByHotenContainingOrEmailContainingOrSdtContaining(String key1, String key2, String key3, Pageable pageable);
}
