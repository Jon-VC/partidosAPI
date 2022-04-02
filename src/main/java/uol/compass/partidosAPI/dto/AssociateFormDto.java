package uol.compass.partidosAPI.dto;

import uol.compass.partidosAPI.model.constants.PoliticalOffice;
import uol.compass.partidosAPI.model.constants.Gender;
import uol.compass.partidosAPI.model.Party;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class AssociateFormDto {

	@NotEmpty(message = "name is required")
	private String name;
	private List<Party> party;
	@NotNull(message = "cargoPolitico is required")
	private PoliticalOffice politicalOffice;
	@NotNull(message = "birthDate is required")
	private Date birthDate;
	@NotNull(message = "gender is required")
	private Gender gender;
	
}
