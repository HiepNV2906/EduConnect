package demo.serviceImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.Enum.TrangThaiCongNo;
import demo.Enum.TrangThaiLop;
import demo.Enum.TrangThaiUngTuyen;
import demo.Enum.VaiTro;
import demo.dto.UngTuyenDTO;
import demo.entity.Lop;
import demo.entity.UngTuyen;
import demo.entity.User;
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
		Integer count = ungTuyenRepository.countByLopId(ungtuyenDTO.getLoimoiid());
		if(count>=maxsizeListUngTuyen) {
			throw new UserException("Số lượng ứng tuyển cho lớp đã đạt tối đa");
		}
		UngTuyen u = UngTuyenMapper.toEntity(ungtuyenDTO);
		u.setId(null);
		u.setTrangthaiungtuyen(TrangThaiUngTuyen.CHO);
		u.setTrangthaicongno(TrangThaiCongNo.KHONG);
		u.setGiasu(giaSuRepository.findById(ungtuyenDTO.getGiasuid()).get());
		Lop lop = lopRepository.findById(ungtuyenDTO.getLopid()).get();
		u.setLop(lop);
		if(ungtuyenDTO.getLoimoiid()!=null) {
			u.setLoimoi(loiMoiRepository.findByGiaSuIdAndLopId(ungtuyenDTO.getGiasuid(), ungtuyenDTO.getLoimoiid()).get());
		}
		UngTuyen ungTuyen = ungTuyenRepository.save(u);

//		Thông báo
		thongBaoRepository.save(ThongBaoModel.ungTuyenLop(ungTuyen.getLop().getHocvien(), (ungTuyen.getLoimoi()!=null)));
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
		if(ungtuyenDTO.getLoimoiid()!=null) {
			u.setLoimoi(loiMoiRepository.findByGiaSuIdAndLopId(ungtuyenDTO.getGiasuid(), ungtuyenDTO.getLoimoiid()).get());
		}
		UngTuyen ungTuyen = ungTuyenRepository.save(u);
		return ungTuyen;
	}

	@Override
	public void deleteUngTuyen(Long id) {
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
		lop.setTrangthailop(TrangThaiLop.DAGIAO);
		
		List<UngTuyen> list = lop.getDsungtuyen();
		for (UngTuyen ungTuyen : list) {
			if(ungTuyen.getId() == ungtuyenid) {
				ungTuyen.setTrangthaiungtuyen(TrangThaiUngTuyen.THANHCONG);
				ungTuyen.setTrangthaicongno(TrangThaiCongNo.CHUATHANHTOAN);
				LocalDate han = LocalDate.now().plusMonths(1);
				ungTuyen.setHanthanhtoan(Date.valueOf(han));
				ungTuyenRepository.save(ungTuyen);
				
				//	Thông Báo
				thongBaoRepository.save(ThongBaoModel.ketQuaUngTuyen(ungTuyen.getGiasu(), true));
				thongBaoRepository.save(ThongBaoModel.ycThanhToan(ungTuyen.getGiasu(), ungTuyen.getHanthanhtoan().toString()));
				thongBaoRepository.save(ThongBaoModel.chonDuocGiaSu(ungTuyen.getLop().getHocvien()));
			} else {
				ungTuyen.setTrangthaiungtuyen(TrangThaiUngTuyen.TUCHOI);
				ungTuyenRepository.save(ungTuyen);
				
				//	Thông Báo
				thongBaoRepository.save(ThongBaoModel.ketQuaUngTuyen(ungTuyen.getGiasu(), false));
			}
		}
		lop.setDsungtuyen(list);
		lopRepository.save(lop);
		return list;
	}

}
