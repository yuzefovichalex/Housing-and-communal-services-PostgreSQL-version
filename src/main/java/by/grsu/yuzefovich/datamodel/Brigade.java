package by.grsu.yuzefovich.datamodel;

import java.io.Serializable;

public class Brigade extends AbstractModel implements Serializable{
	
	private Integer numberOfWorkers;
	
	public Integer getNumberOfWorkers() {
		return numberOfWorkers;
	}

	public void setNumberOfWorkers(Integer numberOfWorkers) {
		this.numberOfWorkers = numberOfWorkers;
	}

	public Brigade (Long id) {
		this.setId(id);
	}
	
	public Brigade() {
		
	}
	
	public Brigade(Integer numberOfWorkers) {
		this.numberOfWorkers = numberOfWorkers;
	}

}
