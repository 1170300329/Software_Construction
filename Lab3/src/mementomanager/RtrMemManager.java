package mementomanager;

import statememento.RouterMemento;

public class RtrMemManager {
	private RouterMemento memento;

	public RouterMemento getMemento() {
		return memento;
	}

	public void setMemento(RouterMemento memento) {
		this.memento = memento;
	}
}