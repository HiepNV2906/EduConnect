package demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.Enum.TrangThaiLoiMoi;
import demo.dto.LoiMoiDTO;
import demo.entity.LoiMoi;

public interface LoiMoiService {
	public LoiMoi addLoiMoi(LoiMoiDTO loiMoiDTO);
	public LoiMoi updateLoiMoi(LoiMoiDTO loiMoiDTO);
	public void deleteLoiMoi(Long id);
	public LoiMoi getLoiMoiById(Long id);
	public List<LoiMoi> findByGiaSuId(Long giasuid);
	public Page<LoiMoi> findByGiaSuId(Long giasuid, Pageable pageable);
	public List<LoiMoi> findByLopId(Long lopid);
	public Page<LoiMoi> findByLopId(Long lopid, Pageable pageable);
	public List<LoiMoi> findByHocVienId(Long hocvienid);
	public LoiMoi findByGiaSuIdAndLopId(Long giasuid, Long lopid);
	public List<LoiMoi> findByTrangThaiLoiMoi(TrangThaiLoiMoi trangThaiLoiMoi);
	public Page<LoiMoi> findByTrangThaiLoiMoi(TrangThaiLoiMoi trangThaiLoiMoi, Pageable pageable);
	public LoiMoi updateTrangThaiLoiMoi(Long loiMoiId, TrangThaiLoiMoi trangThaiLoiMoi);
}
