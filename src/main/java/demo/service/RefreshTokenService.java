package demo.service;

import demo.entity.RefreshToken;

public interface RefreshTokenService {
	public RefreshToken findByToken(String token);
	public RefreshToken createRefreshToken(Long userId);
	public RefreshToken verifyExpiration(RefreshToken token);
	public int deleteByUserId(Long userId);
}
