/**
 * Classe de Componente para suporte a lista de Ordem de Compras (MsCotacao.class)
 * Não alterar essa classe!
 */
package br.inf.ctis.ms.bean;

import java.util.Comparator;

/**
 * @author willians.andrade
 *
 */
class Component implements Comparator<Component> {
	private String id;  
    private String partNumber;
    private Integer quantity;
    
    public Component() {}
    
    public Component(String id, String partNumber, Integer quantity) {
    	this.id = id;
    	this.partNumber = partNumber;
    	this.quantity = quantity;
    }
    
	public String getId() {
		return id;
	}
	
	public String getPartNumber() {
		return partNumber;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public int compare(Component o1, Component o2) {
		return o1.getPartNumber().compareToIgnoreCase(o2.getPartNumber());
	} 
}
