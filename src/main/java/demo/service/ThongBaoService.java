package demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.Enum.TrangThaiThongBao;
import demo.dto.ThongBaoDTO;
import demo.entity.ThongBao;

public interface ThongBaoService {
	public ThongBao addThongBao(ThongBaoDTO thongBaoDTO);
	public ThongBao updateThongBao(ThongBaoDTO thongBaoDTO);
	public ThongBao updateTrangThaiThongBao(Long thongBaoid, TrangThaiThongBao trangThaiThongBao);
	public void deleteThongBao(Long id);
	public ThongBao getThongBaoById(Long id);
	public List<ThongBao> getListThongBao();
	public Page<ThongBao> getListThongBao(Pageable pageable);
	public List<ThongBaoDTO> getListThongBaoByUserId(Long id);
	public Page<ThongBao> getListThongBaoByUserId(Long id, Pageable pageable);
	public List<ThongBao> getByTrangThaiThongBao(TrangThaiThongBao trangThaiThongBao);
	public Page<ThongBao> getByTrangThaiThongBao(TrangThaiThongBao trangThaiThongBao, Pageable pageable);
}
