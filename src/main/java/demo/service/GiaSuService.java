package demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.Enum.TrangThaiUser;
import demo.entity.GiaSu;
import demo.exception.StorageException;
import demo.request.RegisterGiaSuRequest;

public interface GiaSuService {
	public GiaSu addGiaSu(RegisterGiaSuRequest registerGiaSuRequest) throws StorageException;
	public GiaSu updateGiaSu(RegisterGiaSuRequest registerGiaSuRequest) throws StorageException;
	public void deleteGiaSu(Long id);
	public GiaSu getGiaSuById(Long id);
	public Page<GiaSu> getListGiaSu(Pageable pageable);
	public List<GiaSu> getListGiaSu();
	public Page<GiaSu> findByKey(String key, Pageable pageable);
	public List<GiaSu> findByKey(String key);
	public Page<GiaSu> findByFilter(String quan, String nghenghiep,
			String gioitinh, String mon, String trinhdo, Pageable pageable);
	public List<GiaSu> findByFilter(String quan, String nghenghiep,
			String gioitinh, String mon, String trinhdo);
	public Page<GiaSu> findByTrangThai(TrangThaiUser trangThaiUser, Pageable pageable);
	public List<GiaSu> findByTrangThai(TrangThaiUser trangThaiUser);
	public GiaSu updateTrangThai(Long giasuId, TrangThaiUser trangThaiUser);
	public List<GiaSu> findTop10New();
}
