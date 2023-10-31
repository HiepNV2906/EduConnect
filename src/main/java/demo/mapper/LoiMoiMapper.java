package demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import demo.Enum.TrangThaiLoiMoi;
import demo.dto.LoiMoiDTO;
import demo.entity.LoiMoi;

public class LoiMoiMapper {
	public static LoiMoiDTO toDTO(LoiMoi loiMoi) {
		LoiMoiDTO u = new LoiMoiDTO();
		u.setId(loiMoi.getId());
		u.setGiasuid(loiMoi.getGiasu().getId());
		u.setLopid(loiMoi.getLop().getId());
		u.setUngtuyenid(loiMoi.getUngTuyen().getId());
		u.setNgaymoi(loiMoi.getNgaymoi());
		u.setTrangthailoimoi(loiMoi.getTrangthailoimoi().toString());
		return u;
	}
	
	public static LoiMoi toEntity(LoiMoiDTO loiMoiDTO) {
		LoiMoi u = new LoiMoi();
		u.setId(loiMoiDTO.getId());
		u.setNgaymoi(loiMoiDTO.getNgaymoi());
		if(loiMoiDTO.getTrangthailoimoi() != null) {
			u.setTrangthailoimoi(TrangThaiLoiMoi.valueOf(loiMoiDTO.getTrangthailoimoi()));
		}
		return u;
	}
	
	public static LoiMoi update(LoiMoi loiMoi, LoiMoiDTO loiMoiDTO) {
		loiMoi.setNgaymoi(loiMoiDTO.getNgaymoi());
		if(loiMoiDTO.getTrangthailoimoi() != null) {
			loiMoi.setTrangthailoimoi(TrangThaiLoiMoi.valueOf(loiMoiDTO.getTrangthailoimoi()));
		}
		return loiMoi;
	}
	
	public static List<LoiMoiDTO> toListDTO(List<LoiMoi> list){
		if(list == null)
			return null;
		return list.stream().map(u -> toDTO(u)).collect(Collectors.toList());
	}
	
	public static Page<LoiMoiDTO> toPageDTO(Page<LoiMoi> page){
		if(page == null)
			return null;
		List<LoiMoiDTO> pageDTO = page.stream().map(u -> toDTO(u)).collect(Collectors.toList());
		return new PageImpl<>(pageDTO, page.getPageable(), page.getSize());
	}
}
