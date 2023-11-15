package demo.entity;

import java.util.List;

import demo.Enum.TrangThaiUser;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "giasu")
public class GiaSu extends User{
	
	@Column(name = "quan", nullable = false,
			columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String quan;
	
	@Column(name = "diachi", nullable = false,
			columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String diachi;
	
	@Column(name = "quequan", nullable = false,
			columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String quequan;
	
	@Column(name = "nghenghiep", nullable = false,
			columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String nghenghiep;
	
	@Column(name = "truong", nullable = false,
			columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String truong;
	
	@Column(name = "gioithieu", columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String gioithieu;
	
	@Column(name = "kinhnghiem", columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String kinhnghiem;
	
	@Column(name = "thanhtich", columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String thanhtich;
	
	@Column(name = "cccd", nullable = false,
			columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String cccd;
	
	@Column(name = "khuvucday", nullable = false,
			columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String khuvucday;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trangthai", nullable = false, length = 50)
	private TrangThaiUser trangthai;
	
	@OneToMany(mappedBy = "giasu", cascade = CascadeType.ALL)
	private List<UngTuyen> dsungtuyen;
	
	@OneToMany(mappedBy = "giasu", cascade = CascadeType.ALL)
	private List<LoiMoi> dsloimoi;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable( name = "giasu_chude", 
				joinColumns = @JoinColumn(columnDefinition = "giasuid"),
				inverseJoinColumns = @JoinColumn(columnDefinition = "chudeid"))
	private List<ChuDe> dschude;
	
}
