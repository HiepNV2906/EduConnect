package demo.serviceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.Enum.TrangThaiCongNo;
import demo.Enum.VaiTro;
import demo.dto.ThanhToanDTO;
import demo.entity.ThanhToan;
import demo.entity.UngTuyen;
import demo.entity.User;
import demo.mapper.ThanhToanMapper;
import demo.mapper.ThongBaoModel;
import demo.repository.ThanhToanRepository;
import demo.repository.ThongBaoRepository;
import demo.repository.UngTuyenRepository;
import demo.repository.UserRepository;
import demo.service.ThanhToanService;

@Service
public class ThanhToanServiceImpl implements ThanhToanService{

	@Autowired
	ThanhToanRepository thanhToanRepository;
	@Autowired
	UngTuyenRepository ungTuyenRepository;
	@Autowired
	ThongBaoRepository thongBaoRepository;
	@Autowired
	UserRepository userRepository;
	
	@Override
	public ThanhToan addThanhToan(ThanhToanDTO thanhToanDTO) {
		ThanhToan t = ThanhToanMapper.toEntity(thanhToanDTO);
		UngTuyen u = ungTuyenRepository.findById(thanhToanDTO.getUngtuyenid()).get();
		u.setTrangthaicongno(TrangThaiCongNo.DATHANHTOAN);
		t.setUngtuyen(u);
		ThanhToan thanhToan = thanhToanRepository.save(t);
		
//		Thông báo
		List<User> users = userRepository.findByVaitro(VaiTro.ADMIN);
		for (User user : users) {
			thongBaoRepository.save(ThongBaoModel.lichSuThanhToan(user));
		}
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

	@Override
	public List<ThanhToan> getListThanhToanByGiaSuId(Long giasuid) {
		List<ThanhToan> list = thanhToanRepository.findByGiaSuId(giasuid);
		return list;
	}

	@Override
	public List<ThanhToan> getThanhToanFromTo(String from, String to) throws ParseException{
		String pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		List<ThanhToan> list = thanhToanRepository.findByNgayThanhToan(sdf.parse(from), sdf.parse(to));
		System.out.println(list.size());
		return list;
	}

}
