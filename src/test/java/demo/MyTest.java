package demo;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import demo.Enum.VaiTro;
import demo.entity.User;
import demo.exception.StorageException;
import demo.repository.ThanhToanRepository;
import demo.repository.UserRepository;
import demo.request.RegisterUserRequest;
import demo.service.ThongKeService;
import demo.service.UserService;

@SpringBootTest
public class MyTest {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ThanhToanRepository thanhToanRepository;
	
	@Autowired
	ThongKeService thongKeService;
	
//	@Test
//	public void test1() {
//		RegisterUserRequest adminDTO = new RegisterUserRequest(null, "Nguyễn Văn Hiệp", "0123456789", "nguyenvanhiepthlnbg@gmail.com", "123456789", null, "Nam", new Date(2001, 6, 29));
//		try {
//			User admin = userService.addUser(adminDTO);
//			System.out.println(admin.getEmail());
//		} catch (StorageException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void test2() {
//		List<Object[]> list = thongKeService.thongKeGiaSuVaHocVienMoiTheoThang();
//		for (Object[] objects : list) {
//			System.out.println(objects[0] + "-" + objects[1] + "-" + objects[2]);
//		}
	}
	
	@Test
	public void test3() {
		RegisterUserRequest managerDTO = new RegisterUserRequest(null, "Nguyễn Văn Hiệp", "0123456789", "manager@gmail.com", "123456789", null, "Nam", new Date(2001, 6, 29));
		try {
			User managerUser = userService.addUser(managerDTO);
			managerUser.setVaitro(VaiTro.MANAGER);
			managerUser = userRepository.save(managerUser);
			System.out.println(managerUser.getEmail());
		} catch (StorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
