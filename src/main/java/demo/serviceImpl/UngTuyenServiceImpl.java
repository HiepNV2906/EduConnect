package demo.serviceImpl;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.Enum.TrangThaiCongNo;
import demo.Enum.TrangThaiLoiMoi;
import demo.Enum.TrangThaiLop;
import demo.Enum.TrangThaiUngTuyen;
import demo.Enum.TrangThaiUser;
import demo.Enum.VaiTro;
import demo.dto.UngTuyenDTO;
import demo.entity.GiaSu;
import demo.entity.LoiMoi;
import demo.entity.Lop;
import demo.entity.UngTuyen;
import demo.entity.User;
import demo.exception.LopException;
import demo.exception.UngTuyenException;
import demo.exception.UserException;
import demo.mapper.ThongBaoModel;
import demo.mapper.UngTuyenMapper;
import demo.repository.GiaSuRepository;
import demo.repository.LoiMoiRepository;
import demo.repository.LopRepository;
import demo.repository.ThongBaoRepository;
import demo.repository.UngTuyenRepository;
import demo.repository.UserRepository;
import demo.service.UngTuyenService;

@Service
public class UngTuyenServiceImpl implements UngTuyenService{

	private final int maxsizeListUngTuyen = 6;
	
	@Autowired
	UngTuyenRepository ungTuyenRepository;
	@Autowired
	LopRepository lopRepository;
	@Autowired
	GiaSuRepository giaSuRepository;
	@Autowired
	LoiMoiRepository loiMoiRepository;
	@Autowired
	ThongBaoRepository thongBaoRepository;
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UngTuyen addUngTuyen(UngTuyenDTO ungtuyenDTO) {
		Integer count = ungTuyenRepository.countByLopId(ungtuyenDTO.getLopid());
		if(count>=maxsizeListUngTuyen) {
			throw new UngTuyenException("Số lượng ứng tuyển cho lớp đã đạt tối đa");
		}
		if(ungTuyenRepository.findByGiaSuIdAndLopId(ungtuyenDTO.getGiasuid(), ungtuyenDTO.getLopid()).isPresent()) {
			throw new UngTuyenException("Ứng tuyển nhận lớp đã tồn tại");
		}
		UngTuyen u = UngTuyenMapper.toEntity(ungtuyenDTO);
		u.setId(null);
		u.setNgayungtuyen(new Date());
		u.setTrangthaiungtuyen(TrangThaiUngTuyen.CHO);
		u.setTrangthaicongno(TrangThaiCongNo.KHONG);
		GiaSu giaSu = giaSuRepository.findById(ungtuyenDTO.getGiasuid()).get();
		if(giaSu.getTrangthai()!=TrangThaiUser.DAPHEDUYET) {
			throw new UserException("Tài khoản gia sư chưa được phê duyệt");
		}
		u.setGiasu(giaSu);
		Lop lop = lopRepository.findById(ungtuyenDTO.getLopid()).get();
		if(lop.getTrangthailop()!=TrangThaiLop.DANGTIM) {
			throw new LopException("Lớp không tìm gia sư");
		}
		u.setLop(lop);
		Optional<LoiMoi> loimoi = loiMoiRepository.findByGiaSuIdAndLopId(ungtuyenDTO.getGiasuid(), ungtuyenDTO.getLopid());
		if(loimoi.isPresent()) {
			LoiMoi l = loimoi.get();
			l.setTrangthailoimoi(TrangThaiLoiMoi.THANHCONG);
			u.setLoimoi(l);
		}
		UngTuyen ungTuyen = ungTuyenRepository.save(u);

//		Thông báo
		thongBaoRepository.save(ThongBaoModel.ungTuyenLop(ungTuyen.getLop().getHocvien(), (ungTuyen.getLoimoi()!=null), lop.getId()));
		if(count+1 == maxsizeListUngTuyen) {
			lop.setTrangthailop(TrangThaiLop.CANGIAO);
			lopRepository.save(lop);
			List<User> users = userRepository.findByVaitro(VaiTro.ADMIN);
			for (User user : users) {
				thongBaoRepository.save(ThongBaoModel.sapXepGiaSu(user, true));
			}
		}
		return ungTuyen;
	}

	@Override
	public UngTuyen updateUngTuyen(UngTuyenDTO ungtuyenDTO) {
		UngTuyen u = UngTuyenMapper.update(getUngTuyenById(ungtuyenDTO.getId()), ungtuyenDTO);
		u.setGiasu(giaSuRepository.findById(ungtuyenDTO.getGiasuid()).get());
		u.setLop(lopRepository.findById(ungtuyenDTO.getLopid()).get());
		Optional<LoiMoi> loimoi = loiMoiRepository.findByGiaSuIdAndLopId(ungtuyenDTO.getGiasuid(), ungtuyenDTO.getLopid());
		if(loimoi.isPresent()) {
			LoiMoi l = loimoi.get();
			u.setLoimoi(l);
		}
		UngTuyen ungTuyen = ungTuyenRepository.save(u);
		return ungTuyen;
	}

	@Override
	public void deleteUngTuyen(Long id) {
		UngTuyen u = ungTuyenRepository.findById(id).get();
		LoiMoi l = u.getLoimoi();
		if(l!=null) {
			l.setTrangthailoimoi(TrangThaiLoiMoi.CHO);
			loiMoiRepository.save(l);
		}
		ungTuyenRepository.deleteById(id);
	}

	@Override
	public UngTuyen getUngTuyenById(Long id) {
		UngTuyen u = ungTuyenRepository.findById(id).get();
		return u;
	}

	@Override
	public List<UngTuyen> findByGiaSuId(Long giasuid) {
		List<UngTuyen> list = ungTuyenRepository.findByGiaSuId(giasuid);
		return list;
	}

	@Override
	public Page<UngTuyen> findByGiaSuId(Long giasuid, Pageable pageable) {
		Page<UngTuyen> page = ungTuyenRepository.findByGiaSuId(giasuid, pageable);
		return page;
	}

	@Override
	public List<UngTuyen> findByLopId(Long lopid) {
		List<UngTuyen> list = ungTuyenRepository.findByLopId(lopid);
		return list;
	}

	@Override
	public Page<UngTuyen> findByLopId(Long lopid, Pageable pageable) {
		Page<UngTuyen> page = ungTuyenRepository.findByLopId(lopid, pageable);
		return page;
	}

	@Override
	public List<UngTuyen> findByTrangThaiUngTuyen(TrangThaiUngTuyen trangThaiUngTuyen) {
		List<UngTuyen> list = ungTuyenRepository.findByTrangthaiungtuyen(trangThaiUngTuyen);
		return list;
	}

	@Override
	public Page<UngTuyen> findByTrangThaiUngTuyen(TrangThaiUngTuyen trangThaiUngTuyen, Pageable pageable) {
		Page<UngTuyen> page = ungTuyenRepository.findByTrangthaiungtuyen(trangThaiUngTuyen, pageable);
		return page;
	}

	@Override
	public List<UngTuyen> findByTrangThaiCongNo(TrangThaiCongNo trangThaiCongNo) {
		List<UngTuyen> list = ungTuyenRepository.findByTrangthaicongno(trangThaiCongNo);
		return list;
	}

	@Override
	public Page<UngTuyen> findByTrangThaiCongNo(TrangThaiCongNo trangThaiCongNo, Pageable pageable) {
		Page<UngTuyen> page = ungTuyenRepository.findByTrangthaicongno(trangThaiCongNo, pageable);
		return page;
	}

	@Override
	public UngTuyen updateTrangThaiUngTuyen(Long ungTuyenId, TrangThaiUngTuyen trangThaiUngTuyen) {
		UngTuyen u = getUngTuyenById(ungTuyenId);
		u.setTrangthaiungtuyen(trangThaiUngTuyen);
		UngTuyen ungTuyen = ungTuyenRepository.save(u);
		return ungTuyen;
	}

	@Override
	public UngTuyen updateTrangThaiCongNo(Long ungTuyenId, TrangThaiCongNo trangThaiCongNo) {
		UngTuyen u = getUngTuyenById(ungTuyenId);
		u.setTrangthaicongno(trangThaiCongNo);;
		UngTuyen ungTuyen = ungTuyenRepository.save(u);
		return ungTuyen;
	}

	@Override
	public List<UngTuyen> sapXepGiaSu(Long ungtuyenid) {
		UngTuyen u = ungTuyenRepository.findById(ungtuyenid).get();
		Lop lop = u.getLop();
		lop.setNgaygiao(new Date());
		lop.setTrangthailop(TrangThaiLop.DAGIAO);
		
		List<UngTuyen> list = lop.getDsungtuyen();
		for (UngTuyen ungTuyen : list) {
			if(ungTuyen.getId() == ungtuyenid) {
				ungTuyen.setTrangthaiungtuyen(TrangThaiUngTuyen.THANHCONG);
				ungTuyen.setTrangthaicongno(TrangThaiCongNo.CHUATHANHTOAN);
				LocalDate han = LocalDate.now().plusMonths(1);
				Date date = Date.from(han.atStartOfDay(ZoneId.systemDefault()).toInstant());
				ungTuyen.setHanthanhtoan(date);
				ungTuyenRepository.save(ungTuyen);
				
				//	Thông Báo
				thongBaoRepository.save(ThongBaoModel.ketQuaUngTuyen(ungTuyen.getGiasu(), true));
				thongBaoRepository.save(ThongBaoModel.ycThanhToan(ungTuyen.getGiasu(), ungTuyen.getHanthanhtoan().toString()));
				thongBaoRepository.save(ThongBaoModel.chonDuocGiaSu(ungTuyen.getLop().getHocvien()));
			} else {
				ungTuyen.setTrangthaiungtuyen(TrangThaiUngTuyen.TUCHOI);
				ungTuyen.setTrangthaicongno(TrangThaiCongNo.KHONG);
				ungTuyenRepository.save(ungTuyen);
				
				//	Thông Báo
				thongBaoRepository.save(ThongBaoModel.ketQuaUngTuyen(ungTuyen.getGiasu(), false));
			}
		}
		lop.setDsungtuyen(list);
		lopRepository.save(lop);
		return list;
	}

	@Override
	public List<UngTuyen> findByGiaSuAndTrangThaiCongNo(Long giasuid, TrangThaiCongNo trangThaiCongNo) {
		GiaSu giaSu = giaSuRepository.findById(giasuid).get();
		List<UngTuyen> list = ungTuyenRepository.findByGiasuAndTrangthaicongno(giaSu, trangThaiCongNo);
		return list;
	}

}
