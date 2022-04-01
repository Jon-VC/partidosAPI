package uol.compass.partidosAPI.service;

import uol.compass.partidosAPI.dto.AssociateDto;
import uol.compass.partidosAPI.dto.AssociateFormDto;
import uol.compass.partidosAPI.model.Associate;
import uol.compass.partidosAPI.model.Party;
import uol.compass.partidosAPI.exceptions.BusinessException;
import uol.compass.partidosAPI.repository.AssociateRepository;
import uol.compass.partidosAPI.repository.PartyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssociateServiceImpl implements AssociateService {

	@Autowired
	private AssociateRepository associateRepository;
	@Autowired
	private PartyRepository partyRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public AssociateDto save(AssociateFormDto body) {
		Associate associate = this.associateRepository.save(mapper.map(body, Associate.class));
		return mapper.map(associate, AssociateDto.class);
	}
	
	@Override
	public AssociateDto registerParty(Long id, Long party) {
		Associate associate = this.associateRepository.findById(id)
				.orElseThrow(() -> new BusinessException(404, "NOT_FOUND", "Associate not found"));
		Party partyFind = this.partyRepository.findById(party)
			.orElseThrow(() -> new BusinessException(404, "NOT_FOUND", "Associate not found"));
		List<Party> partyList = new ArrayList<>();
		partyList.add(partyFind);
		associate.setParty(partyList);
		associate = this.associateRepository.save(associate);
		return mapper.map(associate, AssociateDto.class);
	}
	
	@Override
	public Page<AssociateDto> findAll(Pageable page) {
		Page<Associate> associate = this.associateRepository.findAll(page);
		List<AssociateDto> associateList = associate.getContent()
				.stream()
				.map(associate1 -> mapper.map(associate1, AssociateDto.class))
				.collect(Collectors.toList());
		return new PageImpl<>(associateList, page, associate.getTotalElements());
	}
	
	@Override
	public AssociateDto search(Long id) {
		Associate associate = this.associateRepository.findById(id)
				.orElseThrow(() -> new BusinessException(404, "NOT_FOUND", "Associado not found"));
		return mapper.map(associate, AssociateDto.class);
	}
	
	@Override
	public AssociateDto update(Long id, AssociateFormDto body) {
		Associate associate = this.associateRepository.findById(id)
				.orElseThrow(() -> new BusinessException(404, "NOT_FOUND", "Associado not found"));
		associate.setName(body.getName());
		associate.setGender(body.getGender());
		associate.setBirthDate(body.getBirthDate());
		associate.setPoliticalOffice(body.getPoliticalOffice());
		Associate updated = this.associateRepository.save(associate);
		return mapper.map(updated, AssociateDto.class);
	}
	
	@Override
	public void delete(Long id) {
		try {
			Associate associate = this.associateRepository.findById(id)
					.orElseThrow(() -> new BusinessException(404, "NOT_FOUND", "Associate not found"));
			this.associateRepository.delete(associate);
		} catch (Exception ex) {
			throw new BusinessException(409, "CONFLICT", "Delete associate from his party");
		}
	}
	
}
