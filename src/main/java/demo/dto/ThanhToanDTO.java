package demo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThanhToanDTO {
	private String id;
	private String nganhang;
	private String magiaodichnganhang;
	private String noidung;
	private Long sotien;
	private Date ngaythanhtoan;
	private Long ungtuyenid;
	
}
