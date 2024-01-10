package demo.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.Enum.TrangThaiLop;
import demo.Enum.TrangThaiUser;
import demo.repository.GiaSuRepository;
import demo.repository.HocVienRepository;
import demo.repository.LopRepository;
import demo.repository.ThanhToanRepository;
import demo.service.ThongKeService;

@Service
public class ThongKeServiceImpl implements ThongKeService{

	@Autowired
	ThanhToanRepository thanhToanRepository;
	@Autowired
	GiaSuRepository giaSuRepository;
	@Autowired
	HocVienRepository hocVienRepository;
	@Autowired
	LopRepository lopRepository;
	
	@Override
	public List<Object[]> thongKeDoanhThuTheoThang(String fromday, String today) {
		LocalDate to;
		LocalDate from;
		if(fromday==null || today==null) {
			to = LocalDate.now();
			from = to.minusMonths(11).withDayOfMonth(1);
		} else {
			to = LocalDate.parse(today);
			from = LocalDate.parse(fromday);
		}
		List<Object[]> list = thanhToanRepository.thongKeDoanhThuTheoThang(from.toString(), to.toString());
		System.out.println("------------------Doanh thu----------------------");
		for(int i=0 ; i<list.size() ; i++) {
			System.out.println(list.get(i)[0] + "-" + list.get(i)[1]);
		}
		int i=0, size = list.size();
		List<Object[]> result = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
		while(!from.isAfter(to)) {
			String label = formatter.format(from);
			if(label.length()<7) {
				
			}
			if(i<size && label.equals(list.get(i)[0])) {
				result.add(list.get(i));
				i++;
			} else {
				Object[] o = new Object[2];
				o[0] = label;
				o[1] = 0;
				result.add(o);
			}
			from = from.plusMonths(1);
		}
		return result;
	}

	@Override
	public List<Object[]> thongKeGiaSuVaHocVienMoiTheoThang(String fromday, String today) {
		LocalDate to;
		LocalDate from;
		if(fromday==null || today==null) {
			to = LocalDate.now();
			from = to.minusMonths(11).withDayOfMonth(1);
		} else {
			to = LocalDate.parse(today);
			from = LocalDate.parse(fromday);
		}
		List<Object[]> giasu = giaSuRepository.thongkegiasumoitheothang(from.toString(), to.toString(), TrangThaiUser.DAPHEDUYET.toString());
		List<Object[]> hocvien = hocVienRepository.thongkehocvienmoitheothang(from.toString(), to.toString(), TrangThaiUser.DAPHEDUYET.toString());
		System.out.println("------------------Gia sư mới----------------------");
		for(int i=0 ; i<giasu.size() ; i++) {
			System.out.println(giasu.get(i)[0] + "-" + giasu.get(i)[1]);
		}
		System.out.println("------------------Học viên mới----------------------");
		for(int i=0 ; i<hocvien.size() ; i++) {
			System.out.println(hocvien.get(i)[0] + "-" + hocvien.get(i)[1]);
		}
		int i=0, sizegiasu = giasu.size();
		int j=0, sizehocvien = hocvien.size();
		List<Object[]> result = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
		while(!from.isAfter(to)) {
			String label = formatter.format(from);
			Long gsval = Long.valueOf("0"), hvval = Long.valueOf("0");
			System.out.println(label + "-" + giasu.get(i)[0] + "-" + hocvien.get(j)[0]);
			if(i<sizegiasu && label.equals(giasu.get(i)[0])) {
				gsval = (Long) giasu.get(i)[1];
				i++;
			}
			if(j<sizehocvien && label.equals(hocvien.get(j)[0])) {
				hvval = (Long) hocvien.get(j)[1];
				j++;
			}
			Object[] o = new Object[3];
			o[0] = label;
			o[1] = gsval;
			o[2] = hvval;
			
			result.add(o);
			from = from.plusMonths(1);
		}
		return result;
	}

	@Override
	public List<Object[]> thongKeLopTheoThang(String fromday, String today) {
		LocalDate to;
		LocalDate from;
		if(fromday==null || today==null) {
			to = LocalDate.now();
			from = to.minusMonths(11).withDayOfMonth(1);
		} else {
			to = LocalDate.parse(today);
			from = LocalDate.parse(fromday);
		}
		List<Object[]> lopmoi = lopRepository.thongKeLopMoiTheoThang(from.toString(), to.toString(), TrangThaiLop.CHOPHEDUYET.toString());
		List<Object[]> lopdagiao = lopRepository.thongKeLopDaGiaoTheoThang(from.toString(), to.toString(), TrangThaiLop.DAGIAO.toString());
		System.out.println("------------------Lớp mới----------------------");
		for(int i=0 ; i<lopmoi.size() ; i++) {
			System.out.println(lopmoi.get(i)[0] + "-" + lopmoi.get(i)[1]);
		}
		System.out.println("------------------Lớp đã giao----------------------");
		for(int i=0 ; i<lopdagiao.size() ; i++) {
			System.out.println(lopdagiao.get(i)[0] + "-" + lopdagiao.get(i)[1]);
		}
		int i=0, sizelopmoi = lopmoi.size();
		int j=0, sizelopdagiao = lopdagiao.size();
		List<Object[]> result = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
		while(!from.isAfter(to)) {
			String label = formatter.format(from);
			Long lopmoival = Long.valueOf("0"), lopdagiaoval = Long.valueOf("0");
			if(i<sizelopmoi && label.equals(lopmoi.get(i)[0])) {
				lopmoival = (Long) lopmoi.get(i)[1];
				i++;
			}
			if(j<sizelopdagiao && label.equals(lopdagiao.get(j)[0])) {
				lopdagiaoval = (Long) lopdagiao.get(j)[1];
				j++;
			}
			Object[] o = new Object[3];
			o[0] = label;
			o[1] = lopmoival;
			o[2] = lopdagiaoval;
			result.add(o);
			from = from.plusMonths(1);
		}
		return result;
	}

	@Override
	public List<Object[]> thongKePhanBoTheoQuan(String from, String to) {
		List<Object[]> lop = lopRepository.phanBoGiaSuTheoQuanHuyen(from, to, TrangThaiLop.CHOPHEDUYET.toString());
		List<Object[]> giasu = giaSuRepository.thongkephanbogiasutheoquan(from, to, TrangThaiUser.DAPHEDUYET.toString());
		int i=0, sizelop = lop.size();
		int j=0, sizegiasu = giasu.size();
		List<Object[]> result = new ArrayList<>();
		String[] quan = {"Ba Đình", "Ba Vì", "Bắc Từ Liêm", "Cầu Giấy", "Chương Mỹ", "Đan Phượng", "Đông Anh", 
						"Đống Đa", "Gia Lâm", "Hà Đông", "Hai Bà Trưng", "Hoài Đức", "Hoàn Kiếm", "Hoàng Mai", 
						"Long Biên", "Mê Linh", "Mỹ Đức", "Nam Từ Liêm", "Phú Xuyên", "Phúc Thọ", "Quốc Oai", 
						"Sóc Sơn", "Sơn Tây", "Tây Hồ", "Thạch Thất", "Thanh Oai", "Thanh Trì", "Thanh Xuân", 
						"Thường Tín", "Ứng Hoà"};
		for (int k=0 ; k<quan.length ; k++) {
			Long lopval = Long.valueOf("0"), giasuval = Long.valueOf("0");
			if(i<sizelop && quan[k].equals(lop.get(i)[0])) {
				lopval = (Long) lop.get(i)[1];
				i++;
			}
			if(j<sizegiasu && quan[k].equals(giasu.get(j)[0])) {
				giasuval = (Long) giasu.get(j)[1];
				j++;
			}
			Object[] o = new Object[3];
			o[0] = quan[k];
			o[1] = lopval;
			o[2] = giasuval;
			result.add(o);
		}
		return result;
	}

	@Override
	public List<Object[]> thongKePhanBoLopTheoHocPhi(String from, String to) {
		List<Object[]> lop = lopRepository.phanBoGiaSuTheoHocPhi(from, to, TrangThaiLop.CHOPHEDUYET.toString());
		return lop;
	}

}
