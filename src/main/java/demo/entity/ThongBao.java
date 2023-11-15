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
	
	@Column(name = "tieude", nullable = false,
			columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String tieude;
	
	@Column(name = "noidung", nullable = false,
			columnDefinition = "TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String noidung;
	
	@Column(name = "ngay", columnDefinition = "DATETIME", nullable = false)
	private Date ngay;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trangthaithongbao", nullable = false,
			columnDefinition = "VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private TrangThaiThongBao trangthaithongbao;
	
	@ManyToOne
	@JoinColumn(name = "userid", referencedColumnName = "id")
	private User user;
	
	@Column(name = "link", nullable = false, length = 50)
	private String link;
}
