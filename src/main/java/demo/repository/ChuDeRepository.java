package demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.entity.ChuDe;

@Repository
public interface ChuDeRepository extends JpaRepository<ChuDe, Long>{
	public Optional<ChuDe> findByTenmonhocAndTrinhdo(String tenmonhoc, String trinhdo);
}
