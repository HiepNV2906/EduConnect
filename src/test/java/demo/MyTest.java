package demo;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import demo.entity.User;
import demo.exception.StorageException;
import demo.request.RegisterUserRequest;
import demo.service.UserService;

@SpringBootTest
public class MyTest {
	
	@Autowired
	UserService userService;
	
	@Test
	public void test1() {
		RegisterUserRequest adminDTO = new RegisterUserRequest(null, "Nguyễn Văn Hiệp", "0123456789", "nguyenvanhiepthlnbg@gmail.com", "123456789", null, "Nam", new Date(2001, 6, 29));
		try {
			User admin = userService.addUser(adminDTO);
			System.out.println(admin.getEmail());
		} catch (StorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
