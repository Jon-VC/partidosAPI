package uol.compass.partidosAPI.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uol.compass.partidosAPI.JPAUtil;
import uol.compass.partidosAPI.builder.PartyBuilder;
import uol.compass.partidosAPI.dto.PartyDto;
import uol.compass.partidosAPI.dto.PartyFormDto;
import uol.compass.partidosAPI.model.Party;
import uol.compass.partidosAPI.model.constants.Ideology;
import uol.compass.partidosAPI.service.PartyService;

import javax.persistence.EntityManager;
import java.util.Date;

class PartyDtoTest {

	private PartyService partyService;

	@BeforeEach
	public void beforeEach() {
		EntityManager em = JPAUtil.getEntityManager();
		this.partyService = new PartyService() {
			@Override
			public PartyDto save(PartyDto body) {
				return null;
			}

			@Override
			public Page<PartyDto> findAll(Pageable page) {
				return null;
			}

			@Override
			public PartyDto search(Long id) {
				return null;
			}

			@Override
			public PartyDto update(Long id, PartyFormDto body) {
				return null;
			}

			@Override
			public void delete(Long id) {

			}

			@Override
			public Party findById(Long id) {
				return null;
			}
		};
		em.getTransaction().begin();
	}

	@Test
	void saveParty() {

		new PartyBuilder()
				.withName("Progressistas")
				.withAcronym("PP")
				.withIdeology(Ideology.CENTRO)
				.withFoundationDate(new Date(1995, 11, 16));
		PartyDto party = PartyBuilder
				.create();
	
		party = partyService.save(party);
		
		Party salvo = partyService.findById(party.getId());
		Assertions.assertNotNull(salvo);
	}

	
}
