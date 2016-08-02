package br.jus.stf.autuacao.peticionamento.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.ApiOperation;

import br.jus.stf.autuacao.peticionamento.domain.model.documento.TipoAnexoRepository;
import br.jus.stf.autuacao.peticionamento.interfaces.dto.TipoAnexoDto;
import br.jus.stf.autuacao.peticionamento.interfaces.dto.TipoAnexoDtoAssembler;

/**
 * Serviços REST relacionados aos anexos.
 * 
 * @author Tomas.Godoi
 * @since 0.0.1
 *
 */
@RestController
@RequestMapping("/api/anexos")
public class AnexoRestResource {

	@Autowired
	private TipoAnexoRepository tipoAnexoRepository;
	
	@Autowired
	private TipoAnexoDtoAssembler tipoAnexoDtoAssembler;
	
	/**
	 * @return
	 */
	@ApiOperation("Retorna os tipos de anexo possíveis para um anexo.")
	@RequestMapping(value = "/tipos", method = RequestMethod.GET)
	public List<TipoAnexoDto> tipos() {
		return tipoAnexoRepository.findAll().stream()
				.map(tipoAnexoDtoAssembler::toDto).collect(Collectors.toList());
	}
	
}
