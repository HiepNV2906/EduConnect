package demo.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.dto.BCDoanhThuDTO;
import demo.dto.GiaSuDTO;
import demo.entity.GiaSu;
import demo.entity.ThanhToan;
import demo.mapper.GiaSuMapper;
import demo.mapper.ThanhToanMapper;
import demo.response.BaseResponse;
import demo.service.GiaSuService;
import demo.service.HocVienService;
import demo.service.ThanhToanService;
import demo.service.ThongKeService;

@RestController
@RequestMapping("/api/thongke")
@PreAuthorize("hasAuthority('MANAGER')")
public class ThongKeController {

	@Autowired
	ThongKeService thongKeService;
	@Autowired
	ThanhToanService thanhToanService;
	@Autowired
	GiaSuService giaSuService;
	@Autowired
	HocVienService hocVienService;
	
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
	
	@GetMapping(value = "/xuatbaocao", produces = "application/json")
	public BaseResponse<?> baocaodoanhthu(
			@RequestParam(name = "from", required = false) String from,
			@RequestParam(name = "to", required = false) String to,
			@RequestParam(name = "type", required = false) Integer type){
		try {
			System.out.println(from);
			if(type==1) {
				List<ThanhToan> tt = thanhToanService.getThanhToanFromTo(from, to);
				System.out.println(tt.size());
				List<BCDoanhThuDTO> data = ThanhToanMapper.toListBCDoanhThuDTO(tt);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			if(type==2) {
				List<GiaSu> gs = giaSuService.giasumoi(from, to);
				List<GiaSuDTO> data = GiaSuMapper.toListDTO(gs);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			return new BaseResponse<>("Null", null, HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
}
