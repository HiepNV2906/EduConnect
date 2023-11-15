package demo.serviceImpl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import demo.entity.RefreshToken;
import demo.entity.User;
import demo.exception.RefreshTokenException;
import demo.repository.RefreshTokenRepository;
import demo.repository.UserRepository;
import demo.service.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{
	@Value("${jwt.refresh.expiration}")
	private Long refreshTokenDurationMs;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	public RefreshToken findByToken(String token) {
		Optional<RefreshToken> optional =  refreshTokenRepository.findByToken(token);
		if(optional.isPresent())
			return optional.get();
		throw new RefreshTokenException("Refresh token không tồn tại");
	}
	
	public RefreshToken createRefreshToken(Long userId) {
		User user = userRepository.findById(userId).get();
		Optional<RefreshToken> optional = refreshTokenRepository.findByUser(user);
		RefreshToken refreshToken;
		if(optional.isPresent()) {
			refreshToken = optional.get();
		} else {
			refreshToken = new RefreshToken();
			refreshToken.setUser(userRepository.findById(userId).get());
		}
		refreshToken.setExpirydate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());
		
		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}
	
	public RefreshToken verifyExpiration(RefreshToken token) {
		if(token.getExpirydate().compareTo(Instant.now()) < 0) {//Giá trị âm nếu đối tượng được so sánh nhỏ hơn đối tượng được cung cấp.
			refreshTokenRepository.delete(token);
			throw new RefreshTokenException("Refresh token đã hết hiệu lực. Vui lòng đăng nhập lại");
		}
		return token;
	}
	
	public int deleteByUserId(Long userId) {
		return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
	}

}
