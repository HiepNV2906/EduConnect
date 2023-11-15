package demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import demo.Enum.TrangThaiLoiMoi;
import demo.entity.LoiMoi;

@Repository
public interface LoiMoiRepository extends JpaRepository<LoiMoi, Long>{
	@Query(value = "SELECT * FROM loimoi WHERE giasuid = ?1", nativeQuery = true)
	public List<LoiMoi> findByGiaSuId(Long giasuid);
	
	@Query(value = "SELECT * FROM loimoi WHERE giasuid = ?1", nativeQuery = true)
	public Page<LoiMoi> findByGiaSuId(Long giasuid, Pageable pageable);
	
	@Query(value = "SELECT * FROM loimoi WHERE lopid = ?1", nativeQuery = true)
	public List<LoiMoi> findByLopId(Long lopid);
	
	@Query(value = "SELECT * FROM loimoi WHERE lopid = ?1", nativeQuery = true)
	public Page<LoiMoi> findByLopId(Long lopid, Pageable pageable);
	
	@Query(value = "SELECT * FROM loimoi WHERE giasuid = ?1 AND lopid = ?2", nativeQuery = true)
	public Optional<LoiMoi> findByGiaSuIdAndLopId(Long giasuid, Long lopid);
	
	@Query(value = "SELECT DISTINCT m.* FROM loimoi m "
			+ "INNER JOIN lop l ON m.lopid=l.id WHERE l.hocvienid = ?1", nativeQuery = true)
	public List<LoiMoi> findByHocVienId(Long hocvienid);
	
	public List<LoiMoi> findByTrangthailoimoi(TrangThaiLoiMoi trangThaiLoiMoi);
	
	public Page<LoiMoi> findByTrangthailoimoi(TrangThaiLoiMoi trangThaiLoiMoi, Pageable pageable);
}
