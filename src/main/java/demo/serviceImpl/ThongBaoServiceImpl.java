package demo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.Enum.TrangThaiThongBao;
import demo.dto.ThongBaoDTO;
import demo.entity.ThongBao;
import demo.mapper.ThongBaoMapper;
import demo.repository.ThongBaoRepository;
import demo.repository.UserRepository;
import demo.service.ThongBaoService;

@Service
public class ThongBaoServiceImpl implements ThongBaoService{

	@Autowired
	ThongBaoRepository thongBaoRepository;
	@Autowired
	UserRepository userRepository;
	
	@Override
	public ThongBao addThongBao(ThongBaoDTO thongBaoDTO) {
		ThongBao t = ThongBaoMapper.toEntity(thongBaoDTO);
		t.setId(null);
		t.setTrangthaithongbao(TrangThaiThongBao.CHUAXEM);
		t.setUser(userRepository.findById(thongBaoDTO.getUserid()).get());
		ThongBao thongbao = thongBaoRepository.save(t);
		return thongbao;
	}

	@Override
	public ThongBao updateThongBao(ThongBaoDTO thongBaoDTO) {
		ThongBao t = ThongBaoMapper.toEntity(thongBaoDTO);
		t.setTrangthaithongbao(TrangThaiThongBao.CHUAXEM);
		t.setUser(userRepository.findById(thongBaoDTO.getUserid()).get());
		ThongBao thongbao = thongBaoRepository.save(t);
		return thongbao;
	}

	@Override
	public void deleteThongBao(Long id) {
		thongBaoRepository.deleteById(id);
	}

	@Override
	public ThongBao getThongBaoById(Long id) {
		ThongBao t = thongBaoRepository.findById(id).get();
		return t;
	}

	@Override
	public List<ThongBao> getListThongBao() {
		List<ThongBao> list = thongBaoRepository.findAll();
		return list;
	}

	@Override
	public Page<ThongBao> getListThongBao(Pageable pageable) {
		Page<ThongBao> page = thongBaoRepository.findAll(pageable);
		return page;
	}

	@Override
	public List<ThongBao> getListThongBaoByUserId(Long id) {
		List<ThongBao> list = thongBaoRepository.findByUserId(id);
		return list;
	}

	@Override
	public Page<ThongBao> getListThongBaoByUserId(Long id, Pageable pageable) {
		Page<ThongBao> page = thongBaoRepository.findByUserId(id, pageable);
		return page;
	}

	@Override
	public List<ThongBao> getByTrangThaiThongBao(TrangThaiThongBao trangThaiThongBao) {
		List<ThongBao> list = thongBaoRepository.findByTrangthaithongbao(trangThaiThongBao);
		return list;
	}

	@Override
	public Page<ThongBao> getByTrangThaiThongBao(TrangThaiThongBao trangThaiThongBao, Pageable pageable) {
		Page<ThongBao> page = thongBaoRepository.findByTrangthaithongbao(trangThaiThongBao, pageable);
		return page;
	}

	@Override
	public ThongBao updateTrangThaiThongBao(Long thongBaoid, TrangThaiThongBao trangThaiThongBao) {
		ThongBao t = thongBaoRepository.findById(thongBaoid).get();
		t.setTrangthaithongbao(trangThaiThongBao);
		thongBaoRepository.save(t);
		return t;
	}

}
