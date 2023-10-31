package demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.Enum.TrangThaiLop;
import demo.dto.LopDTO;
import demo.entity.Lop;

public interface LopService {
	public Lop addLop(LopDTO lopDTO);
	public Lop updateLop(LopDTO lopDTO);
	public void deleteLop(Long id);
	public Lop getLopById(Long id);
	public List<Lop> getListLop();
	public Page<Lop> getListLop(Pageable pageable);
	public List<Lop> findByKey(String key);
	public Page<Lop> findBykey(String key, Pageable pageable);
	public List<Lop> findByFilter(String quan, String hinhthuc, Long hocphi,
			String mon, String trinhdo);
	public Page<Lop> findByFilter(String quan, String hinhthuc, Long hocphi,
			String mon, String trinhdo, Pageable pageable);
	public List<Lop> findByTrangThai(TrangThaiLop trangThaiLop);
	public Page<Lop> findByTrangThai(TrangThaiLop trangThaiLop, Pageable pageable);
	public Lop updateTrangThaiLop(Long lopId, TrangThaiLop trangThaiLop);
}
