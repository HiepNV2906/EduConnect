package demo.dto;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThongBaoDTO {
	private Long id;
	private String tieude;
	private String noidung;
	private Date ngay;
	private String trangthaithongbao;
	private Long userid;
	
}
