package demo.entity;

import java.util.Date;
import java.util.List;

import demo.Enum.TrangThaiLop;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "lop")
public class Lop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "tieude", nullable = false,
			columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String tieude;
	
	@Column(name = "quan", nullable = false,
			columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String quan;
	
	@Column(name = "diachi", nullable = false,
			columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String diachi;
	
	@Column(name = "sobuoi", nullable = false)
	private Integer sobuoi;
	
	@Column(name = "sogio", nullable = false)
	private Float sogio;
	
	@Column(name = "hocphi", nullable = false)
	private Long hocphi;
	
	@Column(name = "phiungtuyen", nullable = false)
	private Long phiungtuyen;
	
	@Column(name = "sohs", nullable = false)
	private Integer sohs;
	
	@Column(name = "gioitinhhs", nullable = false,
			columnDefinition = "VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String gioitinhhs;
	
	@Column(name = "motahs", nullable = true,
			columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String motahs;
	
	@Column(name = "nghenghiepgs", nullable = false,
			columnDefinition = "VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String nghenghiepgs;
	
	@Column(name = "gioitinhgs", nullable = false,
			columnDefinition = "VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String gioitinhgs;
	
	@Column(name = "truonggs", nullable = false,
			columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String truonggs;
	
	@Column(name = "yeucaukhac", nullable = false,
			columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String yeucaukhac;
	
	@Column(name = "ngaytao", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date ngaytao;
	
	@Column(name = "ngaygiao", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date ngaygiao;
	
	@Column(name = "hanungtuyen", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date hanungtuyen;
	
	@Column(name = "hinhthuc", nullable = false,
			columnDefinition = "VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String hinhthuc;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trangthailop", nullable = false, length = 50)
	private TrangThaiLop trangthailop;
	
	@OneToMany(mappedBy = "lop", cascade = CascadeType.ALL)
	private List<LichTrong> dslichtrong;
	
	@OneToMany(mappedBy = "lop", cascade = CascadeType.ALL)
	private List<UngTuyen> dsungtuyen;
	
	@OneToMany(mappedBy = "lop", cascade = CascadeType.ALL)
	private List<LoiMoi> dsloimoi;
	
	@ManyToOne
	@JoinColumn(name = "hocvienid", referencedColumnName = "id")
	private HocVien hocvien;
	
	@ManyToOne
	@JoinColumn(name = "chudeid", referencedColumnName = "id")
	private ChuDe chude;
	
}
