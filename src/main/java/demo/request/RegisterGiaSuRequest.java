package demo.request;

import java.util.List;

import demo.Enum.TrangThaiUser;
import demo.dto.ChuDeDTO;
import demo.entity.GiaSu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterGiaSuRequest extends RegisterUserRequest{
	private String quan;
	private String diachi;
	private String quequan;
	private String nghenghiep;
	private String truong;
	private String gioithieu;
	private String kinhnghiem;
	private String thanhtich;
	private String cccd;
	private String khuvucday;
	private String trangthai;
	private List<ChuDeDTO> dschude;
	
	public GiaSu toGiaSuEntity() {
		GiaSu u = new GiaSu();
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
		u.setQuequan(quequan);
		u.setNghenghiep(nghenghiep);
		u.setTruong(truong);
		u.setGioithieu(gioithieu);
		u.setKinhnghiem(kinhnghiem);
		u.setThanhtich(thanhtich);
		u.setCccd(cccd);
		u.setKhuvucday(khuvucday);
		return u;
	}
}
