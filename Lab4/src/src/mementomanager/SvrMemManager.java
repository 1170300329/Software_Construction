package src.mementomanager;

import src.statememento.ServerMemento;

public class SvrMemManager {
	private ServerMemento memento;

	public ServerMemento getMemento() {
		return memento;
	}

	public void setMemento(ServerMemento memento) {
		this.memento = memento;
	}
	
}
