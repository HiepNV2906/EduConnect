package demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import demo.Enum.TrangThaiLop;
import demo.entity.HocVien;
import demo.entity.Lop;

@Repository
public interface LopRepository extends JpaRepository<Lop, Long>{
	@Query(value = "SELECT * FROM lop WHERE id:=key OR tieude LIKE %:key% OR diachi LIKE %:key% OR "
			+ "motahs LIKE %:key% OR yeucaukhac LIKE %:key% OR truonggs LIKE %:key% "
			+ "trangthailop=:trangthailop "
			+ "ORDER BY ngaytao DESC", nativeQuery = true)
	public List<Lop> findByKeyword(@Param("key") String keyword, @Param("trangthailop") String trangthailop);
	
	@Query(value = "SELECT * FROM lop WHERE id:=key OR tieude LIKE %:key% OR diachi LIKE %:key% OR "
			+ "motahs LIKE %:key% OR yeucaukhac LIKE %:key% OR truonggs LIKE %:key% "
			+ "trangthailop=:trangthailop "
			+ "ORDER BY ngaytao DESC", nativeQuery = true)
	public Page<Lop> findByKeyword(@Param("key") String keyword, @Param("trangthailop") String trangthailop, Pageable pageable);
	
	@Query(value = "SELECT DISTINCT l.* FROM lop l "
			+ "INNER JOIN chude c ON l.chudeid=c.id "
			+ "WHERE (l.id=:key OR l.tieude LIKE %:key% OR l.diachi LIKE %:key% OR "
			+ "l.motahs LIKE %:key% OR l.yeucaukhac LIKE %:key% OR l.truonggs LIKE %:key%) AND "
			+ "l.quan LIKE %:quan% AND l.hinhthuc LIKE %:hinhthuc% AND l.hocphi >= :hocphimin AND "
			+ "l.hocphi < :hocphimax AND c.tenmonhoc LIKE %:mon% AND c.trinhdo LIKE %:trinhdo% AND "
			+ "l.trangthailop=:trangthailop "
			+ "ORDER BY l.ngaytao DESC", nativeQuery = true)
	public List<Lop> findByFilter(@Param("key") String keyword, @Param("quan") String quan, @Param("hinhthuc") String hinhthuc,
			@Param("hocphimin") Long hocphimin, @Param("hocphimax") Long hocphimax, @Param("mon") String mon, 
			@Param("trinhdo") String trinhdo, @Param("trangthailop") String trangthailop);
	
	@Query(value = "SELECT DISTINCT l.* FROM lop l "
			+ "INNER JOIN chude c ON l.chudeid=c.id "
			+ "WHERE (l.id=:key OR l.tieude LIKE %:key% OR l.diachi LIKE %:key% OR "
			+ "l.motahs LIKE %:key% OR l.yeucaukhac LIKE %:key% OR l.truonggs LIKE %:key%) AND "
			+ "l.quan LIKE %:quan% AND l.hinhthuc LIKE %:hinhthuc% AND l.hocphi >= :hocphimin AND "
			+ "l.hocphi < :hocphimax AND c.tenmonhoc LIKE %:mon% AND c.trinhdo LIKE %:trinhdo% AND "
			+ "l.trangthailop=:trangthailop "
			+ "ORDER BY l.ngaytao DESC", nativeQuery = true)
	public Page<Lop> findByFilter(@Param("key") String keyword, @Param("quan") String quan, @Param("hinhthuc") String hinhthuc,
			@Param("hocphimin") Long hocphimin, @Param("hocphimax") Long hocphimax, @Param("mon") String mon, 
			@Param("trinhdo") String trinhdo, @Param("trangthailop") String trangthailop, Pageable pageable);
	
	public List<Lop> findByTrangthailopOrderByNgaytaoDesc(TrangThaiLop trangthailop);
	public Page<Lop> findByTrangthailopOrderByNgaytaoDesc(TrangThaiLop trangthailop, Pageable pageable);
	public List<Lop> findByTrangthailopOrderByHanungtuyenAsc(TrangThaiLop trangthailop);
	
	public List<Lop> findByHocvienAndTrangthailopOrderByNgaytaoDesc(HocVien hocvien, TrangThaiLop trangthailop);
	
	public List<Lop> findAllByOrderByNgaytaoDesc();
	public Page<Lop> findAllByOrderByNgaytaoDesc(Pageable pageable);
	
	public List<Lop> findTop5ByOrderByNgaytaoDesc();
}
