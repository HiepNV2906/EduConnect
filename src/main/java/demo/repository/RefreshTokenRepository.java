package demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import demo.entity.RefreshToken;
import demo.entity.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
	
	public Optional<RefreshToken> findByToken(String token);
	
	public Optional<RefreshToken> findByUser(User user);
	
	public int deleteByUser(User user);
}
