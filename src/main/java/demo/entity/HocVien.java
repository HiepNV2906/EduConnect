package demo.entity;

import java.util.List;

import demo.Enum.TrangThaiUser;
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
	@Column(name = "quan", nullable = false,
			columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String quan;
	
	@Column(name = "diachi", nullable = false,
			columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String diachi;
	
	@Column(name = "cccd", nullable = false,
			columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String cccd;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trangthai", nullable = false, length = 50)
	private TrangThaiUser trangthai;
	
	@OneToMany(mappedBy = "hocvien", cascade = CascadeType.ALL)
	private List<Lop> dslop;
	
}
