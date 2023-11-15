package demo.config;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.Enum.TrangThaiLop;
import demo.Enum.VaiTro;
import demo.entity.Lop;
import demo.entity.User;
import demo.mapper.ThongBaoModel;
import demo.repository.LopRepository;
import demo.repository.ThongBaoRepository;
import demo.repository.UserRepository;

@Component
public class ScheduleTask {
	
	@Autowired
	LopRepository lopRepository;
	@Autowired
	ThongBaoRepository thongBaoRepository;
	@Autowired
	UserRepository userRepository;
	
	@Scheduled(cron = "0 0 0 * * *")
    public void chuyenTrangThaiLopDinhKy() {
		Date now = new Date();
		List<Lop> list = lopRepository.findByTrangthailopOrderByHanungtuyenAsc(TrangThaiLop.DANGTIM);
		for (Lop lop : list) {
			if(lop.getHanungtuyen().before(now)) {
				lop.setTrangthailop(TrangThaiLop.CANGIAO);
				lopRepository.save(lop);
				List<User> users = userRepository.findByVaitro(VaiTro.ADMIN);
				for (User user : users) {
					thongBaoRepository.save(ThongBaoModel.sapXepGiaSu(user, false));
				}
				System.out.println("***");
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
	
	
}
