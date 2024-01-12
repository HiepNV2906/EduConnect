package demo.config;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.Enum.TrangThaiCongNo;
import demo.Enum.TrangThaiLoiMoi;
import demo.Enum.TrangThaiLop;
import demo.Enum.VaiTro;
import demo.entity.LoiMoi;
import demo.entity.Lop;
import demo.entity.UngTuyen;
import demo.entity.User;
import demo.mapper.ThongBaoModel;
import demo.repository.LoiMoiRepository;
import demo.repository.LopRepository;
import demo.repository.ThongBaoRepository;
import demo.repository.UngTuyenRepository;
import demo.repository.UserRepository;

@Component
public class ScheduleTask {
	
	@Autowired
	LopRepository lopRepository;
	@Autowired
	LoiMoiRepository loiMoiRepository;
	@Autowired
	ThongBaoRepository thongBaoRepository;
	@Autowired
	UngTuyenRepository ungTuyenRepository;
	@Autowired
	UserRepository userRepository;
	
	@Scheduled(cron = "0 0 0 * * *")
    public void nhacNhoThanhToan() {
		Date now = new Date();
		List<UngTuyen> list = ungTuyenRepository.findByTrangthaicongno(TrangThaiCongNo.CHUATHANHTOAN);
		for (UngTuyen ungTuyen : list) {
			LocalDate hanthanhtoan = LocalDate.parse(ungTuyen.getHanthanhtoan().toString());
			LocalDate today = LocalDate.parse(now.toString());
			if(today.isEqual(hanthanhtoan.minusDays(7))) {
				thongBaoRepository.save(ThongBaoModel.ycThanhToan(ungTuyen.getGiasu(), ungTuyen.getHanthanhtoan()));
			}
		}
    }
	
	@Scheduled(cron = "0 0 0 * * *")
    public void chuyenTrangThaiLopDinhKy() {
		Date now = new Date();
		List<Lop> list = lopRepository.findByTrangthailopOrderByHanungtuyenAsc(TrangThaiLop.DANGTIM);
		for (Lop lop : list) {
			if(lop.getHanungtuyen().before(now)) {
				lop.setTrangthailop(TrangThaiLop.CANGIAO);
				lopRepository.save(lop);
				List<UngTuyen> x = ungTuyenRepository.findByLopId(lop.getId());
				if(x.size()>0) {
					List<User> users = userRepository.findByVaitro(VaiTro.ADMIN);
					for (User user : users) {
						thongBaoRepository.save(ThongBaoModel.sapXepGiaSu(user, false));
					}
				} else {
					thongBaoRepository.save(ThongBaoModel.khongCoUngTuyen(lop.getHocvien()));
				}
			} else {
				break;
			}
		}
    }
	
	@Scheduled(cron = "0 0 0 * * *")
    public void chuyenTrangThaiLoiMoiDinhKy() {
		Date now = new Date();
		List<LoiMoi> list = loiMoiRepository.findByTrangthailoimoi(TrangThaiLoiMoi.CHO);
		for (LoiMoi loimoi : list) {
			if(loimoi.getLop().getHanungtuyen().before(now)) {
				if(loimoi.getTrangthailoimoi()==TrangThaiLoiMoi.CHO) {
					loimoi.setTrangthailoimoi(TrangThaiLoiMoi.TUCHOI);
					loiMoiRepository.save(loimoi);
				}
			} else {
				break;
			}
		}
    }
	
	@Scheduled(cron = "0 0 0 1 * *")
	public void xoaThongBaoDinhKy() {
		LocalDate currentDate = LocalDate.now();
		LocalDate threeMonthBefore = currentDate.minusMonths(3);
		Date date = Date.from(threeMonthBefore.atStartOfDay(ZoneId.systemDefault()).toInstant());
		thongBaoRepository.deleteByNgayBefore(date);
		System.out.println(date);
	}
	
//	@Scheduled(fixedRate = 5000)
//    public void scheduleTaskWithFixedRate() {
//		List<Lop> list = lopRepository.findByTrangthailopOrderByHanungtuyenAsc(TrangThaiLop.DANGTIM);
//		for (Lop lop : list) {
//			List<UngTuyen> x = ungTuyenRepository.findByLopId(lop.getId());
//			System.out.println(x.size());
//		}
//    }
}
