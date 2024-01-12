package demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.Enum.TrangThaiLop;
import demo.Enum.TrangThaiUngTuyen;
import demo.dto.LopDTO;
import demo.entity.Lop;
import demo.exception.UserException;
import demo.mapper.LopMapper;
import demo.request.Status;
import demo.response.BaseResponse;
import demo.service.LopService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/lop")
public class LopController {

	private final int sizeOfPage = 10;
	
	@Autowired
	LopService lopService;
	
	@PreAuthorize("hasAuthority('HOCVIEN')")
	@PostMapping(value = "", produces = "application/json")
	public BaseResponse<?> addLop(@RequestBody LopDTO lopDTO){
		try {
			Lop l = lopService.addLop(lopDTO);
			LopDTO data = LopMapper.toDTO(l);
			return new BaseResponse<>("Gửi yêu cầu tạo lớp thành công!", data, HttpStatus.CREATED);
		} catch (UserException e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new BaseResponse<>("Có lỗi xảy ra!", null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('HOCVIEN')")
	@PutMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> updateLop(
			@RequestBody LopDTO lopDTO,
			@PathVariable("id") Long id){
		try {
			lopDTO.setId(id);
			Lop l = lopService.updateLop(lopDTO);
			LopDTO data = LopMapper.toDTO(l);
			return new BaseResponse<>("Cập nhật thông tin lớp thành công!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>("Có lỗi xảy ra!", null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "/changeStatus/{id}", produces = "application/json")
	public BaseResponse<?> updateTrangThaiLop(
			@RequestBody Status changeStatus,
			@PathVariable("id") Long id){
		try {
			Lop l = lopService.updateTrangThaiLop(id, TrangThaiLop.valueOf(changeStatus.getStatus()));
			LopDTO data = LopMapper.toDTO(l);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> deleteLop(
			@PathVariable("id") Long id){
		try {
			lopService.deleteLop(id);
			return new BaseResponse<>("Xoá thành công!", null, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>("Có lỗi xảy ra!", null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> getLopById(
			@PathVariable("id") Long id){
		try {
			Lop l = lopService.getLopById(id);
			LopDTO data = LopMapper.toDTO(l);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "", produces = "application/json")
	public BaseResponse<?> getListLop(
			@RequestParam(name = "page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<Lop> pageLop = lopService.getListLop(PageRequest.of(page.orElse(0), sizeOfPage));
				Page<LopDTO> data = LopMapper.toPageDTO(pageLop);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<Lop> listLop = lopService.getListLop();
				List<LopDTO> data = LopMapper.toListDTO(listLop);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/topnew", produces = "application/json")
	public BaseResponse<?> getListLop(){
		try {
			List<Lop> listLop = lopService.getTopNewListLop();
			List<LopDTO> data = LopMapper.toListDTO(listLop);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/search", produces = "application/json")
	public BaseResponse<?> getListLopByKey(
			@RequestParam(name = "key") String key,
			@RequestParam(name = "trangthai") String trangthai,
			@RequestParam(name = "page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<Lop> pageLop = lopService.findBykey(key, TrangThaiLop.valueOf(trangthai), PageRequest.of(page.orElse(0), sizeOfPage));
				Page<LopDTO> data = LopMapper.toPageDTO(pageLop);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<Lop> listLop = lopService.findByKey(key, TrangThaiLop.valueOf(trangthai));
				List<LopDTO> data = LopMapper.toListDTO(listLop);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/filter", produces = "application/json")
	public BaseResponse<?> getListLopByFilter(
			@RequestParam(name = "key", defaultValue = "", required = false) String key,
			@RequestParam(name = "quan", defaultValue = "", required = false) String quan,
			@RequestParam(name = "hocphimin", defaultValue = "0", required = false) Long hocphimin,
			@RequestParam(name = "hocphimax", defaultValue = "1000000000", required = false) Long hocphimax,
			@RequestParam(name = "hinhthuc", defaultValue = "", required = false) String hinhthuc,
			@RequestParam(name = "mon", defaultValue = "", required = false) String mon,
			@RequestParam(name = "trinhdo", defaultValue = "", required = false) String trinhdo,
			@RequestParam(name = "page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<Lop> pageLop = lopService.findByFilter(key, quan, hinhthuc, hocphimin, hocphimax, mon,
						trinhdo, PageRequest.of(page.orElse(0), sizeOfPage));
				Page<LopDTO> data = LopMapper.toPageDTO(pageLop);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<Lop> listLop = lopService.findByFilter(key, quan, hinhthuc, hocphimin, hocphimax, mon,
						trinhdo);
				List<LopDTO> data = LopMapper.toListDTO(listLop);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/trangthai", produces = "application/json")
	public BaseResponse<?> getListLopByTrangThai(
			@RequestParam(name = "status", defaultValue = "") String status,
			@RequestParam(name = "page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<Lop> pageLop = lopService.findByTrangThai(TrangThaiLop.valueOf(
						status), PageRequest.of(page.orElse(0), sizeOfPage));
				Page<LopDTO> data = LopMapper.toPageDTO(pageLop);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<Lop> listLop = lopService.findByTrangThai(TrangThaiLop.valueOf(status));
				List<LopDTO> data = LopMapper.toListDTO(listLop);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('HOCVIEN')")
	@GetMapping(value = "/hocvien", produces = "application/json")
	public BaseResponse<?> getListLopByHocVienAndTrangThai(
			@RequestParam(name = "hocvienid", defaultValue = "") Long hocvienid,
			@RequestParam(name = "status", defaultValue = "") String status){
		try {
			List<Lop> listLop = lopService.findByHocVienAndTrangThai(hocvienid, TrangThaiLop.valueOf(status));
			List<LopDTO> data = LopMapper.toListDTO(listLop);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('HOCVIEN')")
	@GetMapping(value = "/hocvien/ketthuc", produces = "application/json")
	public BaseResponse<?> getListLopByHocVienAndKetThuc(
			@RequestParam(name = "hocvienid", defaultValue = "") Long hocvienid){
		try {
			List<Lop> listLop = lopService.findByHocVienAndKetThuc(hocvienid);
			List<LopDTO> data = LopMapper.toListDTO(listLop);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('GIASU')")
	@GetMapping(value = "/giasu", produces = "application/json")
	public BaseResponse<?> getListLopByGiasuAndUngTuyenThanhcong(
			@RequestParam(name = "giasuid", defaultValue = "") Long giasuid,
			@RequestParam(name = "status", defaultValue = "") String status){
		try {
			List<Lop> listLop = lopService.findByGiaSuAndTrangThaiUngTuyen(giasuid, TrangThaiUngTuyen.valueOf(status));
			List<LopDTO> data = LopMapper.toListDTO(listLop);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/lienquan/{id}", produces = "application/json")
	public BaseResponse<?> getListLopLienQuan(
			@PathVariable("id") Long id){
		try {
			List<Lop> listLop = lopService.loplienquan(id);
			List<LopDTO> data = LopMapper.toListDTO(listLop);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
}
