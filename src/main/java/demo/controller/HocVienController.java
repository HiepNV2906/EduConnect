package demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.Enum.TrangThaiUser;
import demo.dto.HocVienDTO;
import demo.entity.HocVien;
import demo.mapper.HocVienMapper;
import demo.request.Status;
import demo.request.RegisterHocVienRequest;
import demo.response.BaseResponse;
import demo.service.HocVienService;

@RestController
@RequestMapping("/api/hocvien")
public class HocVienController {
	private final int sizeOfPage = 10;
	
	@Autowired
	HocVienService hocVienService;
	
	@PostMapping(value = "", produces = "application/json")
	public BaseResponse<?> addHocVien(
			@RequestBody RegisterHocVienRequest registerHocVienRequest){
		try {
			HocVien h = hocVienService.addHocVien(registerHocVienRequest);
			HocVienDTO data = HocVienMapper.toDTO(h);
			return new BaseResponse<>("Successful!", data, HttpStatus.CREATED);
		} catch(Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = "/changeStatus/{id}", produces = "application/json")
	public BaseResponse<?> changStatusHocVien(
			@RequestBody Status changeStatus,
			@PathVariable("id") Long id){
		try {
			HocVien h = hocVienService.updateHocVien(id, TrangThaiUser.valueOf(changeStatus.getStatus()));
			HocVienDTO data = HocVienMapper.toDTO(h);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch(Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> updateHocVien(
			@RequestBody HocVienDTO hocVienDTO,
			@PathVariable("id") Long id){
		try {
			hocVienDTO.setId(id);
			HocVien h = hocVienService.updateHocVien(hocVienDTO);
			HocVienDTO data = HocVienMapper.toDTO(h);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch(Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> deleteHocVien(
			@PathVariable("id") Long id){
		try {
			hocVienService.deleteHocVien(id);
			return new BaseResponse<>("Successful!", null, HttpStatus.OK);
		} catch(Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> getHocVienById(
			@PathVariable("id") Long id){
		try {
			HocVien h = hocVienService.getHocVienById(id);
			HocVienDTO data = HocVienMapper.toDTO(h);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch(Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "", produces = "application/json")
	public BaseResponse<?> getListHocVien(
			@RequestParam(name="page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<HocVien> pageHocVien = hocVienService.getListHocVien(PageRequest.of(page.orElse(0), sizeOfPage));
				Page<HocVienDTO> data = HocVienMapper.toPageDTO(pageHocVien);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<HocVien> listHocVien = hocVienService.getListHocVien();
				List<HocVienDTO> data = HocVienMapper.toListDTO(listHocVien);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		} catch(Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/search", produces = "application/json")
	public BaseResponse<?> getListHocVienByKey(
			@RequestParam(name="key") String key,
			@RequestParam(name="page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<HocVien> pageHocVien = hocVienService.findByKey(key, PageRequest.of(page.orElse(0), sizeOfPage));
				Page<HocVienDTO> data = HocVienMapper.toPageDTO(pageHocVien);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<HocVien> listHocVien = hocVienService.findByKey(key);
				List<HocVienDTO> data = HocVienMapper.toListDTO(listHocVien);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		} catch(Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
}
