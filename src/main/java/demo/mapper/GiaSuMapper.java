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
import demo.request.RegisterGiaSuRequest;

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
		g.setNgaytao(giaSu.getNgaytao());
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
		u.setNgaytao(giaSuDTO.getNgaytao());
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
	
	public static GiaSu update(GiaSu giaSu, RegisterGiaSuRequest registerGiaSuRequest) {
		giaSu.setHoten(registerGiaSuRequest.getHoten());
		giaSu.setSdt(registerGiaSuRequest.getSdt());
		giaSu.setGioitinh(registerGiaSuRequest.getGioitinh());
		giaSu.setNgaysinh(registerGiaSuRequest.getNgaysinh());
		giaSu.setQuan(registerGiaSuRequest.getQuan());
		giaSu.setDiachi(registerGiaSuRequest.getDiachi());
		giaSu.setQuequan(registerGiaSuRequest.getQuequan());
		giaSu.setNghenghiep(registerGiaSuRequest.getNghenghiep());
		giaSu.setTruong(registerGiaSuRequest.getTruong());
		giaSu.setGioithieu(registerGiaSuRequest.getGioithieu());
		giaSu.setKinhnghiem(registerGiaSuRequest.getKinhnghiem());
		giaSu.setThanhtich(registerGiaSuRequest.getThanhtich());
		giaSu.setKhuvucday(registerGiaSuRequest.getKhuvucday());
		List<ChuDe> listchude = ChuDeMapper.toListEntity(registerGiaSuRequest.getDschude());
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
