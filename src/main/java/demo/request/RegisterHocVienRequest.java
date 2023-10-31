package demo.request;

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
	private String cccd;
	
	public HocVien toHocVienEntity() {
		HocVien u = new HocVien();
		u.setId(id);
		u.setHoten(hoten);
		u.setSdt(sdt);
		u.setEmail(email);
		u.setPassword(password);
		u.setAvata(avata);
		u.setGioitinh(gioitinh);
		u.setNgaysinh(ngaysinh);
		u.setQuan(quan);
		u.setDiachi(diachi);
		u.setCccd(cccd);
		return u;
	}
}
