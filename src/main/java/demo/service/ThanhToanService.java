package demo.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.dto.ThanhToanDTO;
import demo.entity.ThanhToan;

public interface ThanhToanService {
	public ThanhToan addThanhToan(ThanhToanDTO thanhToanDTO);
	public ThanhToan updateThanhToan(ThanhToanDTO thanhToanDTO);
	public void deleteThanhToan(String id);
	public ThanhToan getThanhToanById(String id);
	public List<ThanhToan> getListThanhToan();
	public Page<ThanhToan> getListThanhToan(Pageable pageable);
	public ThanhToan getListThanhToanByUngTuyenId(Long id);
	public List<ThanhToan> getListThanhToanByGiaSuId(Long giasuid);
	public List<ThanhToan> getThanhToanFromTo(String from, String to) throws ParseException;
}
