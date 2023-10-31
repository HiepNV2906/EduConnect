package demo.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.Enum.TrangThaiLoiMoi;
import demo.dto.LoiMoiDTO;
import demo.entity.LoiMoi;
import demo.mapper.LoiMoiMapper;
import demo.mapper.ThongBaoModel;
import demo.repository.GiaSuRepository;
import demo.repository.LoiMoiRepository;
import demo.repository.LopRepository;
import demo.repository.ThongBaoRepository;
import demo.repository.UngTuyenRepository;
import demo.service.LoiMoiService;

@Service
public class LoiMoiServiceImpl implements LoiMoiService{
	
	@Autowired
	LoiMoiRepository loiMoiRepository;
	@Autowired
	GiaSuRepository giaSuRepository;
	@Autowired
	LopRepository lopRepository;
	@Autowired
	UngTuyenRepository ungTuyenRepository;
	@Autowired
	ThongBaoRepository thongBaoRepository;
	
	@Override
	public LoiMoi addLoiMoi(LoiMoiDTO loiMoiDTO) {
		LoiMoi l = LoiMoiMapper.toEntity(loiMoiDTO);
		l.setId(null);
		l.setGiasu(giaSuRepository.findById(loiMoiDTO.getGiasuid()).get());
		l.setLop(lopRepository.findById(loiMoiDTO.getLopid()).get());
		if(loiMoiDTO.getUngtuyenid()!=null) {
			l.setUngTuyen(ungTuyenRepository.findById(loiMoiDTO.getUngtuyenid()).get());
		}
		l.setTrangthailoimoi(TrangThaiLoiMoi.CHO);
		l = loiMoiRepository.save(l);
		
//		Thông báo
		thongBaoRepository.save(ThongBaoModel.nhanLoiMoiUngTuyen(l.getGiasu()));
		
		return l;
	}

	@Override
	public LoiMoi updateLoiMoi(LoiMoiDTO loiMoiDTO) {
		LoiMoi l = getLoiMoiById(loiMoiDTO.getId());
		l = LoiMoiMapper.update(l, loiMoiDTO);
		l = loiMoiRepository.save(l);
		return l;
	}

	@Override
	public void deleteLoiMoi(Long id) {
		loiMoiRepository.deleteById(id);
	}

	@Override
	public LoiMoi getLoiMoiById(Long id) {
		LoiMoi l = loiMoiRepository.findById(id).get();
		return l;
	}

	@Override
	public List<LoiMoi> findByGiaSuId(Long giasuid) {
		List<LoiMoi> list = loiMoiRepository.findByGiaSuId(giasuid);
		return list;
	}

	@Override
	public Page<LoiMoi> findByGiaSuId(Long giasuid, Pageable pageable) {
		Page<LoiMoi> page = loiMoiRepository.findByGiaSuId(giasuid, pageable);
		return page;
	}

	@Override
	public List<LoiMoi> findByLopId(Long lopid) {
		List<LoiMoi> list = loiMoiRepository.findByLopId(lopid);
		return list;
	}

	@Override
	public Page<LoiMoi> findByLopId(Long lopid, Pageable pageable) {
		Page<LoiMoi> page = loiMoiRepository.findByLopId(lopid, pageable);
		return page;
	}

	@Override
	public List<LoiMoi> findByTrangThaiLoiMoi(TrangThaiLoiMoi trangThaiLoiMoi) {
		List<LoiMoi> list = loiMoiRepository.findByTrangthailoimoi(trangThaiLoiMoi);
		return list;
	}

	@Override
	public Page<LoiMoi> findByTrangThaiLoiMoi(TrangThaiLoiMoi trangThaiLoiMoi, Pageable pageable) {
		Page<LoiMoi> page = loiMoiRepository.findByTrangthailoimoi(trangThaiLoiMoi, pageable);
		return page;
	}

	@Override
	public LoiMoi updateTrangThaiLoiMoi(Long loiMoiId, TrangThaiLoiMoi trangThaiLoiMoi) {
		LoiMoi l = getLoiMoiById(loiMoiId);
		l.setTrangthailoimoi(trangThaiLoiMoi);
		l = loiMoiRepository.save(l);
		return l;
	}

	@Override
	public LoiMoi findByGiaSuIdAndLopId(Long giasuid, Long lopid) {
		Optional<LoiMoi> loimoi = loiMoiRepository.findByGiaSuIdAndLopId(giasuid, lopid);
		if(loimoi.isPresent())
			return loimoi.get();
		return null;
	}

}
