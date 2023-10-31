package demo.entity;

import demo.entity.composite.LopLichTrongId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "lichtrong")
public class LichTrong {
	@EmbeddedId
	private LopLichTrongId lopLichTrongId;
	
	@Column(name = "sang", nullable = false)
	private int sang;
	
	@Column(name = "chieu", nullable = false)
	private int chieu;
	
	@Column(name = "toi", nullable = false)
	private int toi;
	
	@ManyToOne
	@MapsId("lopid")
	@JoinColumn(name = "lopid", referencedColumnName = "id")
	private Lop lop;
	
}
