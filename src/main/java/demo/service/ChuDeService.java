package demo.service;

import java.util.List;

import demo.dto.ChuDeDTO;
import demo.entity.ChuDe;

public interface ChuDeService {
	public ChuDe addChuDe(ChuDeDTO chudeDTO);
	public ChuDe updateChuDe(ChuDeDTO chudeDTO);
	public void deleteChuDe(Long id);
	public ChuDe getChuDeById(Long id);
	public List<ChuDe> getListChuDe();
}
