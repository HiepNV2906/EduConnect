package demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import demo.Enum.TrangThaiCongNo;
import demo.Enum.TrangThaiUngTuyen;
import demo.entity.UngTuyen;

@Repository
public interface UngTuyenRepository extends JpaRepository<UngTuyen, Long>{
	
	@Query(value = "SELECT * FROM ungtuyen WHERE giasuid = ?1", nativeQuery = true)
	public List<UngTuyen> findByGiaSuId(Long giasuid);
	
	@Query(value = "SELECT * FROM ungtuyen WHERE giasuid = ?1", nativeQuery = true)
	public Page<UngTuyen> findByGiaSuId(Long giasuid, Pageable pageable);
	
	@Query(value = "SELECT * FROM ungtuyen WHERE lopid = ?1", nativeQuery = true)
	public List<UngTuyen> findByLopId(Long lopid);
	
	@Query(value = "SELECT * FROM ungtuyen WHERE lopid = ?1", nativeQuery = true)
	public Page<UngTuyen> findByLopId(Long lopid, Pageable pageable);
	
	@Query(value = "SELECT COUNT(*) FROM ungtuyen WHERE lopid = ?1", nativeQuery = true)
	public Integer countByLopId(Long lopid);
	
	@Query(value = "SELECT * FROM ungtuyen WHERE giasuid = ?1 AND lopid = ?2", nativeQuery = true)
	public Optional<UngTuyen> findByGiaSuIdAndLopId(Long giasuid, Long lopid);
	
	public List<UngTuyen> findByTrangthaiungtuyen(TrangThaiUngTuyen trangThaiUngTuyen);
	
	public Page<UngTuyen> findByTrangthaiungtuyen(TrangThaiUngTuyen trangThaiUngTuyen, Pageable pageable);
	
	public List<UngTuyen> findByTrangthaicongno(TrangThaiCongNo trangThaiCongNo);
	
	public Page<UngTuyen> findByTrangthaicongno(TrangThaiCongNo trangThaiCongNo, Pageable pageable);
}
