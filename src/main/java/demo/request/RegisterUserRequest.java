package demo.request;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {
	protected Long id;
	protected String hoten;
	protected String sdt;
	protected String email;
	protected String password;
	protected MultipartFile avata;
	protected String gioitinh;
	protected Date ngaysinh;
	
	public User toUserEntity() {
		User u = new User();
		u.setId(id);
		u.setHoten(hoten);
		u.setSdt(sdt);
		u.setEmail(email);
		u.setPassword(password);
		u.setGioitinh(gioitinh);
		u.setNgaysinh(ngaysinh);
		return u;
	}
}
