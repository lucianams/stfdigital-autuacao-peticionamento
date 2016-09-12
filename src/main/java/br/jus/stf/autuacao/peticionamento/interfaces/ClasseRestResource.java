package br.jus.stf.autuacao.peticionamento.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.autuacao.peticionamento.domain.model.suportejudicial.ClassePeticionavelRepository;
import br.jus.stf.autuacao.peticionamento.interfaces.dto.ClassePeticionavelDto;
import br.jus.stf.autuacao.peticionamento.interfaces.dto.ClassePeticionavelDtoAssembler;

/**
 * Serviço REST de classes peticionáveis.
 * 
 * @author anderson.araujo
 * @since 09/05/2016
 *
 */
@RestController
@RequestMapping("/api/classes")
public class ClasseRestResource {
	
	@Autowired
	private ClassePeticionavelRepository classePeticionavelRepository;
	
	@Autowired
	private ClassePeticionavelDtoAssembler classePeticionavelDtoAssembler;
	
	/**
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<ClassePeticionavelDto> listar() {
		return classePeticionavelRepository.findAll().stream().map(classePeticionavelDtoAssembler::toDto).collect(Collectors.toList());
	}
}
