package demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import demo.Enum.TrangThaiLop;
import demo.entity.Lop;

@Repository
public interface LopRepository extends JpaRepository<Lop, Long>{
	@Query(value = "SELECT * FROM lop WHERE tieude LIKE %:key% OR diachi LIKE %:key% OR "
			+ "motahs LIKE %:key% OR yeucaukhac LIKE %:key% OR truonggs LIKE %:key%", nativeQuery = true)
	public List<Lop> findByKeyword(@Param("key") String keyword);
	
	@Query(value = "SELECT * FROM lop WHERE tieude LIKE %:key% OR diachi LIKE %:key% OR "
			+ "motahs LIKE %:key% OR yeucaukhac LIKE %:key% OR truonggs LIKE %:key%", nativeQuery = true)
	public Page<Lop> findByKeyword(@Param("key") String keyword, Pageable pageable);
	
	@Query(value = "SELECT DISTINCT l.* FROM lop l "
			+ "INNER JOIN chude c ON l.chudeid=c.id "
			+ "WHERE l.quan LIKE %:quan% AND l.hinhthuc LIKE %:hinhthuc% AND "
			+ "l.hocphi >= :hocphi AND c.tenmonhoc LIKE %:mon% AND c.trinhdo LIKE %:trinhdo%", nativeQuery = true)
	public List<Lop> findByFilter(@Param("quan") String quan, @Param("hinhthuc") String hinhthuc,
			@Param("hocphi") Long hocphi, @Param("mon") String mon, @Param("trinhdo") String trinhdo);
	
	@Query(value = "SELECT DISTINCT l.* FROM lop l "
			+ "INNER JOIN chude c ON l.chudeid=c.id "
			+ "WHERE l.quan LIKE %:quan% AND l.hinhthuc LIKE %:hinhthuc% AND "
			+ "l.hocphi >= :hocphi AND c.tenmonhoc LIKE %:mon% AND c.trinhdo LIKE %:trinhdo%", nativeQuery = true)
	public Page<Lop> findByFilter(@Param("quan") String quan, @Param("hinhthuc") String hinhthuc,
			@Param("hocphi") Long hocphi, @Param("mon") String mon, @Param("trinhdo") String trinhdo, 
			Pageable pageable);
	
	public List<Lop> findByTrangthailop(TrangThaiLop trangthailop);
	
	public Page<Lop> findByTrangthailop(TrangThaiLop trangthailop, Pageable pageable);
	
}
