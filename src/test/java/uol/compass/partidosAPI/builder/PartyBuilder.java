package uol.compass.partidosAPI.builder;

import uol.compass.partidosAPI.dto.PartyDto;
import uol.compass.partidosAPI.model.Party;
import uol.compass.partidosAPI.model.constants.Ideology;

import java.util.Date;

public class PartyBuilder {

	private String name;
	private String acronym;;
	private Ideology ideology;
	private Date foundationDate;

	public PartyBuilder withName(String name) {
		this.name = name;
		return this;
	}

	public PartyBuilder withAcronym(String acronym) {
		this.acronym = acronym;
		return this;
	}

	public PartyBuilder withIdeology(Ideology ideology) {
		this.ideology = ideology;
		return this;
	}
	
	public PartyBuilder withFoundationDate(Date foundationDate) {
		this.foundationDate = foundationDate;
		return this;
	}

	public PartyDto create() {
		return new PartyDto(name, acronym, ideology, foundationDate);
	}

}
