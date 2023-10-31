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
	
	@Column(name = "nganhang", length = 255, nullable = false)
	private String nganhang;
	
	@Column(name = "magiaodichnganhang", length = 255, nullable = false)
	private String magiaodichnganhang;
	
	@Column(name = "noidung", length = 255, nullable = false)
	private String noidung;
	
	@Column(name = "sotien", nullable = false)
	private Long sotien;
	
	@Column(name = "ngaythanhtoan", columnDefinition = "DATETIME", nullable = false)
	private Date ngaythanhtoan;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ungtuyenid", referencedColumnName = "id")
	private UngTuyen ungtuyen;
	
}
