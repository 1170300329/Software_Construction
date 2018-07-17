package ladder;

import monkey.Monkey;

public class Board {
  private Monkey monkey = null;
  private boolean state = false;

  // Abstraction function:
  // monkey represents the monkey which is on the board currently.state represents whether the board
  // is free.
  // Representation invariant:
  // if monkey == null,state=false;if monkey !=null,state=true.
  // Safety from rep exposure:
  // state is private and it's primitive variant,monkey is private and it can be changed with
  // limited method.
  // Thread safety argument:
  // state is immutable,so it is thread-safe.Monkey's change is synchronized.Although the value of
  // state is associated with the value of monkey,it is still thread-safe because setMonkey() is
  // synchronized,which change the monkey and state at the same synchronized block.
  public void setMonkey(Monkey monkey) {
    synchronized (this) {
      this.monkey = monkey;
      if (monkey != null)
        state = true;
      else
        state = false;
    }
  }

  public boolean getState() {
    synchronized (this) {
      return state;
    }
  }
}
