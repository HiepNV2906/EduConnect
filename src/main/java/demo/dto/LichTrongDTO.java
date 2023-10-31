package demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LichTrongDTO {
	private Long lopid;
	private Long thu;
	private int sang;
	private int chieu;
	private int toi;

}
