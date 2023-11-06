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
	
	@Column(name = "quan", length = 100, nullable = false)
	private String quan;
	
	@Column(name = "diachi", length = 255, nullable = false)
	private String diachi;
	
	@Column(name = "quequan", length = 255, nullable = false)
	private String quequan;
	
	@Column(name = "nghenghiep", length = 100, nullable = false)
	private String nghenghiep;
	
	@Column(name = "truong", length = 255, nullable = false)
	private String truong;
	
	@Column(name = "gioithieu", columnDefinition = "text")
	private String gioithieu;
	
	@Column(name = "kinhnghiem", columnDefinition = "text")
	private String kinhnghiem;
	
	@Column(name = "thanhtich", columnDefinition = "text")
	private String thanhtich;
	
	@Column(name = "cccd", length = 255, nullable = false)
	private String cccd;
	
	@Column(name = "khuvucday", length = 255, nullable = false)
	private String khuvucday;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trangthai", length = 50, nullable = false)
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
