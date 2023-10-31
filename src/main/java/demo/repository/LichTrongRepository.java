package demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import demo.entity.LichTrong;
import demo.entity.composite.LopLichTrongId;

@Repository
public interface LichTrongRepository extends JpaRepository<LichTrong, LopLichTrongId>{
	
	@Query(value = "SELECT * FROM lichtrong WHERE lopid=?1", nativeQuery = true)
	public List<LichTrong> findByLopId(Long lopid);
}
