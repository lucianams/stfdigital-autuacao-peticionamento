package br.jus.stf.autuacao.peticionamento.interfaces.tarefas;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 23.06.2015
 */
public class TarefaDto {

    private String id;
    
    private String title;
    
    private String command;
    
    private Date startDate;

    private Date dueDate;

    private boolean completed;
    
    private boolean starred;

    private boolean important;
    
    private String notes;
    
    private List<RotuloDto> tags = new LinkedList<RotuloDto>();

	public TarefaDto(String id, TaskEntity task) {
		this.id = id;
		this.title = task.getName();
		this.command = task.getTaskDefinitionKey();
		this.startDate = task.getCreateTime();
		this.dueDate = task.getDueDate();
		this.completed = DelegationState.RESOLVED.equals(task.getDelegationState()) || task.isDeleted();
		this.starred = Boolean.TRUE.equals(task.getTaskLocalVariables().get("starred"));
		this.important = task.getPriority() > Task.DEFAULT_PRIORITY;
		this.notes = (String) task.getTaskLocalVariables().get("notes");
	}
	
	public String getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getCommand() {
		return command;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	
	public boolean isCompleted() {
		return completed;
	}
	
	public boolean isStarred() {
		return starred;
	}

	public boolean isImportant() {
		return important;
	}
	
	public String getNotes() {
		return notes;
	}
	
	public List<RotuloDto> getTags() {
		return tags;
	}

}

