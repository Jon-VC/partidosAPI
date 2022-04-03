package uol.compass.partidosAPI.dto;

import lombok.AllArgsConstructor;
import uol.compass.partidosAPI.model.constants.Ideology;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class PartyFormDto {
	
	@NotEmpty(message = "name is required")
	private String name;
	@NotNull(message = "acronym is required")
	private String acronym;
	@NotNull(message = "ideology is required")
	private Ideology ideology;
	@NotNull(message = "foundationDate is required")
	private Date foundationDate;
	
}
