package demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import demo.dto.ChuDeDTO;
import demo.entity.ChuDe;

public class ChuDeMapper {
	
	public static ChuDeDTO toDTO(ChuDe chude) {
		ChuDeDTO c = new ChuDeDTO();
		c.setId(chude.getId());
		c.setTenmonhoc(chude.getTenmonhoc());
		c.setTrinhdo(chude.getTrinhdo());
		return c;
	}
	
	public static ChuDe toEntity(ChuDeDTO chudeDTO) {
		ChuDe c = new ChuDe();
		c.setId(chudeDTO.getId());
		c.setTenmonhoc(chudeDTO.getTenmonhoc());
		c.setTrinhdo(chudeDTO.getTrinhdo());
		return c;
	}
	
	public static List<ChuDeDTO> toListDTO(List<ChuDe> list){
		if(list == null)
			return null;
		return list.stream().map(c -> toDTO(c)).collect(Collectors.toList());
	}
	
	public static List<ChuDe> toListEntity(List<ChuDeDTO> list){
		if(list == null)
			return null;
		return list.stream().map(c -> toEntity(c)).collect(Collectors.toList());
	}
}
