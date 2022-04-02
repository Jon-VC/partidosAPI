package uol.compass.partidosAPI.controller;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import uol.compass.partidosAPI.dto.PartyDto;
import uol.compass.partidosAPI.dto.PartyFormDto;
import uol.compass.partidosAPI.model.Party;
import uol.compass.partidosAPI.model.constants.Ideology;
import uol.compass.partidosAPI.repository.PartyRepository;
import uol.compass.partidosAPI.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/partidos")
public class PartyController {

	@Autowired
	private PartyService partyService;

	@Autowired
	private PartyRepository partyRepository;

	@Autowired
	ModelMapper mapper;

	@PostMapping
	public ResponseEntity<PartyDto> save(@RequestBody @Valid PartyFormDto body) {
		PartyDto party = this.partyService.save(body);
		return ResponseEntity.status(HttpStatus.CREATED).body(party);
	}

	@GetMapping
	@Cacheable(value = "listaDePartidos")
	public List<PartyDto> page(@RequestParam(required = false) Ideology ideology,
							   @PageableDefault(sort = "id",
									   direction = Sort.Direction.ASC, page = 0, size = 10)
									   Pageable pagination) {
		Page<Party> party;

		if (ideology == null) {
			party = partyRepository.findAll(pagination);
		} else {
			party = partyRepository.findByIdeology(ideology, pagination);
		}
		return party.stream().map(e -> mapper.map(e, PartyDto.class)).collect(Collectors.toList());
	}

	@PutMapping(path = "/{id}")
	@Transactional
	@CacheEvict(value = "listaDePartidos", allEntries = true)
	public ResponseEntity<PartyDto> update(@PathVariable Long id, @RequestBody @Valid PartyFormDto body) {
		PartyDto party = this.partyService.update(id, body);
		return ResponseEntity.ok(party);
	}

	@DeleteMapping(path = "/{id}")
	@Transactional
	@CacheEvict(value = "listaDePartidos", allEntries = true)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.partyService.delete(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	
}
