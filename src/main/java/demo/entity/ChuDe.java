package demo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "chude")
public class ChuDe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "tenmonhoc", length = 255, nullable = false)
	private String tenmonhoc;
	
	@Column(name = "trinhdo", length = 50, nullable = false)
	private String trinhdo;
	
	@OneToMany(mappedBy = "chude", cascade = CascadeType.ALL)
	private List<Lop> dslop;
	
	@ManyToMany(mappedBy = "dschude")
	private List<GiaSu> dsgiasu;
	
}
