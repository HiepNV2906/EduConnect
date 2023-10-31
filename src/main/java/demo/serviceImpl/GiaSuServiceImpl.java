package demo.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.Enum.TrangThaiUser;
import demo.Enum.VaiTro;
import demo.dto.GiaSuDTO;
import demo.entity.ChuDe;
import demo.entity.GiaSu;
import demo.entity.User;
import demo.exception.UserException;
import demo.mapper.GiaSuMapper;
import demo.mapper.ThongBaoModel;
import demo.repository.ChuDeRepository;
import demo.repository.GiaSuRepository;
import demo.repository.ThongBaoRepository;
import demo.repository.UserRepository;
import demo.request.RegisterGiaSuRequest;
import demo.service.GiaSuService;

@Service
public class GiaSuServiceImpl implements GiaSuService{

	@Autowired
	GiaSuRepository giaSuRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ChuDeRepository chuDeRepository;
	@Autowired
	ThongBaoRepository thongBaoRepository;

	//	Tạo tài khoản gia sư
	@Override
	public GiaSu addGiaSu(RegisterGiaSuRequest registerGiaSuRequest) {
		if(userRepository.findByEmail(registerGiaSuRequest.getEmail()).isPresent()) {
			throw new UserException("Email đã tồn tại!!!");
		}
		GiaSu g = registerGiaSuRequest.toGiaSuEntity();
		g.setId(null);
		g.setVaitro(VaiTro.GIASU);
		g.setTrangthai(TrangThaiUser.CHUAPHEDUYET);
		List<ChuDe> listChuDe = registerGiaSuRequest.getDschude().stream().map(c -> 
			chuDeRepository.findById(c.getId()).get()).collect(Collectors.toList());
		g.setDschude(listChuDe);
		GiaSu giaSu = giaSuRepository.save(g);
		
		//		Thông báo
		List<User> admin = userRepository.findByVaitro(VaiTro.ADMIN);
		for (User user : admin) {
			thongBaoRepository.save(ThongBaoModel.ycDangKyTaiKhoan(user));
		}
		thongBaoRepository.save(ThongBaoModel.dangKyThanhCong(giaSu));
		return giaSu;
	}

	@Override
	public GiaSu updateGiaSu(GiaSuDTO giasuDTO) {
		GiaSu g = GiaSuMapper.update(getGiaSuById(giasuDTO.getId()), giasuDTO);
		List<ChuDe> listChuDe = giasuDTO.getDschude().stream().map(c -> 
			chuDeRepository.findById(c.getId()).get()).collect(Collectors.toList());
		g.setDschude(listChuDe);
		GiaSu giaSu = giaSuRepository.save(g);
		
//		Thông báo
		thongBaoRepository.save(ThongBaoModel.capNhatTaiKhoan(giaSu));
		return giaSu;
	}

	@Override
	public void deleteGiaSu(Long id) {
		giaSuRepository.deleteById(id);
	}

	@Override
	public GiaSu getGiaSuById(Long id) {
		GiaSu g = giaSuRepository.findById(id).get();
		return g;
	}

	@Override
	public Page<GiaSu> getListGiaSu(Pageable pageable) {
		Page<GiaSu> page = giaSuRepository.findAll(pageable);
		return page;
	}

	@Override
	public List<GiaSu> getListGiaSu() {
		List<GiaSu> list = giaSuRepository.findAll();
		return list;
	}

	@Override
	public Page<GiaSu> findByKey(String key, Pageable pageable) {
		Page<GiaSu> page = giaSuRepository.findByKeyword(key, pageable);
		return page;
	}

	@Override
	public List<GiaSu> findByKey(String key) {
		List<GiaSu> list = giaSuRepository.findByKeyword(key);
		return list;
	}

	@Override
	public Page<GiaSu> findByFilter(String quan, String nghenghiep, String gioitinh, String mon, String trinhdo, Pageable pageable) {
		Page<GiaSu> page = giaSuRepository.findByFilter(quan, nghenghiep, gioitinh, mon, trinhdo, pageable);
		return page;
	}

	@Override
	public List<GiaSu> findByFilter(String quan, String nghenghiep, String gioitinh, String mon, String trinhdo) {
		List<GiaSu> list = giaSuRepository.findByFilter(quan, nghenghiep, gioitinh, mon, trinhdo);
		return list;
	}

	@Override
	public Page<GiaSu> findByTrangThai(TrangThaiUser trangThaiUser, Pageable pageable) {
		Page<GiaSu> page = giaSuRepository.findByTrangthai(trangThaiUser, pageable);
		return page;
	}

	@Override
	public List<GiaSu> findByTrangThai(TrangThaiUser trangThaiUser) {
		List<GiaSu> list = giaSuRepository.findByTrangthai(trangThaiUser);
		return list;
	}

	@Override
	public GiaSu updateTrangThai(Long giasuId, TrangThaiUser trangThaiUser) {
		GiaSu g = getGiaSuById(giasuId);
		g.setTrangthai(trangThaiUser);
		GiaSu giaSu = giaSuRepository.save(g);
		
		//Thông báo
		if(giaSu.getTrangthai() == TrangThaiUser.DAPHEDUYET) {
			thongBaoRepository.save(ThongBaoModel.pheDuyetTaiKhoan(giaSu));
		}
		if(giaSu.getTrangthai() == TrangThaiUser.DINHCHI) {
			thongBaoRepository.save(ThongBaoModel.dinhChiTaiKhoan(giaSu));
		}
		return giaSu;
	}

}
