package demo.entity;

import java.util.Date;

import demo.Enum.TrangThaiLoiMoi;
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
@Entity(name = "loimoi")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"giasuid", "lopid"}))
public class LoiMoi {
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
	
	@Column(name = "ngaymoi", nullable = false)
	private Date ngaymoi;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "trangthailoimoi", length = 50, nullable = false)
	private TrangThaiLoiMoi trangthailoimoi;
	
	@OneToOne(mappedBy = "loimoi", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private UngTuyen ungTuyen;
}
