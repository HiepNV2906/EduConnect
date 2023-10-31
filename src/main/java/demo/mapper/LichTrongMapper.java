package demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import demo.dto.LichTrongDTO;
import demo.entity.LichTrong;
import demo.entity.composite.LopLichTrongId;

public class LichTrongMapper {
	
	public static LichTrongDTO toDTO(LichTrong lichTrong) {
		LichTrongDTO l = new LichTrongDTO();
		l.setLopid(lichTrong.getLopLichTrongId().getLopid());
		l.setThu(lichTrong.getLopLichTrongId().getThu());
		l.setSang(lichTrong.getSang());
		l.setChieu(lichTrong.getChieu());
		l.setToi(lichTrong.getToi());
		return l;
	}
	
	public static LichTrong toEntity(LichTrongDTO lichTrongDTO) {
		LichTrong l = new LichTrong();
		l.setLopLichTrongId(new LopLichTrongId(lichTrongDTO.getLopid(), lichTrongDTO.getThu()));
		l.setSang(lichTrongDTO.getSang());
		l.setChieu(lichTrongDTO.getChieu());
		l.setToi(lichTrongDTO.getToi());
		return l;
	}
	
	public static List<LichTrongDTO> toListDTO(List<LichTrong> list){
		if(list == null)
			return null;
		return list.stream().map(lichtrong -> toDTO(lichtrong)).collect(Collectors.toList());
	}
	
	public static List<LichTrong> toListEntity(List<LichTrongDTO> list){
		if(list == null)
			return null;
		return list.stream().map(lichtrongDTO -> toEntity(lichtrongDTO)).collect(Collectors.toList());
	}
}
