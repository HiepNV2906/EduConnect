package demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import demo.Enum.TrangThaiThongBao;
import demo.entity.ThongBao;

@Repository
public interface ThongBaoRepository extends JpaRepository<ThongBao, Long>{
	
	@Query(value = "SELECT * FROM thongbao WHERE userid = ?1", nativeQuery = true)
	public List<ThongBao> findByUserId(Long id);
	
	@Query(value = "SELECT * FROM thongbao WHERE userid = ?1", nativeQuery = true)
	public Page<ThongBao> findByUserId(Long id, Pageable pageable);
	
	public List<ThongBao> findByTrangthaithongbao(TrangThaiThongBao trangthaithongbao);
	
	public Page<ThongBao> findByTrangthaithongbao(TrangThaiThongBao trangthaithongbao, Pageable pageable);
}
