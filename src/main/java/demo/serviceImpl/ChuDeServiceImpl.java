package demo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.dto.ChuDeDTO;
import demo.entity.ChuDe;
import demo.exception.ChuDeException;
import demo.mapper.ChuDeMapper;
import demo.repository.ChuDeRepository;
import demo.service.ChuDeService;

@Service
public class ChuDeServiceImpl implements ChuDeService{
	@Autowired
	ChuDeRepository chuDeRepository;
	
	@Override
	public ChuDe addChuDe(ChuDeDTO chudeDTO) {
		if(chuDeRepository.findByTenmonhocAndTrinhdo(chudeDTO.getTenmonhoc(), chudeDTO.getTrinhdo()).isPresent()) {
			throw new ChuDeException("Chủ đề đã tồn tại!");
		}
		ChuDe c = ChuDeMapper.toEntity(chudeDTO);
		c.setId(null);
		ChuDe chuDe = chuDeRepository.save(c);
		return chuDe;
	}

	@Override
	public ChuDe updateChuDe(ChuDeDTO chudeDTO) {
		if(chuDeRepository.findByTenmonhocAndTrinhdo(chudeDTO.getTenmonhoc(), chudeDTO.getTrinhdo()).isPresent()) {
			throw new ChuDeException("Chủ đề đã tồn tại!");
		}
		ChuDe c = ChuDeMapper.toEntity(chudeDTO);
		ChuDe chuDe = chuDeRepository.save(c);
		return chuDe;
	}

	@Override
	public void deleteChuDe(Long id) {
		chuDeRepository.deleteById(id);
	}

	@Override
	public ChuDe getChuDeById(Long id) {
		ChuDe c = chuDeRepository.findById(id).get();
		return c;
	}

	@Override
	public List<ChuDe> getListChuDe() {
		List<ChuDe> list = chuDeRepository.findAll();
		return list;
	}

}
