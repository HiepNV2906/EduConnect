package demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HocVienDTO extends UserDTO{
	private String quan;
	private String diachi;
	private String cccd;
	private String trangthai;
	private List<LopDTO> dslop;

}
