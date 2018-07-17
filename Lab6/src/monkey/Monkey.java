package monkey;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ladder.Ladder;
import log.MyLog;
import strategy.Strategy;
import strategy.Strategy1;
import strategy.Strategy2;
import strategy.Strategy3;

public class Monkey implements Runnable {
  private final int ID;
  private final String direction;
  private final int v;
  private int hpms;
  private Set<Ladder> set = Collections.synchronizedSet(new HashSet<>());
  private long begin;
  private List<Integer> list;

  // Abstraction function:
  // The ID represents the ID number of the monkey.The direction represents the direction of the
  // monkey.The v represents the value of the speed of the monkey.The hpms represents the actual
  // speed of the monkey.The set represents all the Ladder shared by all the threads.The begin
  // represents the birth time of the monkey.The list represents the list shared by all the
  // monkeys,which records the order of the end time of the threads.
  // Representation invariant:
  // ID>0;1<=v&&v<=MV.
  // direction==null||direction=="R->L"||direction=="L->R".
  // 0<=hpms&&hpms<=v;set!=null;begin>=0;list!=null.
  // Safety from rep exposure:
  // All the fields are private.ID,direction and v are final.ID,direction,v,hpms and begin are
  // immutable or primitive data type.There are no methods to return list or set.
  // Thread safety argument:
  // The list and set shared by all threads are synchronized,other data in the class is not shared
  // by other threads,so it is thread-safe.
  public Monkey(int ID, String direction, int v, Set<Ladder> set, List<Integer> list) {
    this.ID = ID;
    this.direction = direction;
    this.v = v;
    this.set = set;
    begin = System.currentTimeMillis();
    this.list = Collections.synchronizedList(list);
    MyLog.logger.info("猴子" + ID + "出生: " + "速度：" + v + " 方向： " + direction);
    System.out.println("猴子" + ID + "出生: " + "速度：" + v + " 方向： " + direction);
  }

  public int getID() {
    return ID;
  }

  public int getHpms() {
    return hpms;
  }

  public void setHpms(int hpms) {
    this.hpms = hpms;
  }

  public String getDirection() {
    return direction;
  }

  public int getV() {
    return v;
  }

  public long getBegin() {
    return begin;
  }

  @Override
  public void run() {
    Strategy strategy = null;
    int random = (int) ((Math.random() * 3) + 1);

    random = 3;// 注释掉即可随机选择策略

    Ladder ladder = null;
    if (random == 1) {
      strategy = new Strategy1();
      ladder = strategy.execute(this, set);
    } else if (random == 2) {
      strategy = new Strategy2();
      ladder = strategy.execute(this, set);
    } else if (random == 3) {
      strategy = new Strategy3();
      ladder = strategy.execute(this, set);
    }
    int currentPosition = 1;
    ladder.getList().get(currentPosition).setMonkey(this);
    while (currentPosition <= 20) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      synchronized (ladder) {
        if (currentPosition == 20) {
          ladder.getList().get(currentPosition).setMonkey(null);
          ladder.setCnt(ladder.getCnt() - 1);
          if (ladder.getCnt() == 0) {
            ladder.setState(false);
          }
          list.add(this.getID());
          break;
        }
        int i = 0;
        for (i = currentPosition + 1; i <= currentPosition + this.getV(); i++) {
          if (i > 20)
            break;
          if (ladder.getList().get(i).getState() == false) {
            continue;
          } else {
            break;
          }
        }
        // if (i == 21 && currentPosition + this.getV() > 20) {
        // ladder.getList().get(currentPosition).setMonkey(null);
        // ladder.setCnt(ladder.getCnt() - 1);
        // if (ladder.getCnt() == 0) {
        // ladder.setState(false);
        // }
        // list.add(this.getID());
        // break;
        // }
        ladder.getList().get(i - 1).setMonkey(this);
        this.setHpms(i - 1 - currentPosition);
        if ((i - 1) != currentPosition)
          ladder.getList().get(currentPosition).setMonkey(null);
        currentPosition = i - 1;
        MyLog.logger.info("猴子" + this.getID() + "在" + ladder.getID() + "桥" + (i - 1) + "格" + " 速度："
            + this.getV() + " 实际速度：" + this.getHpms() + " 方向： " + this.getDirection() + " 距离出生时间："
            + (System.currentTimeMillis() - this.getBegin()) + "ms");
      }
    }
    MyLog.logger.info("猴子" + this.getID() + "已经过桥" + " 速度：" + this.getV() + " 方向： "
        + this.getDirection() + " 距离出生时间：" + (System.currentTimeMillis() - this.getBegin()) + "ms");
  }
}
