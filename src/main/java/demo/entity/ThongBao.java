package demo.entity;

import java.util.Date;

import demo.Enum.TrangThaiThongBao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "thongbao")
public class ThongBao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "tieude", length = 255, nullable = false)
	private String tieude;
	
	@Column(name = "noidung", columnDefinition = "text", nullable = false)
	private String noidung;
	
	@Column(name = "ngay", columnDefinition = "DATETIME", nullable = false)
	private Date ngay;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trangthaithongbao", length = 50, nullable = false)
	private TrangThaiThongBao trangthaithongbao;
	
	@ManyToOne
	@JoinColumn(name = "userid", referencedColumnName = "id")
	private User user;
	
}
