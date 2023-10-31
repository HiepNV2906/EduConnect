package demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.dto.ChuDeDTO;
import demo.entity.ChuDe;
import demo.mapper.ChuDeMapper;
import demo.response.BaseResponse;
import demo.service.ChuDeService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/chude")
public class ChuDeController {

	@Autowired
	ChuDeService chuDeService;
	
	@PostMapping(value = "", produces = "application/json")
	public BaseResponse<?> addChuDe(
			@RequestBody ChuDeDTO chuDeDTO){
		try {
			ChuDe c = chuDeService.addChuDe(chuDeDTO);
			ChuDeDTO data = ChuDeMapper.toDTO(c);
			return new BaseResponse<>("Successful!", data, HttpStatus.CREATED);
		} catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> updateChuDe(
			@RequestBody ChuDeDTO chuDeDTO,
			@PathVariable("id") Long id){
		try {
			chuDeDTO.setId(id);
			ChuDe c = chuDeService.updateChuDe(chuDeDTO);
			ChuDeDTO data = ChuDeMapper.toDTO(c);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> deleteChuDe(
			@PathVariable("id") Long id){
		try {
			chuDeService.deleteChuDe(id);
			return new BaseResponse<>("Successful!", null, HttpStatus.OK);
		} catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> getChuDeById(
			@PathVariable("id") Long id){
		try {
			ChuDe c = chuDeService.getChuDeById(id);
			ChuDeDTO data = ChuDeMapper.toDTO(c);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "", produces = "application/json")
	public BaseResponse<?> getListChuDe(){
		try {
			List<ChuDe> c = chuDeService.getListChuDe();
			List<ChuDeDTO> data = ChuDeMapper.toListDTO(c);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
}
