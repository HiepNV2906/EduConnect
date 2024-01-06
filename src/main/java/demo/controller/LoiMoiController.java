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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.Enum.TrangThaiLoiMoi;
import demo.dto.LoiMoiDTO;
import demo.entity.LoiMoi;
import demo.exception.LoiMoiException;
import demo.exception.LopException;
import demo.exception.UserException;
import demo.mapper.LoiMoiMapper;
import demo.request.Status;
import demo.response.BaseResponse;
import demo.service.LoiMoiService;

@RestController
@RequestMapping("/api/loimoi")
public class LoiMoiController {

	private final int sizeOfPage= 10;
	
	@Autowired
	LoiMoiService loiMoiService;
	
	@PreAuthorize("hasAuthority('HOCVIEN')")
	@PostMapping(value = "", produces = "application/json")
	public BaseResponse<?> addLoiMoi(@RequestBody LoiMoiDTO loiMoiDTO){
		try {
			LoiMoi u = loiMoiService.addLoiMoi(loiMoiDTO);
			LoiMoiDTO data = LoiMoiMapper.toDTO(u);
			return new BaseResponse<>("Gửi lời mời gia sư ứng tuyển thành công!", data, HttpStatus.CREATED);
		} catch (LoiMoiException e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		} catch (UserException e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		} catch (LopException e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new BaseResponse<>("Có lỗi xảy ra!", null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('HOCVIEN')")
	@PutMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> updateLoiMoi(
			@RequestBody LoiMoiDTO loiMoiDTO,
			@PathVariable("id") Long id){
		try {
			loiMoiDTO.setId(id);
			LoiMoi u = loiMoiService.updateLoiMoi(loiMoiDTO);
			LoiMoiDTO data = LoiMoiMapper.toDTO(u);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "/changeloimoi/{id}", produces = "application/json")
	public BaseResponse<?> updateTrangThaiLoiMoi(
			@RequestBody Status changeStatus,
			@PathVariable("id") Long id){
		try {
			LoiMoi u = loiMoiService.updateTrangThaiLoiMoi(id, TrangThaiLoiMoi.valueOf(changeStatus.getStatus()));
			LoiMoiDTO data = LoiMoiMapper.toDTO(u);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>("Có lỗi xảy ra!", null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('HOCVIEN')")
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> deleteLoiMoi(
			@PathVariable("id") Long id){
		try {
			loiMoiService.deleteLoiMoi(id);
			return new BaseResponse<>("Xoá thành công!", null, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> getLoiMoiById(
			@PathVariable("id") Long id){
		try {
			LoiMoi u = loiMoiService.getLoiMoiById(id);
			LoiMoiDTO data = LoiMoiMapper.toDTO(u);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/giasu/{id}", produces = "application/json")
	public BaseResponse<?> getLoiMoiByGiaSuId(
			@PathVariable("id") Long id,
			@RequestParam(name = "page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<LoiMoi> pageLoiMoi = loiMoiService.findByGiaSuId(id, PageRequest.of(page.orElse(0), sizeOfPage));
				Page<LoiMoiDTO> data = LoiMoiMapper.toPageDTO(pageLoiMoi);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<LoiMoi> listLoiMoi = loiMoiService.findByGiaSuId(id);
				List<LoiMoiDTO> data = LoiMoiMapper.toListDTO(listLoiMoi);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/lop/{id}", produces = "application/json")
	public BaseResponse<?> getLoiMoiByLopId(
			@PathVariable("id") Long id,
			@RequestParam(name = "page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<LoiMoi> pageLoiMoi = loiMoiService.findByLopId(id, PageRequest.of(page.orElse(0), sizeOfPage));
				Page<LoiMoiDTO> data = LoiMoiMapper.toPageDTO(pageLoiMoi);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<LoiMoi> listLoiMoi = loiMoiService.findByLopId(id);
				List<LoiMoiDTO> data = LoiMoiMapper.toListDTO(listLoiMoi);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('HOCVIEN')")
	@GetMapping(value = "/hocvien/{id}", produces = "application/json")
	public BaseResponse<?> getLoiMoiByHocVienId(
			@PathVariable("id") Long id){
		try {
			List<LoiMoi> listLoiMoi = loiMoiService.findByHocVienId(id);
			List<LoiMoiDTO> data = LoiMoiMapper.toListDTO(listLoiMoi);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
}
