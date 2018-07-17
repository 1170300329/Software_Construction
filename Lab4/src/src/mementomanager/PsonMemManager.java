package src.mementomanager;

import src.statememento.PersonMemento;

public class PsonMemManager {
	private PersonMemento memento;

	public PersonMemento getMemento() {
		return memento;
	}

	public void setMemento(PersonMemento memento) {
		this.memento = memento;
	}
}
