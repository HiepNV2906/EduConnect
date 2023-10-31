package demo.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	protected Long id;
	protected String hoten;
	protected String sdt;
	protected String email;
	protected String avata;
	protected String gioitinh;
	protected Date ngaysinh;
	protected List<ThongBaoDTO> dsthongbao;

}
