package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.entity.ChuDe;

@Repository
public interface ChuDeRepository extends JpaRepository<ChuDe, Long>{

}
