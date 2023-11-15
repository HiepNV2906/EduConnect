package demo.entity;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "thanhtoan")
public class ThanhToan {
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "nganhang", nullable = false, 
			columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String nganhang;
	
	@Column(name = "magiaodichnganhang", nullable = false,
			columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String magiaodichnganhang;
	
	@Column(name = "noidung", nullable = false,
			columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_vietnamese_ci")
	private String noidung;
	
	@Column(name = "sotien", nullable = false)
	private Long sotien;
	
	@Column(name = "ngaythanhtoan", columnDefinition = "DATETIME", nullable = false)
	private Date ngaythanhtoan;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ungtuyenid", referencedColumnName = "id")
	private UngTuyen ungtuyen;
	
}
