package src.mementomanager;

import src.statememento.ComputerMemento;

public class CptMemManager {
	private ComputerMemento memento;

	public ComputerMemento getMemento() {
		return memento;
	}

	public void setMemento(ComputerMemento memento) {
		this.memento = memento;
	}
	
}
