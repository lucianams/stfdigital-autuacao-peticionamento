package br.jus.stf.autuacao.peticionamento.interfaces.tarefas;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 22.03.2015
 */
public class RotuloDto {

    private Long id;
    private String name;
    private String label;
    private String color;
    
	public RotuloDto(Long id, String name, String label, String color) {
		this.id = id;
		this.name = name;
		this.label = label;
		this.color = color;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getColor() {
		return color;
	}

}
