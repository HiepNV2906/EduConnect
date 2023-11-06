package demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import demo.Enum.TrangThaiUser;
import demo.entity.GiaSu;

@Repository
public interface GiaSuRepository extends JpaRepository<GiaSu, Long>{
	
	@Query(value = "SELECT * FROM giasu WHERE sdt LIKE %:key% OR hoten LIKE %:key% "
			+ "OR diachi LIKE %:key% OR gioithieu LIKE %:key% OR kinhnghiem LIKE %:key% OR "
			+ "thanhtich LIKE %:key% OR quequan LIKE %:key% OR truong LIKE %:key%", nativeQuery = true)
	public List<GiaSu> findByKeyword(@Param("key") String keyword);
	
	@Query(value = "SELECT * FROM giasu WHERE sdt LIKE %:key% OR hoten LIKE %:key% "
			+ "OR diachi LIKE %:key% OR gioithieu LIKE %:key% OR kinhnghiem LIKE %:key% OR "
			+ "thanhtich LIKE %:key% OR quequan LIKE %:key% OR truong LIKE %:key%", nativeQuery = true)
	public Page<GiaSu> findByKeyword(@Param("key") String keyword, Pageable pageable);
	
	@Query(value = "SELECT DISTINCT g.* FROM giasu_chude gc "
			+ "INNER JOIN giasu g ON gc.dsgiasu_id=g.id "
			+ "INNER JOIN chude c ON gc.dschude_id=c.id "
			+ "WHERE g.quan LIKE %:quan% AND g.nghenghiep LIKE %:nghenghiep% AND g.gioitinh LIKE %:gioitinh% "
			+ "AND c.tenmonhoc LIKE %:mon% AND c.trinhdo LIKE %:trinhdo%", nativeQuery = true)
	public List<GiaSu> findByFilter(@Param("quan") String quan, @Param("nghenghiep") String nghenghiep,
			@Param("gioitinh") String gioitinh, @Param("mon") String mon, @Param("trinhdo") String trinhdo);
	
	@Query(value = "SELECT DISTINCT g.* FROM giasu_chude gc "
			+ "INNER JOIN giasu g ON gc.dsgiasu_id=g.id "
			+ "INNER JOIN chude c ON gc.dschude_id=c.id "
			+ "WHERE g.quan LIKE %:quan% AND g.nghenghiep LIKE %:nghenghiep% AND g.gioitinh LIKE %:gioitinh% "
			+ "AND c.tenmonhoc LIKE %:mon% AND c.trinhdo LIKE %:trinhdo%", nativeQuery = true)
	public Page<GiaSu> findByFilter(@Param("quan") String quan, @Param("nghenghiep") String nghenghiep,
			@Param("gioitinh") String gioitinh, @Param("mon") String mon, @Param("trinhdo") String trinhdo,
			Pageable pageable);
	
	public List<GiaSu> findByTrangthai(TrangThaiUser trangthai);
	public Page<GiaSu> findByTrangthai(TrangThaiUser trangthai, Pageable pageable);
	
	public List<GiaSu> findAllByOrderByNgaytaoDesc();
	public Page<GiaSu> findAllByOrderByNgaytaoDesc(Pageable pageable);
	
	public List<GiaSu> findTop10ByOrderByNgaytaoDesc();
}
