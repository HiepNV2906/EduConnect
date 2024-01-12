package demo.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.Enum.TrangThaiLop;
import demo.Enum.TrangThaiUngTuyen;
import demo.Enum.TrangThaiUser;
import demo.Enum.VaiTro;
import demo.dto.LichTrongDTO;
import demo.dto.LopDTO;
import demo.entity.HocVien;
import demo.entity.Lop;
import demo.entity.User;
import demo.exception.UserException;
import demo.mapper.LopMapper;
import demo.mapper.ThongBaoModel;
import demo.repository.ChuDeRepository;
import demo.repository.HocVienRepository;
import demo.repository.LopRepository;
import demo.repository.ThongBaoRepository;
import demo.repository.UserRepository;
import demo.service.LichTrongService;
import demo.service.LopService;

@Service
public class LopServiceImpl implements LopService{

	@Autowired
	LopRepository lopRepository;
	@Autowired
	HocVienRepository hocVienRepository;
	@Autowired
	ChuDeRepository chuDeRepository;
	@Autowired
	LichTrongService lichTrongService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ThongBaoRepository thongBaoRepository;

	//	Tạo lớp gia sư
	@Override
	public Lop addLop(LopDTO lopDTO) {
		Lop l = LopMapper.toEntity(lopDTO);
		l.setId(null);
		l.setNgaytao(new Date());
		l.setTrangthailop(TrangThaiLop.CHOPHEDUYET);
		HocVien hocvien = hocVienRepository.findById(lopDTO.getHocvienid()).get();
		if(hocvien.getTrangthai()!=TrangThaiUser.DAPHEDUYET) {
			throw new UserException("Tài khoản học viên chưa được phê duyệt");
		}
		l.setHocvien(hocvien);
		l.setChude(chuDeRepository.findById(lopDTO.getChude().getId()).get());
		Lop lop = lopRepository.save(l);
		for (LichTrongDTO i : lopDTO.getDslichtrong()) {
			i.setLopid(lop.getId());
		}
		lop.setDslichtrong(lichTrongService.addListLichTrong(lop, lopDTO.getDslichtrong()));
		
		//		Thông báo
		thongBaoRepository.save(ThongBaoModel.taoLopThanhCong(hocvien));
		List<User> admin = userRepository.findByVaitro(VaiTro.ADMIN);
		for (User user : admin) {
			thongBaoRepository.save(ThongBaoModel.ycTaoLop(user));
		}
		
		return lop;
	}

	@Override
	public Lop updateLop(LopDTO lopDTO) {
		Lop l = LopMapper.update(getLopById(lopDTO.getId()), lopDTO);
		l.setChude(chuDeRepository.findById(lopDTO.getChude().getId()).get());
		Lop lop = lopRepository.save(l);
		for (LichTrongDTO i : lopDTO.getDslichtrong()) {
			i.setLopid(lop.getId());
		}
		lop.setDslichtrong(lichTrongService.addListLichTrong(lop, lopDTO.getDslichtrong()));
		thongBaoRepository.save(ThongBaoModel.capNhatLop(l.getHocvien()));
		return lop;
	}

	@Override
	public void deleteLop(Long id) {
		lopRepository.deleteById(id);
	}

	@Override
	public Lop getLopById(Long id) {
		Lop l = lopRepository.findById(id).get();
		return l;
	}

	@Override
	public List<Lop> getListLop() {
		List<Lop> list = lopRepository.findAllByOrderByNgaytaoDesc();
		return list;
	}

	@Override
	public Page<Lop> getListLop(Pageable pageable) {
		Page<Lop> page = lopRepository.findAllByOrderByNgaytaoDesc(pageable);
		return page;
	}

	@Override
	public List<Lop> findByKey(String key, TrangThaiLop trangThaiLop) {
		List<Lop> list = lopRepository.findByKeyword(key, trangThaiLop.toString());
		return list;
	}

	@Override
	public Page<Lop> findBykey(String key, TrangThaiLop trangThaiLop, Pageable pageable) {
		Page<Lop> page = lopRepository.findByKeyword(key, trangThaiLop.toString(), pageable);
		return page;
	}

	@Override
	public List<Lop> findByFilter(String key, String quan, String hinhthuc, Long hocphimin, 
			Long hocphimax, String mon, String trinhdo) {
		List<Lop> list = lopRepository.findByFilter(key, quan, hinhthuc, hocphimin, hocphimax, 
				mon, trinhdo, TrangThaiLop.DANGTIM.toString());
		return list;
	}

	@Override
	public Page<Lop> findByFilter(String key, String quan, String hinhthuc, Long hocphimin, 
			Long hocphimax, String mon, String trinhdo, Pageable pageable) {
		Page<Lop> page = lopRepository.findByFilter(key, quan, hinhthuc, hocphimin, hocphimax, 
				mon, trinhdo, TrangThaiLop.DANGTIM.toString(), pageable);
		return page;
	}

	@Override
	public List<Lop> findByTrangThai(TrangThaiLop trangThaiLop) {
		List<Lop> list = lopRepository.findByTrangthailopOrderByNgaytaoDesc(trangThaiLop);
		return list;
	}

	@Override
	public Page<Lop> findByTrangThai(TrangThaiLop trangThaiLop, Pageable pageable) {
		Page<Lop> page = lopRepository.findByTrangthailopOrderByNgaytaoDesc(trangThaiLop, pageable);
		return page;
	}

	@Override
	public Lop updateTrangThaiLop(Long lopId, TrangThaiLop trangThaiLop) {
		Lop l = getLopById(lopId);
		l.setTrangthailop(trangThaiLop);
		Lop lop = lopRepository.save(l);
		
//		Thông báo
		if(lop.getTrangthailop() == TrangThaiLop.DANGTIM) {
			thongBaoRepository.save(ThongBaoModel.pheDuyetLop(lop.getHocvien()));
		}
		
		if(lop.getTrangthailop() == TrangThaiLop.CANGIAO) {
			List<User> users = userRepository.findByVaitro(VaiTro.ADMIN);
			for (User user : users) {
				thongBaoRepository.save(ThongBaoModel.sapXepGiaSu(user, false));
			}
		}
		
		if(lop.getTrangthailop() == TrangThaiLop.DANGTIM) {
			thongBaoRepository.save(ThongBaoModel.chonDuocGiaSu(lop.getHocvien()));
		}
		return lop;
	}

	@Override
	public List<Lop> getTopNewListLop() {
		List<Lop> list = lopRepository.findTop5ByTrangthailopOrderByNgaytaoDesc(TrangThaiLop.DANGTIM);
		return list;
	}

	@Override
	public List<Lop> findByHocVienAndTrangThai(Long hocvienid, TrangThaiLop trangThaiLop) {
		List<Lop> list = lopRepository.findByHocvienAndTrangthailopOrderByNgaytaoDesc(
				hocVienRepository.findById(hocvienid).get(), trangThaiLop);
		return list;
	}

	@Override
	public List<Lop> findByGiaSuAndTrangThaiUngTuyen(Long giasuid, TrangThaiUngTuyen trangThaiUngTuyen) {
		List<Lop> list = lopRepository.findByGiaSuAndTrangThaiUngTuyen(giasuid, trangThaiUngTuyen.toString());
		return list;
	}
	
	@Override
	public List<Lop> findByHocVienAndKetThuc(Long hocvienid){
		List<Lop> list = lopRepository.findByHocVienAndKetThuc(hocvienid, 
				TrangThaiLop.CANGIAO.toString(), TrangThaiLop.DAGIAO.toString());
		return list;
	}

	@Override
	public List<Lop> loplienquan(Long lopid) {
		Lop l = getLopById(lopid);
		List<Lop> list = lopRepository.findLopLienQuan(TrangThaiLop.DANGTIM.toString(), 
				l.getChude().getTenmonhoc(), l.getChude().getTrinhdo(), l.getQuan(), l.getId());
		return list;
	}

}
