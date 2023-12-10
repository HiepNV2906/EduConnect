package demo.service;

import java.util.List;

public interface ThongKeService {
	public List<Object[]> thongKeDoanhThuTheoThang(String fromday, String today);
	public List<Object[]> thongKeGiaSuVaHocVienMoiTheoThang(String fromday, String today);
	public List<Object[]> thongKeLopTheoThang(String fromday, String today);
	public List<Object[]> thongKePhanBoTheoQuan(String from, String to);
	public List<Object[]> thongKePhanBoLopTheoHocPhi(String from, String to);
}
