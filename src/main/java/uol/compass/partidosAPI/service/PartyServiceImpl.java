package uol.compass.partidosAPI.service;

import uol.compass.partidosAPI.dto.PartyDto;
import uol.compass.partidosAPI.dto.PartyFormDto;
import uol.compass.partidosAPI.model.Party;
import uol.compass.partidosAPI.exceptions.BusinessException;
import uol.compass.partidosAPI.repository.PartyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PartyServiceImpl implements PartyService {

	@Autowired
	private PartyRepository partyRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public PartyDto save(PartyDto body) {
		Party party = this.partyRepository.save(mapper.map(body, Party.class));
		return mapper.map(party, PartyDto.class);
	}
	
	@Override
	public Page<PartyDto> findAll(Pageable page) {
		Page<Party> party = this.partyRepository.findAll(page);
		List<PartyDto> partyList = new ArrayList<>();
		for (Party party1 : party.getContent()) {
			PartyDto map = mapper.map(party1, PartyDto.class);
			partyList.add(map);
		}
		return new PageImpl<>(partyList, page, party.getTotalElements());
	}
	
	@Override
	public PartyDto search(Long id) {
		Party party = this.partyRepository.findById(id)
				.orElseThrow(() -> new BusinessException(404, "NOT_FOUND", "Party not found"));
		return mapper.map(party, PartyDto.class);
	}
	
	
	@Override
	public PartyDto update(Long id, PartyFormDto body) {
		Party party = this.partyRepository.findById(id)
				.orElseThrow(() -> new BusinessException(404, "NOT_FOUND", "Party not found"));
		party.setName(body.getName());
		party.setAcronym(body.getAcronym());
		party.setIdeology(body.getIdeology());
		party.setFoundationDate(body.getFoundationDate());
		Party updated = this.partyRepository.save(party);
		return mapper.map(updated, PartyDto.class);
	}
	
	@Override
	public void delete(Long id) {
		try {
			Party party = this.partyRepository.findById(id)
					.orElseThrow(() -> new BusinessException(404, "NOT_FOUND", "Party not found"));
			this.partyRepository.delete(party);
		} catch (Exception ex) {
			throw new BusinessException(409, "CONFLICT", "Delete associate from the party");
		}
	}

	@Override
	public Party findById(Long id) {
		return null;
	}


}
