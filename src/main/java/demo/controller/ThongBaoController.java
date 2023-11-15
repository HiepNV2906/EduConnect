package demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.dto.ThongBaoDTO;
import demo.entity.ThongBao;
import demo.mapper.ThongBaoMapper;
import demo.response.BaseResponse;
import demo.service.ThongBaoService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/thongbao")
public class ThongBaoController {

	@Autowired
	ThongBaoService thongBaoService;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(value = "", produces = "application/json")
	public BaseResponse<?> addThongBao(@RequestBody ThongBaoDTO thongBaoDTO){
		try {
			ThongBao t = thongBaoService.addThongBao(thongBaoDTO);
			ThongBaoDTO data = ThongBaoMapper.toDTO(t);
			return new BaseResponse<>("Successful!", data, HttpStatus.CREATED);
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> updateThongBao(@RequestBody ThongBaoDTO thongBaoDTO, @PathVariable("id") Long id){
		try {
			thongBaoDTO.setId(id);
			ThongBao t = thongBaoService.updateThongBao(thongBaoDTO);
			ThongBaoDTO data = ThongBaoMapper.toDTO(t);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> deleteThongBao(@PathVariable("id") Long id){
		try {
			thongBaoService.deleteThongBao(id);
			return new BaseResponse<>("Successful!", null, HttpStatus.OK);
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> getThongBaoById(@PathVariable("id") Long thongbaoId){
		try {
			ThongBao t = thongBaoService.getThongBaoById(thongbaoId);
			ThongBaoDTO data = ThongBaoMapper.toDTO(t);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/user/{userid}", produces = "application/json")
	public BaseResponse<?> getThongBaoByUserId(@PathVariable("userid") Long userId){
		try {
			List<ThongBaoDTO> data = thongBaoService.getListThongBaoByUserId(userId);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}

}
