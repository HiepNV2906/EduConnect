package demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import demo.Enum.TrangThaiUser;
import demo.dto.ChuDeDTO;
import demo.dto.GiaSuDTO;
import demo.dto.ThongBaoDTO;
import demo.dto.UngTuyenDTO;
import demo.entity.ChuDe;
import demo.entity.GiaSu;

public class GiaSuMapper {
	
	public static GiaSuDTO toDTO(GiaSu giaSu) {
		GiaSuDTO g = new GiaSuDTO();
		g.setId(giaSu.getId());
		g.setHoten(giaSu.getHoten());
		g.setSdt(giaSu.getSdt());
		g.setEmail(giaSu.getEmail());
		g.setAvata(giaSu.getAvata());
		g.setGioitinh(giaSu.getGioitinh());
		g.setNgaysinh(giaSu.getNgaysinh());
		g.setQuan(giaSu.getQuan());
		g.setDiachi(giaSu.getDiachi());
		g.setQuequan(giaSu.getQuequan());
		g.setNghenghiep(giaSu.getNghenghiep());
		g.setTruong(giaSu.getTruong());
		g.setGioithieu(giaSu.getGioithieu());
		g.setKinhnghiem(giaSu.getKinhnghiem());
		g.setThanhtich(giaSu.getThanhtich());
		g.setCccd(giaSu.getCccd());
		g.setKhuvucday(giaSu.getKhuvucday());
		g.setTrangthai(giaSu.getTrangthai().toString());
		List<ThongBaoDTO> listthongbao = ThongBaoMapper.toListDTO(giaSu.getDsthongbao());
		g.setDsthongbao(listthongbao);
		List<UngTuyenDTO> listungtuyen = UngTuyenMapper.toListDTO(giaSu.getDsungtuyen());
		g.setDsungtuyen(listungtuyen);
		List<ChuDeDTO> listchude = ChuDeMapper.toListDTO(giaSu.getDschude());
		g.setDschude(listchude);
		return g;
	}
	
	public static GiaSu toEntity(GiaSuDTO giaSuDTO) {
		GiaSu u = new GiaSu();
		u.setId(giaSuDTO.getId());
		u.setHoten(giaSuDTO.getHoten());
		u.setSdt(giaSuDTO.getSdt());
		u.setEmail(giaSuDTO.getEmail());
		u.setAvata(giaSuDTO.getAvata());
		u.setGioitinh(giaSuDTO.getGioitinh());
		u.setNgaysinh(giaSuDTO.getNgaysinh());
		u.setQuan(giaSuDTO.getQuan());
		u.setDiachi(giaSuDTO.getDiachi());
		u.setQuequan(giaSuDTO.getQuequan());
		u.setNghenghiep(giaSuDTO.getNghenghiep());
		u.setTruong(giaSuDTO.getTruong());
		u.setGioithieu(giaSuDTO.getGioithieu());
		u.setKinhnghiem(giaSuDTO.getKinhnghiem());
		u.setThanhtich(giaSuDTO.getThanhtich());
		u.setCccd(giaSuDTO.getCccd());
		u.setKhuvucday(giaSuDTO.getKhuvucday());
		if(giaSuDTO.getTrangthai() != null) {
			u.setTrangthai(TrangThaiUser.valueOf(giaSuDTO.getTrangthai()));
		}
		List<ChuDe> listchude = ChuDeMapper.toListEntity(giaSuDTO.getDschude());
		u.setDschude(listchude);
		return u;
	}
	
	public static GiaSu update(GiaSu giaSu, GiaSuDTO giaSuDTO) {
		giaSu.setHoten(giaSuDTO.getHoten());
		giaSu.setSdt(giaSuDTO.getSdt());
		giaSu.setAvata(giaSuDTO.getAvata());
		giaSu.setGioitinh(giaSuDTO.getGioitinh());
		giaSu.setNgaysinh(giaSuDTO.getNgaysinh());
		giaSu.setQuan(giaSuDTO.getQuan());
		giaSu.setDiachi(giaSuDTO.getDiachi());
		giaSu.setQuequan(giaSuDTO.getQuequan());
		giaSu.setNghenghiep(giaSuDTO.getNghenghiep());
		giaSu.setTruong(giaSuDTO.getTruong());
		giaSu.setGioithieu(giaSuDTO.getGioithieu());
		giaSu.setKinhnghiem(giaSuDTO.getKinhnghiem());
		giaSu.setThanhtich(giaSuDTO.getThanhtich());
		giaSu.setCccd(giaSuDTO.getCccd());
		giaSu.setKhuvucday(giaSuDTO.getKhuvucday());
		List<ChuDe> listchude = ChuDeMapper.toListEntity(giaSuDTO.getDschude());
		giaSu.setDschude(listchude);
		return giaSu;
	}
	
	public static List<GiaSuDTO> toListDTO(List<GiaSu> list){
		if(list == null)
			return null;
		return list.stream().map(g -> toDTO(g)).collect(Collectors.toList());
	}
	
	public static List<GiaSu> toListEntity(List<GiaSuDTO> list){
		if(list == null)
			return null;
		return list.stream().map(g -> toEntity(g)).collect(Collectors.toList());
	}
	
	public static Page<GiaSuDTO> toPageDTO(Page<GiaSu> page){
		if(page == null)
			return null;
		List<GiaSuDTO> pageDTO = page.stream().map(g -> toDTO(g)).collect(Collectors.toList());
		return new PageImpl<>(pageDTO, page.getPageable(), page.getSize());
	}
}
