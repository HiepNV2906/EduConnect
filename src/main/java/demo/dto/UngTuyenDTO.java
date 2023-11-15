package demo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UngTuyenDTO {
	private Long id;
	private Long giasuid;
	private String tengs;
	private Long lopid;
	private LoiMoiDTO loimoi;
	private Date ngayungtuyen;
	private Date hanthanhtoan;
	private String trangthaiungtuyen;
	private String trangthaicongno;
	private ThanhToanDTO thanhtoan;
	
}
