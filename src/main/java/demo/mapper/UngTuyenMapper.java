package demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import demo.Enum.TrangThaiCongNo;
import demo.Enum.TrangThaiUngTuyen;
import demo.dto.UngTuyenDTO;
import demo.entity.UngTuyen;

public class UngTuyenMapper {
	
	public static UngTuyenDTO toDTO(UngTuyen ungTuyen) {
		UngTuyenDTO u = new UngTuyenDTO();
		u.setId(ungTuyen.getId());
		u.setGiasuid(ungTuyen.getGiasu().getId());
		u.setLopid(ungTuyen.getLop().getId());
		if(ungTuyen.getLoimoi()!=null) {
			u.setLoimoiid(ungTuyen.getLoimoi().getId());
		}
		u.setNgayungtuyen(ungTuyen.getNgayungtuyen());
		u.setHanthanhtoan(ungTuyen.getHanthanhtoan());
		u.setTrangthaiungtuyen(ungTuyen.getTrangthaiungtuyen().toString());
		u.setTrangthaicongno(ungTuyen.getTrangthaicongno().toString());
		return u;
	}
	
	public static UngTuyen toEntity(UngTuyenDTO ungTuyenDTO) {
		UngTuyen u = new UngTuyen();
		u.setId(ungTuyenDTO.getId());
		u.setNgayungtuyen(ungTuyenDTO.getNgayungtuyen());
		u.setHanthanhtoan(ungTuyenDTO.getHanthanhtoan());
		if(ungTuyenDTO.getTrangthaiungtuyen() != null) {
			u.setTrangthaiungtuyen(TrangThaiUngTuyen.valueOf(ungTuyenDTO.getTrangthaiungtuyen()));
		}
		if(ungTuyenDTO.getTrangthaicongno() != null) {
			u.setTrangthaicongno(TrangThaiCongNo.valueOf(ungTuyenDTO.getTrangthaicongno()));
		}
		return u;
	}
	
	public static UngTuyen update(UngTuyen ungTuyen, UngTuyenDTO ungTuyenDTO) {
		ungTuyen.setNgayungtuyen(ungTuyenDTO.getNgayungtuyen());
		ungTuyen.setHanthanhtoan(ungTuyenDTO.getHanthanhtoan());
		if(ungTuyenDTO.getTrangthaiungtuyen() != null) {
			ungTuyen.setTrangthaiungtuyen(TrangThaiUngTuyen.valueOf(ungTuyenDTO.getTrangthaiungtuyen()));
		}
		if(ungTuyenDTO.getTrangthaicongno() != null) {
			ungTuyen.setTrangthaicongno(TrangThaiCongNo.valueOf(ungTuyenDTO.getTrangthaicongno()));
		}
		return ungTuyen;
	}
	
	public static List<UngTuyenDTO> toListDTO(List<UngTuyen> list){
		if(list == null)
			return null;
		return list.stream().map(u -> toDTO(u)).collect(Collectors.toList());
	}
	
	public static Page<UngTuyenDTO> toPageDTO(Page<UngTuyen> page){
		if(page == null)
			return null;
		List<UngTuyenDTO> pageDTO = page.stream().map(u -> toDTO(u)).collect(Collectors.toList());
		return new PageImpl<>(pageDTO, page.getPageable(), page.getSize());
	}
}
