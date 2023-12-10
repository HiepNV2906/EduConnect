package demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.response.BaseResponse;
import demo.service.ThongKeService;

@RestController
@RequestMapping("/api/thongke")
@PreAuthorize("hasAuthority('MANAGER')")
public class ThongKeController {

	@Autowired
	ThongKeService thongKeService;
	
	@GetMapping(value = "/doanhthu", produces = "application/json")
	public BaseResponse<?> thongKeDoanhThu(
			@RequestParam(name = "from", required = false) String from,
			@RequestParam(name = "to", required = false) String to){
		try {
			List<Object[]> data = thongKeService.thongKeDoanhThuTheoThang(from, to);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/giasuvahocvien", produces = "application/json")
	public BaseResponse<?> thongKeGiaSuVaHocVien(
			@RequestParam(name = "from", required = false) String from,
			@RequestParam(name = "to", required = false) String to){
		try {
			List<Object[]> data = thongKeService.thongKeGiaSuVaHocVienMoiTheoThang(from, to);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/lop", produces = "application/json")
	public BaseResponse<?> thongKeLop(
			@RequestParam(name = "from", required = false) String from,
			@RequestParam(name = "to", required = false) String to){
		try {
			List<Object[]> data = thongKeService.thongKeLopTheoThang(from, to);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/theoquan", produces = "application/json")
	public BaseResponse<?> phanBoGiaSuTheoQuan(
			@RequestParam(name = "from", required = false) String from,
			@RequestParam(name = "to", required = false) String to){
		try {
			List<Object[]> data = thongKeService.thongKePhanBoTheoQuan(from, to);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/giasutheohocphi", produces = "application/json")
	public BaseResponse<?> phanBoLopTheoHocPhi(
			@RequestParam(name = "from", required = false) String from,
			@RequestParam(name = "to", required = false) String to){
		try {
			List<Object[]> data = thongKeService.thongKePhanBoLopTheoHocPhi(from, to);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
}
