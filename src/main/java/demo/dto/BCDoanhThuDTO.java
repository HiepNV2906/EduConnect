package demo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BCDoanhThuDTO {
	private Long mags;
	private String hotengs;
	private Long malop;
	private String chudeday;
	private Long hocphi;
	private Date ngaythanhtoan;
	private Long phinhanlop;
}
