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
	public List<Lop> findByKey(String key, TrangThaiLop trangThaiLop);
	public Page<Lop> findBykey(String key, TrangThaiLop trangThaiLop, Pageable pageable);
	public List<Lop> findByFilter(String key, String quan, String hinhthuc, Long hocphimin,
			Long hocphimax, String mon, String trinhdo);
	public Page<Lop> findByFilter(String key, String quan, String hinhthuc, Long hocphimin,
			Long hocphimax, String mon, String trinhdo, Pageable pageable);
	public List<Lop> findByTrangThai(TrangThaiLop trangThaiLop);
	public Page<Lop> findByTrangThai(TrangThaiLop trangThaiLop, Pageable pageable);
	public Lop updateTrangThaiLop(Long lopId, TrangThaiLop trangThaiLop);
	public List<Lop> getTopNewListLop();
}
