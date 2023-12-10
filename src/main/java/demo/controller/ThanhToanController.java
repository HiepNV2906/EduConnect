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

import demo.dto.ThanhToanDTO;
import demo.entity.ThanhToan;
import demo.mapper.ThanhToanMapper;
import demo.response.BaseResponse;
import demo.service.ThanhToanService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/thanhtoan")
public class ThanhToanController {

	private final int sizeOfPage = 10;
	
	@Autowired
	ThanhToanService thanhToanService;
	
	@PostMapping(value = "", produces = "application/json")
	public BaseResponse<?> addThanhToan(@RequestBody ThanhToanDTO thanhToanDTO){
		try {
			ThanhToan t = thanhToanService.addThanhToan(thanhToanDTO);
			ThanhToanDTO data = ThanhToanMapper.toDTO(t);
			return new BaseResponse<>("Successful!", data, HttpStatus.CREATED);
		} catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> updateThanhToan(
			@RequestBody ThanhToanDTO thanhToanDTO,
			@PathVariable("id") String id){
		try {
			thanhToanDTO.setId(id);
			ThanhToan t = thanhToanService.updateThanhToan(thanhToanDTO);
			ThanhToanDTO data = ThanhToanMapper.toDTO(t);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> deleteThanhToan(
			@PathVariable("id") String id){
		try {
			thanhToanService.deleteThanhToan(id);
			return new BaseResponse<>("Successful!", null, HttpStatus.OK);
		} catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> getThanhToanById(
			@PathVariable("id") String id){
		try {
			ThanhToan t = thanhToanService.getThanhToanById(id);
			ThanhToanDTO data = ThanhToanMapper.toDTO(t);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = "", produces = "application/json")
	public BaseResponse<?> getListThanhToan(
			@RequestParam(name="page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<ThanhToan> pageThanhToan = thanhToanService.getListThanhToan(PageRequest.of(page.orElse(0), sizeOfPage));
				Page<ThanhToanDTO> data = ThanhToanMapper.toPageDTO(pageThanhToan);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<ThanhToan> listThanhToan = thanhToanService.getListThanhToan();
				List<ThanhToanDTO> data = ThanhToanMapper.toListDTO(listThanhToan);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/ungtuyenid/{utid}", produces = "application/json")
	public BaseResponse<?> getListThanhToanByUngTuyenId(
			@PathVariable("utid") Long utid){
		try {
			ThanhToan listThanhToan = thanhToanService.getListThanhToanByUngTuyenId(utid);
			ThanhToanDTO data = ThanhToanMapper.toDTO(listThanhToan);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/giasu/{giasuid}", produces = "application/json")
	public BaseResponse<?> getListThanhToanByGiaSuId(
			@PathVariable("giasuid") Long giasuid){
		try {
			List<ThanhToan> listThanhToan = thanhToanService.getListThanhToanByGiaSuId(giasuid);
			List<ThanhToanDTO> data = ThanhToanMapper.toListDTO(listThanhToan);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
}
