package demo.mapper;

import java.util.Date;

//import java.sql.Time;
//import java.sql.Date;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;

import demo.Enum.TrangThaiThongBao;
import demo.entity.ThongBao;
import demo.entity.User;

public class ThongBaoModel{

	public static ThongBao dangKyThanhCong(User user) {
		String tieude = "Đăng ký thành công";
		String noidung = "Bạn đã đăng ký thông tin và tài khoản thành công. Tài khoản của bạn đang chờ xác thực và phê duyệt.";
		
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user);
	}
	
	public static ThongBao capNhatTaiKhoan(User user) {
		String tieude = "Cập nhật tài khoản";
		String noidung = "Tài khoản của bạn vừa được cập nhật thông tin.";
		
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user);
	}
	
	public static ThongBao pheDuyetTaiKhoan(User user) {
		String tieude = "Xác thực tài khoản thành công";
		String noidung = "Tài khoản của bạn đã được xác thực và phê duyệt thành công.";
		
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user);
	}
	
	public static ThongBao ycDangKyTaiKhoan(User user) {
		String tieude = "Đăng ký tài khoản mới";
		String noidung = "Một tài khoản vừa được tạo và đang chờ xác thực.";
		
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user);
	}
	
	public static ThongBao dinhChiTaiKhoan(User user) {
		String tieude = "Đình chỉ tài khoản";
		String noidung = "Tài khoản của bạn tạm thời bị đình chỉ do vi phạm quy định của chúng tôi.";
		
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user);
	}
	
	public static ThongBao ycTaoLop(User user) {
		String tieude = "Yêu cầu tạo lớp";
		String noidung = "Một yêu cầu tạo lớp mới được tạo và đang chờ phê duyệt";
		
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user);
	}
	
	public static ThongBao pheDuyetLop(User user) {
		String tieude = "Phê duyệt yêu cầu tạo lớp";
		String noidung = "Lớp của bạn đã được phê duyệt và bắt đầu có thể tìm kiếm gia sư.";
		
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user);
	}
	
	public static ThongBao nhanLoiMoiUngTuyen(User user) {
		String tieude = "Lời mời ứng tuyển";
		String noidung = "Bạn nhận được một lời mời ứng tuyển nhận lớp từ học viên.";
		
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user);
	}
	
	public static ThongBao ungTuyenLop(User user, boolean invited) {
		String tieude = "Yêu cầu ứng tuyển";
		String noidung = "Lớp của bạn nhận được một yêu cầu ứng tuyển từ gia sư.";
		if(invited) {
			noidung = "Một gia sư mà bạn mời ứng tuyển đã ứng tuyển nhận lớp của bạn.";
		}
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user);
	}
	
	public static ThongBao ketQuaUngTuyen(User user, boolean success) {
		String tieude = "Kết quả ứng tuyển";
		String noidung = "Rất tiếc, ứng tuyển nhận lớp của bạn không thành công.";
		if(success) {
			noidung = "Chúc mừng! Ứng tuyển nhận lớp của bạn thành công. Bạn vui lòng liên hệ với học viên "
					+ "thông báo nhận lớp và thanh toán phí nhận lớp trong thời gian quy định.";
		}
		
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user);
	}
	
	public static ThongBao sapXepGiaSu(User user, boolean fullslot) {
		String tieude = "Sắp xếp gia sư";
		String noidung = "Một lớp đã hết hạn ứng tuyển và cần sắp xếp gia sư nhận lớp.";
		if(fullslot) {
			noidung = "Một lớp có số lượng gia sư ứng tuyển đạt tối đa và có thể sắp xếp gia sư nhận lớp.";
		}
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user);
	}
	
	public static ThongBao chonDuocGiaSu(User user) {
		String tieude = "Lớp đã chọn được gia sư";
		String noidung = "Một lớp của bạn đã chọn được gia sư.";
		
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user);
	}
	
	public static ThongBao ycThanhToan(User user, String date) {
		String tieude = "Yêu cầu thanh toán";
		String noidung = "Vui lòng thanh toán phí nhận lớp trước " + date;
		
		
		
		return new ThongBao(null, tieude, noidung, new java.util.Date(), TrangThaiThongBao.CHUAXEM, user);
	}
	
	public static ThongBao lichSuThanhToan(User user) {
		String tieude = "Lịch sử thanh toán";
		String noidung = "Tài khoản " + user.getEmail() + " vừa thanh toán phí nhận lớp";
		
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user);
	}
}
