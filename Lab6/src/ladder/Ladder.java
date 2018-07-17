package ladder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ladder {
  private final List<Board> list = Collections.synchronizedList(new ArrayList<>());
  private boolean state = false;
  private String direction = null;
  private int cnt = 0;
  private int ID;
  private int slowv = 0;

  // Abstraction function:
  // The list represents the Boards contained in the Ladder.The state represents whether the Ladder
  // is free.The direction represents the direction of the monkeys on the Ladder.The cnt represents
  // the number of the monkeys on the Ladder.The ID represents the ID number of the Ladder.The slowv
  // represents the actual speed of the last monkey on the ladder.
  // Representation invariant:
  // list!=null.
  // list.size()==20.
  // state=false||state==true.
  // direction ==null||direction.equals("L->R")||direction.equals("R->L").
  // cnt>=0;ID>0;slowv>=0.
  // Safety from rep exposure:
  // state,direction,cnt,ID and slowv are immutable or primitive data type,which is safe from rep
  // exposure.list is private and final,and the list can be only changed with limited methods.
  // Thread safety argument:
  // state,direction,cnt,ID and slowv are immutable or primitive data type,which is thread-safe.list
  // is private and final and it is synchronized and the elements in it is thread-safe,so the method
  // with list is thread-safe.All the methods in the class is synchronized,so it is thread-safe.
  public int getSlowv() {
    synchronized (this) {
      return slowv;
    }
  }

  public void setSlowv(int slowv) {
    synchronized (this) {
      this.slowv = slowv;
    }
  }

  public Ladder(int ID) {
    list.add(new Board());
    for (int i = 1; i <= 20; i++) {
      list.add(new Board());
    }
    this.ID = ID;
  }

  public int getID() {
    synchronized (this) {
      return ID;
    }
  }

  public void setDirection(String direction) {
    synchronized (this) {
      this.direction = direction;
    }
  }

  public void setState(boolean state) {
    synchronized (this) {
      this.state = state;
    }
  }

  public int getCnt() {
    synchronized (this) {
      return cnt;
    }
  }

  public void setCnt(int cnt) {
    synchronized (this) {
      this.cnt = cnt;
    }
  }

  public boolean getState() {
    synchronized (this) {
      return state;
    }
  }

  public List<Board> getList() {
    synchronized (this) {
      return list;
    }
  }

  public String getDiection() {
    synchronized (this) {
      return direction;
    }
  }

  public boolean getStateAndSet() {
    synchronized (this) {
      if (state == false) {
        state = true;
        return false;
      } else {
        return true;
      }
    }
  }
}
