package demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import demo.entity.ThanhToan;

@Repository
public interface ThanhToanRepository extends JpaRepository<ThanhToan, String>{
	
	@Query(value = "SELECT * FROM thanhtoan WHERE ungtuyenid = ?1", nativeQuery = true)
	public ThanhToan findByUngTuyenId(Long id);
	
	@Query(value = "SELECT DISTINCT t.* FROM thanhtoan t "
			+ "INNER JOIN ungtuyen u ON t.ungtuyenid=u.id "
			+ "WHERE u.giasuid = ?1", nativeQuery = true)
	public List<ThanhToan> findByGiaSuId(Long giasuid);
	
	@Query(value = "SELECT DATE_FORMAT(ngaythanhtoan, '%m/%Y'), SUM(sotien) "
			+ "FROM thanhtoan "
			+ "WHERE ngaythanhtoan BETWEEN ?1 AND ?2 "
			+ "GROUP BY DATE_FORMAT(ngaythanhtoan, '%m/%Y') "
			+ "ORDER BY ngaythanhtoan", nativeQuery = true)
	public List<Object[]> thongKeDoanhThuTheoThang(String from, String to);
	
	@Query(value = "SELECT * "
			+ "FROM thanhtoan "
			+ "WHERE ngaythanhtoan BETWEEN ?1 AND ?2 "
			+ "ORDER BY ngaythanhtoan", nativeQuery = true)
	public List<ThanhToan> findByNgayThanhToan(Date from, Date to);
}
