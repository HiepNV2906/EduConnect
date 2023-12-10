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

import demo.Enum.TrangThaiCongNo;
import demo.Enum.TrangThaiUngTuyen;
import demo.dto.UngTuyenDTO;
import demo.entity.UngTuyen;
import demo.mapper.UngTuyenMapper;
import demo.request.Status;
import demo.response.BaseResponse;
import demo.service.UngTuyenService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/ungtuyen")
public class UngTuyenController {

	private final int sizeOfPage= 10;
	
	@Autowired
	UngTuyenService ungTuyenService;
	
	@PreAuthorize("hasAuthority('GIASU')")
	@PostMapping(value = "", produces = "application/json")
	public BaseResponse<?> addUngTuyen(@RequestBody UngTuyenDTO ungTuyenDTO){
		try {
			UngTuyen u = ungTuyenService.addUngTuyen(ungTuyenDTO);
			UngTuyenDTO data = UngTuyenMapper.toDTO(u);
			return new BaseResponse<>("Successful!", data, HttpStatus.CREATED);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('GIASU')")
	@PutMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> updateUngTuyen(
			@RequestBody UngTuyenDTO ungTuyenDTO,
			@PathVariable("id") Long id){
		try {
			ungTuyenDTO.setId(id);
			UngTuyen u = ungTuyenService.updateUngTuyen(ungTuyenDTO);
			UngTuyenDTO data = UngTuyenMapper.toDTO(u);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "/changeungtuyen/{id}", produces = "application/json")
	public BaseResponse<?> updateTrangThaiUngTuyen(
			@RequestBody Status changeStatus,
			@PathVariable("id") Long id){
		try {
			UngTuyen u = ungTuyenService.updateTrangThaiUngTuyen(id, TrangThaiUngTuyen.valueOf(changeStatus.getStatus()));
			UngTuyenDTO data = UngTuyenMapper.toDTO(u);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "/changecongno/{id}", produces = "application/json")
	public BaseResponse<?> updateTrangThaiCongNo(
			@RequestBody Status changeStatus,
			@PathVariable("id") Long id){
		try {
			UngTuyen u = ungTuyenService.updateTrangThaiCongNo(id, TrangThaiCongNo.valueOf(changeStatus.getStatus()));
			UngTuyenDTO data = UngTuyenMapper.toDTO(u);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('GIASU')")
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> deleteUngTuyen(
			@PathVariable("id") Long id){
		try {
			ungTuyenService.deleteUngTuyen(id);
			return new BaseResponse<>("Successful!", null, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> getUngTuyenById(
			@PathVariable("id") Long id){
		try {
			UngTuyen u = ungTuyenService.getUngTuyenById(id);
			UngTuyenDTO data = UngTuyenMapper.toDTO(u);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = "/sapxepgiasu/{ungtuyenid}", produces = "application/json")
	public BaseResponse<?> sapXepGiaSu(
			@PathVariable("ungtuyenid") Long ungtuyenid){
		try {
			List<UngTuyen> u = ungTuyenService.sapXepGiaSu(ungtuyenid);
			List<UngTuyenDTO> data = UngTuyenMapper.toListDTO(u);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('GIASU')")
	@GetMapping(value = "/giasu/{id}", produces = "application/json")
	public BaseResponse<?> getUngTuyenByGiaSuId(
			@PathVariable("id") Long id,
			@RequestParam(name = "page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<UngTuyen> pageUngTuyen = ungTuyenService.findByGiaSuId(id, PageRequest.of(page.orElse(0), sizeOfPage));
				Page<UngTuyenDTO> data = UngTuyenMapper.toPageDTO(pageUngTuyen);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<UngTuyen> listUngTuyen = ungTuyenService.findByGiaSuId(id);
				List<UngTuyenDTO> data = UngTuyenMapper.toListDTO(listUngTuyen);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = "/trangthaicongno", produces = "application/json")
	public BaseResponse<?> getUngTuyenByTrangThaiCongNo(
			@RequestParam(name = "status") String status){
		try {
			List<UngTuyen> listUngTuyen = ungTuyenService.findByTrangThaiCongNo(TrangThaiCongNo.valueOf(status));
			List<UngTuyenDTO> data = UngTuyenMapper.toListDTO(listUngTuyen);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('GIASU')")
	@GetMapping(value = "/congnogiasu", produces = "application/json")
	public BaseResponse<?> getUngTuyenByTrangThaiCongNoAndGiaSu(
			@RequestParam(name = "giasuid") Long giasuid,
			@RequestParam(name = "status") String status){
		try {
			List<UngTuyen> listUngTuyen = ungTuyenService.findByGiaSuAndTrangThaiCongNo(giasuid, TrangThaiCongNo.valueOf(status));
			List<UngTuyenDTO> data = UngTuyenMapper.toListDTO(listUngTuyen);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('HOCVIEN')")
	@GetMapping(value = "/lop/{id}", produces = "application/json")
	public BaseResponse<?> getUngTuyenByLopId(
			@PathVariable("id") Long id,
			@RequestParam(name = "page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<UngTuyen> pageUngTuyen = ungTuyenService.findByLopId(id, PageRequest.of(page.orElse(0), sizeOfPage));
				Page<UngTuyenDTO> data = UngTuyenMapper.toPageDTO(pageUngTuyen);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
			else {
				List<UngTuyen> listUngTuyen = ungTuyenService.findByLopId(id);
				List<UngTuyenDTO> data = UngTuyenMapper.toListDTO(listUngTuyen);
				return new BaseResponse<>("Successful!", data, HttpStatus.OK);
			}
		}catch (Exception e) {
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
}
