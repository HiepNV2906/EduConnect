package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import demo.entity.ThanhToan;

@Repository
public interface ThanhToanRepository extends JpaRepository<ThanhToan, String>{
	
	@Query(value = "SELECT * FROM thanhtoan WHERE ungtuyenid = ?1", nativeQuery = true)
	public ThanhToan findByUngTuyenId(Long id);
}
