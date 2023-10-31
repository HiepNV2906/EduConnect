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
	
	@Column(name = "tieude", length = 255, nullable = false)
	private String tieude;
	
	@Column(name = "quan", length = 100, nullable = false)
	private String quan;
	
	@Column(name = "diachi", length = 255, nullable = false)
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
	
	@Column(name = "gioitinhhs", length = 50, nullable = false)
	private String gioitinhhs;
	
	@Column(name = "motahs", columnDefinition = "text", nullable = false)
	private String motahs;
	
	@Column(name = "nghenghiepgs", length = 100, nullable = false)
	private String nghenghiepgs;
	
	@Column(name = "gioitinhgs", length = 50, nullable = false)
	private String gioitinhgs;
	
	@Column(name = "truonggs", length = 255, nullable = false)
	private String truonggs;
	
	@Column(name = "yeucaukhac", columnDefinition = "text", nullable = false)
	private String yeucaukhac;
	
	@Column(name = "ngaytao", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date ngaytao;
	
	@Column(name = "hanungtuyen", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date hanungtuyen;
	
	@Column(name = "hinhthuc", length = 50, nullable = false)
	private String hinhthuc;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trangthailop", length = 50, nullable = false)
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
