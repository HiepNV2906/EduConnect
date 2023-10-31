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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import demo.Enum.TrangThaiLop;
import demo.dto.LopDTO;
import demo.entity.Lop;
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
	
	@PostMapping(value = "", produces = "application/json")
	public BaseResponse<?> addLop(@RequestBody LopDTO lopDTO){
		try {
			Lop l = lopService.addLop(lopDTO);
			LopDTO data = LopMapper.toDTO(l);
			return new BaseResponse<>("Successful!", data, HttpStatus.CREATED);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> updateLop(
			@RequestBody LopDTO lopDTO,
			@PathVariable("id") Long id){
		try {
			lopDTO.setId(id);
			Lop l = lopService.updateLop(lopDTO);
			LopDTO data = LopMapper.toDTO(l);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
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
	
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> deleteLop(
			@PathVariable("id") Long id){
		try {
			lopService.deleteLop(id);
			return new BaseResponse<>("Successful!", null, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
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
	
	@GetMapping(value = "/search", produces = "application/json")
	public BaseResponse<?> getListLopByKey(
			@RequestParam(name = "key") String key,
			@RequestParam(name = "page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<Lop> pageLop = lopService.findBykey(key, PageRequest.of(page.orElse(0), sizeOfPage));
				Page<LopDTO> data = LopMapper.toPageDTO(pageLop);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<Lop> listLop = lopService.findByKey(key);
				List<LopDTO> data = LopMapper.toListDTO(listLop);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/filter", produces = "application/json")
	public BaseResponse<?> getListLopByFilter(
			@RequestParam(name = "quan", defaultValue = "") String quan,
			@RequestParam(name = "hocphi", defaultValue = "0") Long hocphi,
			@RequestParam(name = "hinhthuc", defaultValue = "") String hinhthuc,
			@RequestParam(name = "mon", defaultValue = "") String mon,
			@RequestParam(name = "trinhdo", defaultValue = "") String trinhdo,
			@RequestParam(name = "page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<Lop> pageLop = lopService.findByFilter(quan, hinhthuc, hocphi, mon, trinhdo, PageRequest.of(page.orElse(0), sizeOfPage));
				Page<LopDTO> data = LopMapper.toPageDTO(pageLop);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<Lop> listLop = lopService.findByFilter(quan, hinhthuc, hocphi, mon, trinhdo);
				List<LopDTO> data = LopMapper.toListDTO(listLop);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/trangthai", produces = "application/json")
	public BaseResponse<?> getListLopByTrangThai(
			@RequestBody Status status,
			@RequestParam(name = "page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<Lop> pageLop = lopService.findByTrangThai(TrangThaiLop.valueOf(
						status.getStatus()), PageRequest.of(page.orElse(0), sizeOfPage));
				Page<LopDTO> data = LopMapper.toPageDTO(pageLop);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<Lop> listLop = lopService.findByTrangThai(TrangThaiLop.valueOf(status.getStatus()));
				List<LopDTO> data = LopMapper.toListDTO(listLop);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
}
