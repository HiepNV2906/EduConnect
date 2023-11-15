package demo.request;

import org.springframework.web.multipart.MultipartFile;

import demo.Enum.TrangThaiUser;
import demo.entity.HocVien;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterHocVienRequest extends RegisterUserRequest{
	private String quan;
	private String diachi;
	private MultipartFile cccd;
	private String trangthai;
	
	public HocVien toHocVienEntity() {
		HocVien u = new HocVien();
		u.setId(id);
		u.setHoten(hoten);
		u.setSdt(sdt);
		u.setEmail(email);
		u.setPassword(password);
		u.setGioitinh(gioitinh);
		u.setNgaysinh(ngaysinh);
		u.setQuan(quan);
		u.setDiachi(diachi);
		if(trangthai!=null) {
			u.setTrangthai(TrangThaiUser.valueOf(trangthai));
		}
		return u;
	}
}
