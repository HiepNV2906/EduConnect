package demo.entity;

import java.util.List;

import demo.Enum.TrangThaiUser;
import demo.dto.HocVienDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "hocvien")
public class HocVien extends User{
	@Column(name = "quan", length = 100, nullable = false)
	private String quan;
	
	@Column(name = "diachi", length = 255, nullable = false)
	private String diachi;
	
	@Column(name = "cccd", length = 255, nullable = false)
	private String cccd;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trangthai", length = 50, nullable = false)
	private TrangThaiUser trangthai;
	
	@OneToMany(mappedBy = "hocvien", cascade = CascadeType.ALL)
	private List<Lop> dslop;
	
}
