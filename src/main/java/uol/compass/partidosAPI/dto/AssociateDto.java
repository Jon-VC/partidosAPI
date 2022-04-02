package uol.compass.partidosAPI.dto;

import uol.compass.partidosAPI.model.constants.PoliticalOffice;
import uol.compass.partidosAPI.model.constants.Gender;
import uol.compass.partidosAPI.model.Party;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AssociateDto {

	private Long id;
	private String name;
	private List<Party> party;
	private PoliticalOffice politicalOffice;
	private Date birthDate;
	private Gender gender;
	
}
