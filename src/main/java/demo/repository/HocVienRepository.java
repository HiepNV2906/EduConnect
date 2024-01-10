package demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import demo.Enum.TrangThaiUser;
import demo.entity.HocVien;

@Repository
public interface HocVienRepository extends JpaRepository<HocVien, Long>{
	@Query(value = "SELECT * FROM hocvien WHERE sdt LIKE %:key% OR hoten LIKE %:key% "
			+ "OR diachi LIKE %:key% OR quan LIKE %:key%", nativeQuery = true)
	public List<HocVien> findByKeyword(@Param("key") String keyword);
	
	@Query(value = "SELECT * FROM hocvien WHERE sdt LIKE %:key% OR hoten LIKE %:key% "
			+ "OR diachi LIKE %:key% OR quan LIKE %:key%", nativeQuery = true)
	public Page<HocVien> findByKeyword(@Param("key") String keyword, Pageable pageable);
	
	public List<HocVien> findByTrangthai(TrangThaiUser trangthai);
	public Page<HocVien> findByTrangthai(TrangThaiUser trangthai, Pageable pageable);
	
	@Query(value = "SELECT DATE_FORMAT(ngaytao, '%m/%Y'), COUNT(*) "
			+ "FROM hocvien "
			+ "WHERE trangthai=?3 AND (ngaytao BETWEEN ?1 AND ?2) "
			+ "GROUP BY DATE_FORMAT(ngaytao, '%m/%Y') "
			+ "ORDER BY ngaytao", nativeQuery = true)
	public List<Object[]> thongkehocvienmoitheothang(String from, String to, String trangthai);
}
