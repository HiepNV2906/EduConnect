package demo.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoiMoiDTO {
	private Long id;
	private Long giasuid;
	private String tengs;
	private Long lopid;
	private String tieudelop;
	private Date ngaymoi;
	private String trangthailoimoi;
	private Long ungtuyenid;
}
