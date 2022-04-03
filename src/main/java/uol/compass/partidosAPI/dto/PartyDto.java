package uol.compass.partidosAPI.dto;

import uol.compass.partidosAPI.model.constants.Ideology;
import uol.compass.partidosAPI.model.Associate;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PartyDto{

	private Long id;
	private String name;
	private String acronym;
	private Ideology ideology;
	private Date foundationDate;
	private List<Associate> associates;

	public PartyDto(String name, String acronym, Ideology ideology, Date foundationDate) {

	}
}
