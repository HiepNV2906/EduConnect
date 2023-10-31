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

import demo.Enum.TrangThaiUser;
import demo.dto.ChuDeDTO;
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
	
	@PostMapping(value = "", produces = "application/json")
	public BaseResponse<?> addGiaSu(@RequestBody RegisterGiaSuRequest registerGiaSuRequest){
		try {
			for (ChuDeDTO i : registerGiaSuRequest.getDschude()) {
				System.out.println(i.getId());
			}
			GiaSu g = giaSuService.addGiaSu(registerGiaSuRequest);
			GiaSuDTO data = GiaSuMapper.toDTO(g);
			return new BaseResponse<>("Successful!", data, HttpStatus.CREATED);
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
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
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public BaseResponse<?> updateGiaSu(@RequestBody GiaSuDTO giaSuDTO, @PathVariable("id") Long id){
		try {
			giaSuDTO.setId(id);
			GiaSu g = giaSuService.updateGiaSu(giaSuDTO);
			GiaSuDTO data = GiaSuMapper.toDTO(g);
			return new BaseResponse<>("Successful!", data, HttpStatus.OK);
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
	
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
			@RequestParam(name="page") Optional<Integer> page,
			@RequestParam(name="quan", defaultValue = "") String quan,
			@RequestParam(name="nghenghiep", defaultValue = "") String nghenghiep,
			@RequestParam(name="gioitinh", defaultValue = "") String gioitinh,
			@RequestParam(name="mon", defaultValue = "") String mon,
			@RequestParam(name="trinhdo", defaultValue = "") String trinhdo){
		try {
			if(page.isPresent()) {
				Page<GiaSu> pageGiaSu = giaSuService.findByFilter(quan, nghenghiep, gioitinh, mon, trinhdo,
						PageRequest.of(page.orElse(0), sizeOfPage));
				Page<GiaSuDTO> data = GiaSuMapper.toPageDTO(pageGiaSu);
				return new BaseResponse<>("Successful", data, HttpStatus.OK);
			}
			else {
				List<GiaSu> listGiaSu = giaSuService.findByFilter(quan, nghenghiep, gioitinh, mon, trinhdo);
				List<GiaSuDTO> data = GiaSuMapper.toListDTO(listGiaSu);
				return new BaseResponse<>("Successful", data, HttpStatus.OK);
			}
		} catch (Exception e){
			return new BaseResponse<>(e.getMessage(), null, HttpStatus.BAD_REQUEST);
		}
	}
}
