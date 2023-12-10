package demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import demo.Enum.TrangThaiLop;
import demo.dto.LopDTO;
import demo.entity.Lop;

public class LopMapper {
	
	public static LopDTO toDTO(Lop lop) {
		LopDTO l = new LopDTO();
		l.setId(lop.getId());
		l.setTieude(lop.getTieude());
		l.setQuan(lop.getQuan());
		l.setDiachi(lop.getDiachi());
		l.setSobuoi(lop.getSobuoi());
		l.setSogio(lop.getSogio());
		l.setHocphi(lop.getHocphi());
		l.setPhiungtuyen(lop.getPhiungtuyen());
		l.setSohs(lop.getSohs());
		l.setGioitinhhs(lop.getGioitinhhs());
		l.setMotahs(lop.getMotahs());
		l.setNghenghiepgs(lop.getNghenghiepgs());
		l.setGioitinhgs(lop.getGioitinhgs());
		l.setTruonggs(lop.getTruonggs());
		l.setYeucaukhac(lop.getYeucaukhac());
		l.setNgaytao(lop.getNgaytao());
		l.setNgaygiao(lop.getNgaygiao());
		l.setHanungtuyen(lop.getHanungtuyen());
		l.setHinhthuc(lop.getHinhthuc());
		l.setTrangthailop(lop.getTrangthailop().toString());
		l.setHocvienid(lop.getHocvien().getId());
		l.setChude(ChuDeMapper.toDTO(lop.getChude()));
		l.setDslichtrong(LichTrongMapper.toListDTO(lop.getDslichtrong()));
		l.setDsungtuyen(UngTuyenMapper.toListDTO(lop.getDsungtuyen()));
		return l;
	}
	
	public static Lop toEntity(LopDTO lopDTO) {
		Long phiUT = Math.round(lopDTO.getHocphi()*lopDTO.getSobuoi()*4*0.25);
		Lop l = new Lop();
		l.setId(lopDTO.getId());
		l.setTieude(lopDTO.getTieude());
		l.setQuan(lopDTO.getQuan());
		l.setDiachi(lopDTO.getDiachi());
		l.setSobuoi(lopDTO.getSobuoi());
		l.setSogio(lopDTO.getSogio());
		l.setHocphi(lopDTO.getHocphi());
		l.setPhiungtuyen(phiUT);
		l.setSohs(lopDTO.getSohs());
		l.setGioitinhhs(lopDTO.getGioitinhhs());
		l.setMotahs(lopDTO.getMotahs());
		l.setNghenghiepgs(lopDTO.getNghenghiepgs());
		l.setGioitinhgs(lopDTO.getGioitinhgs());
		l.setTruonggs(lopDTO.getTruonggs());
		l.setYeucaukhac(lopDTO.getYeucaukhac());
		l.setNgaytao(lopDTO.getNgaytao());
		l.setHanungtuyen(lopDTO.getHanungtuyen());
		l.setHinhthuc(lopDTO.getHinhthuc());
		if(lopDTO.getTrangthailop() != null) {
			l.setTrangthailop(TrangThaiLop.valueOf(lopDTO.getTrangthailop()));
		}
		if(lopDTO.getNgaygiao() != null) {
			l.setNgaygiao(lopDTO.getNgaygiao());
		}
		return l;
	}
	
	public static Lop update(Lop l, LopDTO lopDTO) {
		Long phiUT = Math.round(lopDTO.getHocphi()*lopDTO.getSobuoi()*4*0.25);
		l.setTieude(lopDTO.getTieude());
		l.setQuan(lopDTO.getQuan());
		l.setDiachi(lopDTO.getDiachi());
		l.setSobuoi(lopDTO.getSobuoi());
		l.setSogio(lopDTO.getSogio());
		l.setHocphi(lopDTO.getHocphi());
		l.setSohs(lopDTO.getSohs());
		l.setGioitinhhs(lopDTO.getGioitinhhs());
		l.setMotahs(lopDTO.getMotahs());
		l.setNghenghiepgs(lopDTO.getNghenghiepgs());
		l.setGioitinhgs(lopDTO.getGioitinhgs());
		l.setTruonggs(lopDTO.getTruonggs());
		l.setYeucaukhac(lopDTO.getYeucaukhac());
		l.setHanungtuyen(lopDTO.getHanungtuyen());
		l.setHinhthuc(lopDTO.getHinhthuc());
		if(lopDTO.getTrangthailop() != null) {
			l.setTrangthailop(TrangThaiLop.valueOf(lopDTO.getTrangthailop()));
		}
		if(lopDTO.getPhiungtuyen()!=null) {
			l.setPhiungtuyen(lopDTO.getPhiungtuyen());
		} else {
			l.setPhiungtuyen(phiUT);
		}
		if(lopDTO.getNgaygiao() != null) {
			l.setNgaygiao(lopDTO.getNgaygiao());
		}
		return l;
	}
	
	public static List<LopDTO> toListDTO(List<Lop> list){
		if(list == null)
			return null;
		return list.stream().map(u -> toDTO(u)).collect(Collectors.toList());
	}
	
	public static Page<LopDTO> toPageDTO(Page<Lop> page){
		if(page == null)
			return null;
		List<LopDTO> pageDTO = page.stream().map(u -> toDTO(u)).collect(Collectors.toList());
		return new PageImpl<>(pageDTO, page.getPageable(), page.getSize());
	}
}
