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
}
