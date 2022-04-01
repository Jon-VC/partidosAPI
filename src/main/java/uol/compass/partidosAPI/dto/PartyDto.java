package uol.compass.partidosAPI.dto;

import uol.compass.partidosAPI.model.constants.Ideology;
import uol.compass.partidosAPI.model.Associate;
import lombok.Data;

import java.util.List;

@Data
public class PartyDto {

	private Long id;
	private String name;
	private String acronym;
	private Ideology ideology;
	private int foundationDate;
	private List<Associate> associates;
	
}