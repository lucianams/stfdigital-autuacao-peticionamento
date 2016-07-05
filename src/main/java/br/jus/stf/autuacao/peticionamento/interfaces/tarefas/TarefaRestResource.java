package br.jus.stf.autuacao.peticionamento.interfaces.tarefas;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;
import java.util.stream.Collectors;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
@RestController
@RequestMapping("/api/tarefas")
public class TarefaRestResource {

	@Autowired
    private RuntimeService runtimeService;
	
	@Autowired
    private TaskService taskService;
	
    @ApiOperation(value = "Lista todas as tarefas associadas ao usuário corrente")
	@RequestMapping(method = GET)
	public List<TarefaDto> tarefas() {    	
        return taskService.createTaskQuery().list().stream()
        		.map(task -> toDto((TaskEntity) task))
        		.collect(Collectors.toList());
	}

	private TarefaDto toDto(TaskEntity task) {
		ProcessInstance process = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		//TODO Implementar serviço que recupera os rótulos
		return new TarefaDto(process.getBusinessKey(), task);
	}

}
