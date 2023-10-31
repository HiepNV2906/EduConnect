package demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.Enum.TrangThaiCongNo;
import demo.Enum.TrangThaiUngTuyen;
import demo.dto.UngTuyenDTO;
import demo.entity.UngTuyen;

public interface UngTuyenService {
	public UngTuyen addUngTuyen(UngTuyenDTO ungtuyenDTO);
	public UngTuyen updateUngTuyen(UngTuyenDTO ungtuyenDTO);
	public void deleteUngTuyen(Long id);
	public UngTuyen getUngTuyenById(Long id);
	public List<UngTuyen> findByGiaSuId(Long giasuid);
	public Page<UngTuyen> findByGiaSuId(Long giasuid, Pageable pageable);
	public List<UngTuyen> findByLopId(Long lopid);
	public Page<UngTuyen> findByLopId(Long lopid, Pageable pageable);
	public List<UngTuyen> findByTrangThaiUngTuyen(TrangThaiUngTuyen trangThaiUngTuyen);
	public Page<UngTuyen> findByTrangThaiUngTuyen(TrangThaiUngTuyen trangThaiUngTuyen, Pageable pageable);
	public List<UngTuyen> findByTrangThaiCongNo(TrangThaiCongNo trangThaiCongNo);
	public Page<UngTuyen> findByTrangThaiCongNo(TrangThaiCongNo trangThaiCongNo, Pageable pageable);
	public UngTuyen updateTrangThaiUngTuyen(Long ungTuyenId, TrangThaiUngTuyen trangThaiUngTuyen);
	public UngTuyen updateTrangThaiCongNo(Long ungTuyenId, TrangThaiCongNo trangThaiCongNo);
	
	public List<UngTuyen> sapXepGiaSu(Long ungtuyenid);
}
