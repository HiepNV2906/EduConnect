package demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiaSuDTO extends UserDTO{
	private String quan;
	private String diachi;
	private String quequan;
	private String nghenghiep;
	private String truong;
	private String gioithieu;
	private String kinhnghiem;
	private String thanhtich;
	private String cccd;
	private String khuvucday;
	private String trangthai;
	private List<UngTuyenDTO> dsungtuyen;
	private List<ChuDeDTO> dschude;
	
}
