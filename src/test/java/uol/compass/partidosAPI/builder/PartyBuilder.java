package uol.compass.partidosAPI.builder;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import uol.compass.partidosAPI.dto.PartyDto;
import uol.compass.partidosAPI.model.constants.Ideology;

import java.util.Date;

@AllArgsConstructor
public class PartyBuilder {

	private static String name;
	private static String acronym;
	private static Ideology ideology;
	private static Date foundationDate;

	public PartyBuilder withName(String name) {
		PartyBuilder.name = name;
		return this;
	}

	public PartyBuilder withAcronym(String acronym) {
		PartyBuilder.acronym = acronym;
		return this;
	}

	public PartyBuilder withIdeology(Ideology ideology) {
		PartyBuilder.ideology = ideology;
		return this;
	}
	
	public PartyBuilder withFoundationDate(Date foundationDate) {
		PartyBuilder.foundationDate = foundationDate;
		return this;
	}

	public static PartyDto create() {
		return new PartyDto(name, acronym, ideology, foundationDate);
	}

}
