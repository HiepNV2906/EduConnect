package demo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.dto.ThanhToanDTO;
import demo.entity.ThanhToan;
import demo.mapper.ThanhToanMapper;
import demo.mapper.ThongBaoModel;
import demo.repository.ThanhToanRepository;
import demo.repository.ThongBaoRepository;
import demo.repository.UngTuyenRepository;
import demo.service.ThanhToanService;

@Service
public class ThanhToanServiceImpl implements ThanhToanService{

	@Autowired
	ThanhToanRepository thanhToanRepository;
	@Autowired
	UngTuyenRepository ungTuyenRepository;
	@Autowired
	ThongBaoRepository thongBaoRepository;
	
	@Override
	public ThanhToan addThanhToan(ThanhToanDTO thanhToanDTO) {
		ThanhToan t = ThanhToanMapper.toEntity(thanhToanDTO);
		t.setUngtuyen(ungTuyenRepository.findById(thanhToanDTO.getUngtuyenid()).get());
		ThanhToan thanhToan = thanhToanRepository.save(t);
		
//		Thông báo
		thongBaoRepository.save(ThongBaoModel.lichSuThanhToan(thanhToan.getUngtuyen().getGiasu()));
		return thanhToan;
	}

	@Override
	public ThanhToan updateThanhToan(ThanhToanDTO thanhToanDTO) {
		ThanhToan t = ThanhToanMapper.toEntity(thanhToanDTO);
		t.setUngtuyen(ungTuyenRepository.findById(thanhToanDTO.getUngtuyenid()).get());
		ThanhToan thanhToan = thanhToanRepository.save(t);
		return thanhToan;
	}

	@Override
	public void deleteThanhToan(String id) {
		thanhToanRepository.deleteById(id);
	}

	@Override
	public ThanhToan getThanhToanById(String id) {
		ThanhToan t = thanhToanRepository.findById(id).get();
		return t;
	}

	@Override
	public List<ThanhToan> getListThanhToan() {
		List<ThanhToan> list = thanhToanRepository.findAll();
		return list;
	}

	@Override
	public Page<ThanhToan> getListThanhToan(Pageable pageable) {
		Page<ThanhToan> page = thanhToanRepository.findAll(pageable);
		return page;
	}

	@Override
	public ThanhToan getListThanhToanByUngTuyenId(Long id) {
		ThanhToan t = thanhToanRepository.findByUngTuyenId(id);
		return t;
	}

}
