package demo.entity.composite;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiaSuLopId implements Serializable{
	private Long giasuid;
	private Long lopid;
}
