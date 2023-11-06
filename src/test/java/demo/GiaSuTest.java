//package demo;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import demo.Enum.TrangThaiLoiMoi;
//import demo.entity.GiaSu;
//import demo.repository.GiaSuRepository;
//import demo.service.StorageService;
//
//@SpringBootTest
//public class GiaSuTest {
//	@Autowired
//	GiaSuRepository giaSuRepository;
//	@Autowired
//	StorageService storageService;
//	
////	@Test
////	public void testGetDoctorById() {		
////		List<GiaSu> a = giaSuRepository.findByKeyword("Ap");
////		for(int i=0 ; i<a.size() ; i++) {
////			System.out.println(a.get(i).getId());
////		}
////		assertThat(a).isNotNull();
////	}
//	
////	@Test
////	public void testGetDoctorByName() {		
////		List<GiaSu> a = giaSuRepository.findByFilter("", "", "Nam", "", "");
////		System.out.println(a.size());
////		for(int i=0 ; i<a.size() ; i++) {
////			System.out.println(a.get(i).getId());
////		}
////		assertThat(a).isNotNull();
////	}
//	
//	
//	@Test
//	public void testGetDoctorByName() {
////		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//		System.out.print(new Date());
////		TrangThaiLoiMoi a = TrangThaiLoiMoi.CHO;
////		System.out.print(a == TrangThaiLoiMoi.CHO);
////		System.out.print(a == TrangThaiLoiMoi.THANHCONG);
//		
////		System.out.println(storageService.load("183f44af-52a3-4b6f-8109-5775761d1ca7.jpg"));
//	}
//}
