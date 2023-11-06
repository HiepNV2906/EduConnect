package demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.Enum.TrangThaiUser;
import demo.entity.HocVien;
import demo.exception.StorageException;
import demo.request.RegisterHocVienRequest;

public interface HocVienService {
	public HocVien addHocVien(RegisterHocVienRequest registerHocVienRequest) throws StorageException;
	public HocVien updateHocVien(RegisterHocVienRequest registerHocVienRequest) throws StorageException;
	public void deleteHocVien(Long id);
	public HocVien getHocVienById(Long id);
	public Page<HocVien> getListHocVien(Pageable pageable);
	public List<HocVien> getListHocVien();
	public Page<HocVien> findByKey(String key, Pageable pageable);
	public List<HocVien> findByKey(String key);
	public Page<HocVien> findByTrangThai(TrangThaiUser trangThaiUser, Pageable pageable);
	public List<HocVien> findByTrangThai(TrangThaiUser trangThaiUser);
	public HocVien updateHocVien(Long hocvienId, TrangThaiUser trangThaiUser);
}
