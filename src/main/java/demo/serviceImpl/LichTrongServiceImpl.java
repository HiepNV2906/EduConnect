package demo.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.dto.LichTrongDTO;
import demo.entity.LichTrong;
import demo.entity.Lop;
import demo.entity.composite.LopLichTrongId;
import demo.mapper.LichTrongMapper;
import demo.repository.LichTrongRepository;
import demo.service.LichTrongService;

@Service
public class LichTrongServiceImpl implements LichTrongService{

	@Autowired
	LichTrongRepository lichTrongRepository;
	
	@Override
	public LichTrong addLichTrong(Lop lop, LichTrongDTO lichtrongDTO) {
		LichTrong l = LichTrongMapper.toEntity(lichtrongDTO);
		l.setLop(lop);
		LichTrong lichTrong = lichTrongRepository.save(l);
		return lichTrong;
	}

	@Override
	public LichTrong updateLichTrong(LichTrongDTO lichtrongDTO) {
		LichTrong l = LichTrongMapper.toEntity(lichtrongDTO);
		LichTrong lichTrong = lichTrongRepository.save(l);
		return lichTrong;
	}

	@Override
	public void deleteLichTrong(LopLichTrongId lichTrongId) {
		lichTrongRepository.deleteById(lichTrongId);
	}

	@Override
	public LichTrong getLichTrongById(LopLichTrongId lichTrongId) {
		LichTrong l = lichTrongRepository.findById(lichTrongId).get();
		return l;
	}

	@Override
	public List<LichTrong> getLichTrongByLopId(Long lopid) {
		List<LichTrong> list = lichTrongRepository.findAll();
		return list;
	}

	@Override
	public List<LichTrong> addListLichTrong(Lop lop, List<LichTrongDTO> listDTO) {
		List<LichTrong> list = new ArrayList<>(); 
		for (LichTrongDTO lichTrongDTO : listDTO) {
			list.add(addLichTrong(lop, lichTrongDTO));
		}
		return list;
	}

}
