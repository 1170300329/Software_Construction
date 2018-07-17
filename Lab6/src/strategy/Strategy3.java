package strategy;

import java.util.Set;
import ladder.Ladder;
import log.MyLog;
import monkey.Monkey;

public class Strategy3 implements Strategy {
  /**
   * 优先选择没有猴子的梯子，如果都有的话，选择一个与我方运动方向相同，最后一只猴子行进速度最快的梯子。如果方向都相反，则等待。
   */
  @Override
  public Ladder execute(Monkey monkey, Set<Ladder> set) {
    while (true) {
      int fast = -1;
      Ladder templadder = null;
      for (Ladder ladder : set) {
        synchronized (ladder) {
          if (ladder.getStateAndSet() == false) {
            ladder.setCnt(1);
            ladder.setDirection(monkey.getDirection());
            return ladder;
          } else if (ladder.getDiection().equals(monkey.getDirection())
              && ladder.getList().get(1).getState() == false) {
            if (ladder.getSlowv() > fast) {
              fast = ladder.getSlowv();
              templadder = ladder;
            }
          }
        }
      }
      if (templadder != null) {
        synchronized (templadder) {
          templadder.setCnt(templadder.getCnt() + 1);
          return templadder;
        }
      }
      long temp = System.currentTimeMillis() - monkey.getBegin();
      if (temp > 0 && temp % 1000 == 0)
        MyLog.logger.info("猴子" + monkey.getID() + "已经等待" + temp + "ms");
    }
  }
}

