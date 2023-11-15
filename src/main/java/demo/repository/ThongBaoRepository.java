package demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import demo.Enum.TrangThaiThongBao;
import demo.entity.ThongBao;
import demo.entity.User;

@Repository
public interface ThongBaoRepository extends JpaRepository<ThongBao, Long>{
	
	public List<ThongBao> findByUserOrderByNgayDesc(User user);
	
	public Page<ThongBao> findByUserOrderByNgayDesc(User user, Pageable pageable);
	
	public List<ThongBao> findByTrangthaithongbao(TrangThaiThongBao trangthaithongbao);
	
	public Page<ThongBao> findByTrangthaithongbao(TrangThaiThongBao trangthaithongbao, Pageable pageable);
	
	@Transactional
	public void deleteByNgayBefore(Date date);
}
