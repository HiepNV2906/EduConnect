package demo.entity;

import java.util.Date;
import java.util.List;

import demo.Enum.VaiTro;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id")
	protected Long id;
	
	@Column(name = "hoten", length = 255, nullable = false)
	protected String hoten;
	
	@Column(name = "sdt", length = 15, nullable = false)
	protected String sdt;
	
	@Column(name = "email", length = 255, nullable = false, unique = true)
	protected String email;
	
	@Column(name = "password", length = 255, nullable = false)
	protected String password;
	
	@Column(name = "avata", length = 255, nullable = false)
	protected String avata;
	
	@Column(name = "gioitinh", length = 50, nullable = false)
	protected String gioitinh;
	
	@Column(name = "ngaysinh", nullable = false)
	protected Date ngaysinh;
	
	@Column(name = "ngaytao", nullable = false)
	protected Date ngaytao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "vaitro", length = 50, nullable = false)
	protected VaiTro vaitro;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	protected List<ThongBao> dsthongbao;
	
	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected RefreshToken refreshtoken;
}
