package demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import demo.dto.ThanhToanDTO;
import demo.entity.ThanhToan;

public class ThanhToanMapper {
	
	public static ThanhToanDTO toDTO(ThanhToan thanhToan) {
		ThanhToanDTO t = new ThanhToanDTO();
		t.setId(thanhToan.getId());
		t.setNganhang(thanhToan.getNganhang());
		t.setMagiaodichnganhang(thanhToan.getMagiaodichnganhang());
		t.setNoidung(thanhToan.getNoidung());
		t.setSotien(thanhToan.getSotien());
		t.setNgaythanhtoan(thanhToan.getNgaythanhtoan());
		t.setUngtuyenid(thanhToan.getUngtuyen().getId());
		return t;
	}
	
	public static ThanhToan toEntity(ThanhToanDTO thanhToanDTO) {
		ThanhToan t = new ThanhToan();
		t.setId(thanhToanDTO.getId());
		t.setNganhang(thanhToanDTO.getNganhang());
		t.setMagiaodichnganhang(thanhToanDTO.getMagiaodichnganhang());
		t.setNoidung(thanhToanDTO.getNoidung());
		t.setSotien(thanhToanDTO.getSotien());
		t.setNgaythanhtoan(thanhToanDTO.getNgaythanhtoan());
		return t;
	}
	
	public static List<ThanhToanDTO> toListDTO(List<ThanhToan> list){
		if(list == null)
			return null;
		return list.stream().map(t -> toDTO(t)).collect(Collectors.toList());
	}

	public static List<ThanhToan> toListEntity(List<ThanhToanDTO> list){
		if(list == null)
			return null;
		return list.stream().map(t -> toEntity(t)).collect(Collectors.toList());
	}
	
	public static Page<ThanhToanDTO> toPageDTO(Page<ThanhToan> page){
		if(page == null)
			return null;
		List<ThanhToanDTO> pageDTO = page.stream().map(t -> toDTO(t)).collect(Collectors.toList());
		return new PageImpl<>(pageDTO, page.getPageable(), page.getSize());
	}
}
