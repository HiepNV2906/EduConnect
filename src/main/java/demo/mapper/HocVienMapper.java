package demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import demo.Enum.TrangThaiUser;
import demo.dto.HocVienDTO;
import demo.dto.LopDTO;
import demo.dto.ThongBaoDTO;
import demo.entity.HocVien;

public class HocVienMapper {
	
	public static HocVienDTO toDTO(HocVien hocVien) {
		HocVienDTO h = new HocVienDTO();
		h.setId(hocVien.getId());
		h.setHoten(hocVien.getHoten());
		h.setSdt(hocVien.getSdt());
		h.setEmail(hocVien.getEmail());
		h.setAvata(hocVien.getAvata());
		h.setGioitinh(hocVien.getGioitinh());
		h.setNgaysinh(hocVien.getNgaysinh());
		h.setQuan(hocVien.getQuan());
		h.setDiachi(hocVien.getDiachi());
		h.setCccd(hocVien.getCccd());
		h.setTrangthai(hocVien.getTrangthai().toString());
		List<ThongBaoDTO> listthongbao = ThongBaoMapper.toListDTO(hocVien.getDsthongbao());
		h.setDsthongbao(listthongbao);
		List<LopDTO> listlop = LopMapper.toListDTO(hocVien.getDslop());
		h.setDslop(listlop);
		return h;
	}
	
	public static HocVien toEntity(HocVienDTO hocVienDTO) {
		HocVien u = new HocVien();
		u.setId(hocVienDTO.getId());
		u.setHoten(hocVienDTO.getHoten());
		u.setSdt(hocVienDTO.getSdt());
		u.setEmail(hocVienDTO.getEmail());
		u.setAvata(hocVienDTO.getAvata());
		u.setGioitinh(hocVienDTO.getGioitinh());
		u.setNgaysinh(hocVienDTO.getNgaysinh());
		u.setQuan(hocVienDTO.getQuan());
		u.setDiachi(hocVienDTO.getDiachi());
		u.setCccd(hocVienDTO.getCccd());
		if(hocVienDTO.getTrangthai() != null) {
			u.setTrangthai(TrangThaiUser.valueOf(hocVienDTO.getTrangthai()));
		}
		return u;
	}
	
	public static HocVien update(HocVien hocVien, HocVienDTO hocVienDTO) {
		hocVien.setHoten(hocVienDTO.getHoten());
		hocVien.setSdt(hocVienDTO.getSdt());
		hocVien.setAvata(hocVienDTO.getAvata());
		hocVien.setGioitinh(hocVienDTO.getGioitinh());
		hocVien.setNgaysinh(hocVienDTO.getNgaysinh());
		hocVien.setQuan(hocVienDTO.getQuan());
		hocVien.setDiachi(hocVienDTO.getDiachi());
		hocVien.setCccd(hocVienDTO.getCccd());
		return hocVien;
	}
	
	public static List<HocVienDTO> toListDTO(List<HocVien> list){
		if(list == null)
			return null;
		return list.stream().map(h -> toDTO(h)).collect(Collectors.toList());
	}
	
	public static List<HocVien> toListEntity(List<HocVienDTO> list){
		if(list == null)
			return null;
		return list.stream().map(h -> toEntity(h)).collect(Collectors.toList());
	}
	
	public static Page<HocVienDTO> toPageDTO(Page<HocVien> page){
		if(page == null)
			return null;
		List<HocVienDTO> pageDTO = page.stream().map(h -> toDTO(h)).collect(Collectors.toList());
		return new PageImpl<>(pageDTO, page.getPageable(), page.getSize());
	}
}
