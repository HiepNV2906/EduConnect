package demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import demo.Enum.TrangThaiThongBao;
import demo.dto.ThongBaoDTO;
import demo.entity.ThongBao;

@Component
public class ThongBaoMapper {
	
	public static ThongBaoDTO toDTO(ThongBao thongbao) {
		ThongBaoDTO t = new ThongBaoDTO();
		t.setId(thongbao.getId());
		t.setTieude(thongbao.getTieude());
		t.setNoidung(thongbao.getNoidung());
		t.setNgay(thongbao.getNgay());
		t.setTrangthaithongbao(thongbao.getTrangthaithongbao().toString());
		t.setUserid(thongbao.getUser().getId());
		return t;
	}
	
	public static ThongBao toEntity(ThongBaoDTO thongbaoDTO) {
		ThongBao t = new ThongBao();
		t.setId(thongbaoDTO.getId());
		t.setTieude(thongbaoDTO.getTieude());
		t.setNoidung(thongbaoDTO.getNoidung());
		t.setNgay(thongbaoDTO.getNgay());
		if(thongbaoDTO.getTrangthaithongbao() != null) {
			t.setTrangthaithongbao(TrangThaiThongBao.valueOf(thongbaoDTO.getTrangthaithongbao()));
		}
		return t;
	}
	
	public static List<ThongBaoDTO> toListDTO(List<ThongBao> list){
		if(list == null)
			return null;
		return list.stream().map(thongbao -> toDTO(thongbao)).collect(Collectors.toList());
	}
	
	public static List<ThongBao> toListEntity(List<ThongBaoDTO> list){
		if(list == null)
			return null;
		return list.stream().map(thongbaoDTO -> toEntity(thongbaoDTO)).collect(Collectors.toList());
	}
	
	public static Page<ThongBaoDTO> toPageDTO(Page<ThongBao> page){
		if(page == null)
			return null;
		List<ThongBaoDTO> pageDTO = page.stream().map(thongbao -> toDTO(thongbao)).collect(Collectors.toList());
		return new PageImpl<>(pageDTO, page.getPageable(), page.getSize());
	}
}
