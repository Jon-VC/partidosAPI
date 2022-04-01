package uol.compass.partidosAPI.model;

import uol.compass.partidosAPI.model.constants.Ideology;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "partidos")
public class Party {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	@Column(unique = true, nullable = false)
	private String name;
	@Column(unique = true, nullable = false)
	private String acronym;
	@Column(nullable = false)
	private Ideology ideology;
	@Column(nullable = false)
	private int foundationDate;
	@OneToMany(mappedBy = "party")
	private List<Associate> associates;

}
