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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import demo.Enum.TrangThaiUser;
import demo.dto.GiaSuDTO;
import demo.entity.GiaSu;
import demo.mapper.GiaSuMapper;
import demo.request.Status;
import demo.request.RegisterGiaSuRequest;
import demo.response.BaseResponse;
import demo.service.GiaSuService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/giasu")
public class GiaSuController {
	private final int sizeOfPage=10;
	
	@Autowired
	GiaSuService giaSuService;
	

	@PostMapping(value = "")
	public BaseResponse<?> addGiaSu(
			@RequestParam("infoGS") String infoGS,
            @RequestParam(name = "avata") MultipartFile avata,
            @RequestParam(name = "cccd") MultipartFile cccd){
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			RegisterGiaSuRequest registerGiaSuRequest = objectMapper.readValue(infoGS, RegisterGiaSuRequest.class);
			registerGiaSuRequest.setAvata(avata);
			registerGiaSuRequest.setCccd(cccd);
			GiaSu g = giaSuService.addGiaSu(registerGiaSuRequest);
			GiaSuDTO data = GiaSuMapper.toDTO(g);
			return new BaseResponse<>("Successful!", data, HttpStatus.CREATED);
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping(value = "/changeStatus/{id}", produces = "application/json")
	public BaseResponse<?> updateGiaSu(@RequestBody Status changeStatus, @PathVariable("id") Long id){
		try {
			GiaSu g = giaSuService.updateTrangThai(id, TrangThaiUser.valueOf(changeStatus.getStatus()));
			GiaSuDTO data = GiaSuMapper.toDTO(g);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN') || hasAuthority('GIASU')")
	@PutMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> updateGiaSu(
			@RequestParam("infoGS") String infoGS,
            @RequestParam(name = "avata", required = false) MultipartFile avata,
            @RequestParam(name = "cccd", required = false) MultipartFile cccd, 
            @PathVariable("id") Long id){
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			RegisterGiaSuRequest registerGiaSuRequest = objectMapper.readValue(infoGS, RegisterGiaSuRequest.class);
			registerGiaSuRequest.setId(id);
			registerGiaSuRequest.setAvata(avata);
			registerGiaSuRequest.setCccd(cccd);
			GiaSu g = giaSuService.updateGiaSu(registerGiaSuRequest);
			GiaSuDTO data = GiaSuMapper.toDTO(g);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> deleteGiaSu(@PathVariable("id") Long id){
		try {
			giaSuService.deleteGiaSu(id);
			return new BaseResponse<>("Successful!", null, HttpStatus.OK);
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> getGiaSuById(@PathVariable("id") Long id){
		try {
			GiaSu g = giaSuService.getGiaSuById(id);
			GiaSuDTO data = GiaSuMapper.toDTO(g);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/topnew", produces = "application/json")
	public BaseResponse<?> getTop10GiaSuMoi(){
		try {
			List<GiaSu> listGiaSu = giaSuService.findTop10New();
			List<GiaSuDTO> data = GiaSuMapper.toListDTO(listGiaSu);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "", produces = "application/json")
	public BaseResponse<?> getDSGiaSu(@RequestParam(name="page") Optional<Integer> page){
		try {
			if(page.isPresent()) {
				Page<GiaSu> pageGiaSu = giaSuService.getListGiaSu(PageRequest.of(page.orElse(0), sizeOfPage));
				Page<GiaSuDTO> data = GiaSuMapper.toPageDTO(pageGiaSu);
				return new BaseResponse<>("Successful", data, HttpStatus.OK);
			}
			else {
				List<GiaSu> listGiaSu = giaSuService.getListGiaSu();
				List<GiaSuDTO> data = GiaSuMapper.toListDTO(listGiaSu);
				return new BaseResponse<>("Successful", data, HttpStatus.OK);
			}
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/trangthai", produces = "application/json")
	public BaseResponse<?> getDSGiaSuByStatus(
			@RequestParam(name="status") String status,
			@RequestParam(name="page") Optional<Integer> page){
		try {
			List<GiaSu> listGiaSu = giaSuService.findByTrangThai(TrangThaiUser.valueOf(status));
			List<GiaSuDTO> data = GiaSuMapper.toListDTO(listGiaSu);
			return new BaseResponse<>("Successful", data, HttpStatus.OK);
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/search", produces = "application/json")
	public BaseResponse<?> getDSGiaSuByKey(
			@RequestParam(name="page") Optional<Integer> page,
			@RequestParam(name="key", defaultValue = "") String key){
		try {
			if(page.isPresent()) {
				Page<GiaSu> pageGiaSu = giaSuService.findByKey(key, PageRequest.of(page.orElse(0), sizeOfPage));
				Page<GiaSuDTO> data = GiaSuMapper.toPageDTO(pageGiaSu);
				return new BaseResponse<>("Successful", data, HttpStatus.OK);
			}
			else {
				List<GiaSu> listGiaSu = giaSuService.findByKey(key);
				List<GiaSuDTO> data = GiaSuMapper.toListDTO(listGiaSu);
				return new BaseResponse<>("Successful", data, HttpStatus.OK);
			}
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/filter", produces = "application/json")
	public BaseResponse<?> getDSGiaSuByFilter(
			@RequestParam(name="key", defaultValue = "") String key,
			@RequestParam(name="page") Optional<Integer> page,
			@RequestParam(name="quan", defaultValue = "") String quan,
			@RequestParam(name="nghenghiep", defaultValue = "") String nghenghiep,
			@RequestParam(name="gioitinh", defaultValue = "") String gioitinh,
			@RequestParam(name="mon", defaultValue = "") String mon,
			@RequestParam(name="trinhdo", defaultValue = "") String trinhdo){
		try {
			if(page.isPresent()) {
				Page<GiaSu> pageGiaSu = giaSuService.findByFilter(key, quan, gioitinh, mon, trinhdo,
						PageRequest.of(page.orElse(0), sizeOfPage));
				Page<GiaSuDTO> data = GiaSuMapper.toPageDTO(pageGiaSu);
				return new BaseResponse<>("Successful", data, HttpStatus.OK);
			}
			else {
				List<GiaSu> listGiaSu = giaSuService.findByFilter(key, quan, gioitinh, mon, trinhdo);
				List<GiaSuDTO> data = GiaSuMapper.toListDTO(listGiaSu);
				return new BaseResponse<>("Successful", data, HttpStatus.OK);
			}
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
}
