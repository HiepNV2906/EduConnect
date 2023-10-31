package demo.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LopDTO {
	private Long id;
	private String tieude;
	private String quan;
	private String diachi;
	private Integer sobuoi;
	private Float sogio;
	private Long hocphi;
	private Long phiungtuyen;
	private Integer sohs;
	private String gioitinhhs;
	private String motahs;
	private String nghenghiepgs;
	private String gioitinhgs;
	private String truonggs;
	private String yeucaukhac;
	private Date ngaytao;
	private Date hanungtuyen;
	private String hinhthuc;
	private String trangthailop;
	private List<LichTrongDTO> dslichtrong;
	private List<UngTuyenDTO> dsungtuyen;
	private Long hocvienid;
	private Long chudeid;
	
}
