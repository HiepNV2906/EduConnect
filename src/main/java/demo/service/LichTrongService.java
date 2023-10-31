package demo.service;

import java.util.List;

import demo.dto.LichTrongDTO;
import demo.entity.LichTrong;
import demo.entity.Lop;
import demo.entity.composite.LopLichTrongId;

public interface LichTrongService {
	public LichTrong addLichTrong(Lop lop, LichTrongDTO lichtrongDTO);
	public List<LichTrong> addListLichTrong(Lop lop, List<LichTrongDTO> list);
	public LichTrong updateLichTrong(LichTrongDTO lichtrongDTO);
	public void deleteLichTrong(LopLichTrongId lichTrongId);
	public LichTrong getLichTrongById(LopLichTrongId lichTrongId);
	public List<LichTrong> getLichTrongByLopId(Long lopidDTO);
}
