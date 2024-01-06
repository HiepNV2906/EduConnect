package demo.repository;

import java.util.Date;
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
			+ "WHERE (sdt LIKE %:key% OR hoten LIKE %:key% OR diachi LIKE %:key% OR "
			+ "gioithieu LIKE %:key% OR kinhnghiem LIKE %:key% OR thanhtich LIKE %:key% OR "
			+ "quequan LIKE %:key% OR truong LIKE %:key%) AND g.trangthai LIKE %:trangthai% AND "
			+ "g.khuvucday LIKE %:quan% AND g.gioitinh LIKE %:gioitinh% "
			+ "AND c.tenmonhoc LIKE %:mon% AND c.trinhdo LIKE %:trinhdo%", nativeQuery = true)
	public List<GiaSu> findByFilter(@Param("key") String keyword, @Param("quan") String quan, 
			@Param("gioitinh") String gioitinh, 
			@Param("mon") String mon, @Param("trinhdo") String trinhdo, @Param("trangthai") String trangthai);
	
	@Query(value = "SELECT DISTINCT g.* FROM giasu_chude gc "
			+ "INNER JOIN giasu g ON gc.dsgiasu_id=g.id "
			+ "INNER JOIN chude c ON gc.dschude_id=c.id "
			+ "WHERE (sdt LIKE %:key% OR hoten LIKE %:key% OR diachi LIKE %:key% OR "
			+ "gioithieu LIKE %:key% OR kinhnghiem LIKE %:key% OR thanhtich LIKE %:key% OR "
			+ "quequan LIKE %:key% OR truong LIKE %:key%) AND "
			+ "g.khuvucday LIKE %:quan% AND g.gioitinh LIKE %:gioitinh% "
			+ "AND c.tenmonhoc LIKE %:mon% AND c.trinhdo LIKE %:trinhdo%", nativeQuery = true)
	public Page<GiaSu> findByFilter(@Param("key") String keyword, @Param("quan") String quan,
			@Param("gioitinh") String gioitinh, @Param("mon") String mon, @Param("trinhdo") String trinhdo,
			Pageable pageable);
	
	public List<GiaSu> findByTrangthai(TrangThaiUser trangthai);
	public Page<GiaSu> findByTrangthai(TrangThaiUser trangthai, Pageable pageable);
	
	public List<GiaSu> findAllByOrderByNgaytaoDesc();
	public Page<GiaSu> findAllByOrderByNgaytaoDesc(Pageable pageable);
	
	public List<GiaSu> findTop8ByTrangthaiOrderByNgaytaoDesc(TrangThaiUser trangthai);
	
	@Query(value = "SELECT DATE_FORMAT(ngaytao, '%m/%Y'), COUNT(*) "
			+ "FROM giasu "
			+ "WHERE trangthai=?3 AND (ngaytao BETWEEN ?1 AND ?2) "
			+ "GROUP BY DATE_FORMAT(ngaytao, '%m/%Y') "
			+ "ORDER BY DATE_FORMAT(ngaytao, '%m/%Y')", nativeQuery = true)
	public List<Object[]> thongkegiasumoitheothang(String from, String to, String trangthai);
	
	@Query(value = "SELECT * "
			+ "FROM giasu "
			+ "WHERE ngaytao BETWEEN ?1 AND ?2 "
			+ "ORDER BY ngaytao", nativeQuery = true)
	public List<GiaSu> giasumoi(Date from, Date to);
	
	@Query(value = "SELECT quan, COUNT(*) "
			+ "FROM giasu "
			+ "WHERE trangthai=?3 AND (ngaytao BETWEEN ?1 AND ?2) "
			+ "GROUP BY quan "
			+ "ORDER BY quan", nativeQuery = true)
	public List<Object[]> thongkephanbogiasutheoquan(String from, String to, String trangthai);
}
