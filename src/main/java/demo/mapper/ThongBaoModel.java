package demo.mapper;

import java.util.Date;


import demo.Enum.TrangThaiThongBao;
import demo.Enum.VaiTro;
import demo.entity.ThongBao;
import demo.entity.User;

public class ThongBaoModel{

	public static ThongBao dangKyThanhCong(User user) {
		String tieude = "Đăng ký thành công";
		String noidung = "Bạn đã đăng ký thông tin và tài khoản thành công. Tài khoản của bạn đang chờ xác thực và phê duyệt.";
		String link = "";
		if(user.getVaitro()==VaiTro.ADMIN) {
			link = "/admin";
		} else if(user.getVaitro()==VaiTro.HOCVIEN) {
			link = "/hocvien";
		} else {
			link = "/giasu";
		}
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao capNhatTaiKhoan(User user) {
		String tieude = "Cập nhật tài khoản";
		String noidung = "Tài khoản của bạn vừa được cập nhật thông tin.";
		String link = "";
		if(user.getVaitro()==VaiTro.ADMIN) {
			link = "/admin";
		} else if(user.getVaitro()==VaiTro.HOCVIEN) {
			link = "/hocvien";
		} else {
			link = "/giasu";
		}
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao pheDuyetTaiKhoan(User user) {
		String tieude = "Xác thực tài khoản thành công";
		String noidung = "Tài khoản của bạn đã được xác thực và phê duyệt thành công.";
		String link = "";
		if(user.getVaitro()==VaiTro.ADMIN) {
			link = "/admin";
		} else if(user.getVaitro()==VaiTro.HOCVIEN) {
			link = "/hocvien";
		} else {
			link = "/giasu";
		}	
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao ycDangKyTaiKhoanGiasu(User user) {
		String tieude = "Gia sư mới";
		String noidung = "Một tài khoản gia sư vừa được tạo và đang chờ xác thực.";
		String link = "/admin/dangkygiasu";
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao ycDangKyTaiKhoanHocVien(User user) {
		String tieude = "Học viên mới";
		String noidung = "Một tài khoản học viên vừa được tạo và đang chờ xác thực.";
		String link = "/admin/danhsachhocvien";
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao dinhChiTaiKhoan(User user) {
		String tieude = "Đình chỉ tài khoản";
		String noidung = "Tài khoản của bạn tạm thời bị đình chỉ do vi phạm quy định của chúng tôi.";
		String link = "";
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao taoLopThanhCong(User user) {
		String tieude = "Gửi yêu cầu tìm gia sư thành công";
		String noidung = "Yêu cầu tạo lớp tìm gia sư mới được tạo. Vui lòng chờ phê duyệt";
		String link = "/hocvien/lopchoduyet";

		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao capNhatLop(User user) {
		String tieude = "Lớp của bạn vừa được cập nhật";
		String noidung = "Thông tin về lớp của bạn vừa được nhật";
		String link = "/hocvien/lopchoduyet";

		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao ycTaoLop(User user) {
		String tieude = "Yêu cầu tạo lớp";
		String noidung = "Một yêu cầu tạo lớp mới được tạo và đang chờ phê duyệt";
		String link = "/admin/yeucautaolop";

		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao pheDuyetLop(User user) {
		String tieude = "Phê duyệt yêu cầu tạo lớp";
		String noidung = "Lớp của bạn đã được phê duyệt và bắt đầu có thể tìm kiếm gia sư.";
		String link = "/hocvien/lopdangtim";
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao nhanLoiMoiUngTuyen(User user, Long idlop) {
		String tieude = "Lời mời ứng tuyển";
		String noidung = "Bạn nhận được một lời mời ứng tuyển nhận lớp từ học viên.";
		String link = "/chitietlop?id="+idlop;
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao ungTuyenLop(User user, boolean invited, Long lopid) {
		String tieude = "Yêu cầu ứng tuyển";
		String noidung = "Lớp của bạn nhận được một yêu cầu ứng tuyển từ gia sư.";
		if(invited) {
			noidung = "Một gia sư mà bạn mời ứng tuyển đã ứng tuyển nhận lớp của bạn.";
		}
		String link = "/hocvien/lop/ungtuyen?id="+lopid;
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao ketQuaUngTuyen(User user, boolean success) {
		String tieude = "Kết quả ứng tuyển";
		String noidung = "Rất tiếc, ứng tuyển nhận lớp của bạn không thành công.";
		if(success) {
			noidung = "Chúc mừng! Ứng tuyển nhận lớp của bạn thành công. Bạn vui lòng liên hệ với học viên "
					+ "thông báo nhận lớp và thanh toán phí nhận lớp trong thời gian quy định.";
		}
		String link = "/giasu/lichsuungtuyen";
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao sapXepGiaSu(User user, boolean fullslot) {
		String tieude = "Sắp xếp gia sư";
		String noidung = "Một lớp đã hết hạn ứng tuyển và cần sắp xếp gia sư nhận lớp.";
		if(fullslot) {
			noidung = "Một lớp có số lượng gia sư ứng tuyển đạt tối đa và có thể sắp xếp gia sư nhận lớp.";
		}
		String link = "/admin/lopcangiao";
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao khongCoUngTuyen(User user) {
		String tieude = "Không có gia sư ứng tuyển";
		String noidung = "Một lớp đã hết hạn ứng tuyển nhưng chưa có gia sư ứng tuyển nhận lớp.";
		String link = "/hocvien/lopdagiao";
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao chonDuocGiaSu(User user) {
		String tieude = "Lớp đã chọn được gia sư";
		String noidung = "Một lớp của bạn đã chọn được gia sư.";
		
		String link = "/hocvien/lopdagiao";
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao ycThanhToan(User user, String date) {
		String tieude = "Yêu cầu thanh toán";
		String noidung = "Vui lòng thanh toán phí nhận lớp trước " + date;
		String link = "/giasu/congno";
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
	
	public static ThongBao lichSuThanhToan(User user) {
		String tieude = "Lịch sử thanh toán";
		String noidung = "Tài khoản " + user.getEmail() + " vừa thanh toán phí nhận lớp";
		String link = "/admin/lichsugiaodich";
		
		
		return new ThongBao(null, tieude, noidung, new Date(), TrangThaiThongBao.CHUAXEM, user, link);
	}
}
