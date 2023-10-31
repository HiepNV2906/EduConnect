package demo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.Enum.TrangThaiUser;
import demo.Enum.VaiTro;
import demo.dto.HocVienDTO;
import demo.entity.HocVien;
import demo.entity.User;
import demo.exception.UserException;
import demo.mapper.HocVienMapper;
import demo.mapper.ThongBaoModel;
import demo.repository.HocVienRepository;
import demo.repository.ThongBaoRepository;
import demo.repository.UserRepository;
import demo.request.RegisterHocVienRequest;
import demo.service.HocVienService;

@Service
public class HocVienServiceImpl implements HocVienService{
	
	@Autowired
	HocVienRepository hocVienRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ThongBaoRepository thongBaoRepository;
	
	//	Tạo tài khoản học viên
	@Override
	public HocVien addHocVien(RegisterHocVienRequest registerHocVienRequest) {
		if(userRepository.findByEmail(registerHocVienRequest.getEmail()).isPresent()) {
			throw new UserException("Email đã tồn tại!!!");
		}
		HocVien h = registerHocVienRequest.toHocVienEntity();
		h.setId(null);
		h.setVaitro(VaiTro.HOCVIEN);
		h.setTrangthai(TrangThaiUser.CHUAPHEDUYET);
		HocVien hocvien = hocVienRepository.save(h);
		
		//		Thông Báo
		List<User> admin = userRepository.findByVaitro(VaiTro.ADMIN);
		for (User user : admin) {
			thongBaoRepository.save(ThongBaoModel.ycDangKyTaiKhoan(user));
		}
		thongBaoRepository.save(ThongBaoModel.dangKyThanhCong(hocvien));
		return hocvien;
	}

	@Override
	public HocVien updateHocVien(HocVienDTO hocvienDTO) {
		HocVien h = HocVienMapper.update(getHocVienById(hocvienDTO.getId()), hocvienDTO);
		HocVien hocvien = hocVienRepository.save(h);
		
		//	Thông báo	
		thongBaoRepository.save(ThongBaoModel.capNhatTaiKhoan(hocvien));
		return hocvien;
	}

	@Override
	public void deleteHocVien(Long id) {
		hocVienRepository.deleteById(id);
	}

	@Override
	public HocVien getHocVienById(Long id) {
		HocVien h = hocVienRepository.findById(id).get();
		return h;
	}

	@Override
	public Page<HocVien> getListHocVien(Pageable pageable) {
		Page<HocVien> page = hocVienRepository.findAll(pageable);
		return page;
	}

	@Override
	public List<HocVien> getListHocVien() {
		List<HocVien> list = hocVienRepository.findAll();
		return list;
	}

	@Override
	public Page<HocVien> findByKey(String key, Pageable pageable) {
		Page<HocVien> page = hocVienRepository.findByKeyword(key, pageable);
		return page;
	}

	@Override
	public List<HocVien> findByKey(String key) {
		List<HocVien> list = hocVienRepository.findByKeyword(key);
		return list;
	}

	@Override
	public Page<HocVien> findByTrangThai(TrangThaiUser trangThaiUser, Pageable pageable) {
		Page<HocVien> page = hocVienRepository.findByTrangthai(trangThaiUser, pageable);
		return page;
	}

	@Override
	public List<HocVien> findByTrangThai(TrangThaiUser trangThaiUser) {
		List<HocVien> list = hocVienRepository.findByTrangthai(trangThaiUser);
		return list;
	}

	@Override
	public HocVien updateHocVien(Long hocvienId, TrangThaiUser trangThaiUser) {
		HocVien h = getHocVienById(hocvienId);
		h.setTrangthai(trangThaiUser);
		HocVien hocvien = hocVienRepository.save(h);
		
		//Thông báo
		if(hocvien.getTrangthai() == TrangThaiUser.DAPHEDUYET) {
			thongBaoRepository.save(ThongBaoModel.pheDuyetTaiKhoan(hocvien));
		}
		if(hocvien.getTrangthai() == TrangThaiUser.DINHCHI) {
			thongBaoRepository.save(ThongBaoModel.dinhChiTaiKhoan(hocvien));
		}
		return hocvien;
	}

}
