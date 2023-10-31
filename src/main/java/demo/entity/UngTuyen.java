package demo.entity;

import java.util.Date;

import demo.Enum.TrangThaiCongNo;
import demo.Enum.TrangThaiUngTuyen;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ungtuyen")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"giasuid", "lopid"}))
public class UngTuyen {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "giasuid", referencedColumnName = "id")
	private GiaSu giasu;
	
	@ManyToOne
	@JoinColumn(name = "lopid", referencedColumnName = "id")
	private Lop lop;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loimoiid", referencedColumnName = "id")
	private LoiMoi loimoi;
	
	@Column(name = "ngayungtuyen", columnDefinition = "DATETIME", nullable = false)
	private Date ngayungtuyen;
	
	@Column(name = "hanthanhtoan", nullable = false)
	private Date hanthanhtoan;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trangthaiungtuyen", length = 50, nullable = false)
	private TrangThaiUngTuyen trangthaiungtuyen;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trangthaicongno", length = 50)
	private TrangThaiCongNo trangthaicongno;
	
	@OneToOne(mappedBy = "ungtuyen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private ThanhToan thanhtoan;
	
}
