package demo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.Enum.TrangThaiLop;
import demo.Enum.VaiTro;
import demo.dto.LichTrongDTO;
import demo.dto.LopDTO;
import demo.entity.Lop;
import demo.entity.User;
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
		l.setTrangthailop(TrangThaiLop.CHOPHEDUYET);
		l.setHocvien(hocVienRepository.findById(lopDTO.getHocvienid()).get());
		l.setChude(chuDeRepository.findById(lopDTO.getChudeid()).get());
		Lop lop = lopRepository.save(l);
		for (LichTrongDTO i : lopDTO.getDslichtrong()) {
			i.setLopid(lop.getId());
		}
		lop.setDslichtrong(lichTrongService.addListLichTrong(lop, lopDTO.getDslichtrong()));
		
		//		Thông báo
		List<User> admin = userRepository.findByVaitro(VaiTro.ADMIN);
		for (User user : admin) {
			thongBaoRepository.save(ThongBaoModel.ycTaoLop(user));
		}
		
		return lop;
	}

	@Override
	public Lop updateLop(LopDTO lopDTO) {
		Lop l = LopMapper.update(getLopById(lopDTO.getId()), lopDTO);
		l.setChude(chuDeRepository.findById(lopDTO.getChudeid()).get());
		Lop lop = lopRepository.save(l);
		for (LichTrongDTO i : lopDTO.getDslichtrong()) {
			i.setLopid(lop.getId());
		}
		lop.setDslichtrong(lichTrongService.addListLichTrong(lop, lopDTO.getDslichtrong()));
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
		List<Lop> list = lopRepository.findAll();
		return list;
	}

	@Override
	public Page<Lop> getListLop(Pageable pageable) {
		Page<Lop> page = lopRepository.findAll(pageable);
		return page;
	}

	@Override
	public List<Lop> findByKey(String key) {
		List<Lop> list = lopRepository.findByKeyword(key);
		return list;
	}

	@Override
	public Page<Lop> findBykey(String key, Pageable pageable) {
		Page<Lop> page = lopRepository.findByKeyword(key, pageable);
		return page;
	}

	@Override
	public List<Lop> findByFilter(String quan, String hinhthuc, Long hocphi, String mon, String trinhdo) {
		List<Lop> list = lopRepository.findByFilter(quan, hinhthuc, hocphi, mon, trinhdo);
		return list;
	}

	@Override
	public Page<Lop> findByFilter(String quan, String hinhthuc, Long hocphi, String mon, String trinhdo,
			Pageable pageable) {
		Page<Lop> page = lopRepository.findByFilter(quan, hinhthuc, hocphi, mon, trinhdo, pageable);
		return page;
	}

	@Override
	public List<Lop> findByTrangThai(TrangThaiLop trangThaiLop) {
		List<Lop> list = lopRepository.findByTrangthailop(trangThaiLop);
		return list;
	}

	@Override
	public Page<Lop> findByTrangThai(TrangThaiLop trangThaiLop, Pageable pageable) {
		Page<Lop> page = lopRepository.findByTrangthailop(trangThaiLop, pageable);
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

}
