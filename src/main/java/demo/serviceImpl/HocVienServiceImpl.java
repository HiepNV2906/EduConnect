package demo.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import demo.Enum.TrangThaiUser;
import demo.Enum.VaiTro;
import demo.entity.HocVien;
import demo.entity.User;
import demo.exception.StorageException;
import demo.exception.UserException;
import demo.mapper.HocVienMapper;
import demo.mapper.ThongBaoModel;
import demo.repository.HocVienRepository;
import demo.repository.ThongBaoRepository;
import demo.repository.UserRepository;
import demo.request.RegisterHocVienRequest;
import demo.service.HocVienService;
import demo.service.StorageService;

@Service
public class HocVienServiceImpl implements HocVienService{
	
	@Autowired
	HocVienRepository hocVienRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ThongBaoRepository thongBaoRepository;
	@Autowired
	StorageService storageService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//	Tạo tài khoản học viên
	@Override
	public HocVien addHocVien(RegisterHocVienRequest registerHocVienRequest) throws StorageException {
		if(userRepository.findByEmail(registerHocVienRequest.getEmail()).isPresent()) {
			throw new UserException("Email đã tồn tại!!!");
		}
		HocVien h = registerHocVienRequest.toHocVienEntity();
		h.setId(null);
		h.setNgaytao(new Date());
		h.setVaitro(VaiTro.HOCVIEN);
		h.setPassword(bCryptPasswordEncoder.encode(registerHocVienRequest.getPassword()));
		h.setTrangthai(TrangThaiUser.CHUAPHEDUYET);
		
		if(registerHocVienRequest.getAvata()!=null && registerHocVienRequest.getCccd()!=null) {
			UUID uuid1=UUID.randomUUID();
			String uuString1=uuid1.toString();
			UUID uuid2=UUID.randomUUID();
			String uuString2=uuid2.toString();
			h.setAvata(storageService.getStoredFilename(registerHocVienRequest.getAvata(), uuString1));
			storageService.store(registerHocVienRequest.getAvata(), h.getAvata());
			h.setCccd(storageService.getStoredFilename(registerHocVienRequest.getCccd(), uuString2));
			storageService.store(registerHocVienRequest.getCccd(), h.getCccd());
		}
		else {
			throw new StorageException("File trống");
		}
		
		HocVien hocvien = hocVienRepository.save(h);
		
		//		Thông Báo
		List<User> admin = userRepository.findByVaitro(VaiTro.ADMIN);
		for (User user : admin) {
			thongBaoRepository.save(ThongBaoModel.ycDangKyTaiKhoanHocVien(user));
		}
		thongBaoRepository.save(ThongBaoModel.dangKyThanhCong(hocvien));
		return hocvien;
	}

	@Override
	public HocVien updateHocVien(RegisterHocVienRequest registerHocVienRequest) throws StorageException {
		if(registerHocVienRequest.getEmail()!=null && userRepository.findByEmail(registerHocVienRequest.getEmail()).isPresent()) {
			throw new UserException("Email đã tồn tại!!!");
		}
		HocVien h = HocVienMapper.update(getHocVienById(registerHocVienRequest.getId()), registerHocVienRequest);
		
		if(registerHocVienRequest.getAvata()!=null) {
			h.setAvata(storageService.getStoredFilename(registerHocVienRequest.getAvata(), h.getAvata()));
			storageService.store(registerHocVienRequest.getAvata(), h.getAvata());
		}
		
		if(registerHocVienRequest.getCccd()!=null) {
			h.setCccd(storageService.getStoredFilename(registerHocVienRequest.getCccd(), h.getCccd()));
			storageService.store(registerHocVienRequest.getCccd(), h.getCccd());
		}
		
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
