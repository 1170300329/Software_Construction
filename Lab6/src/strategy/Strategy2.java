package strategy;

import java.util.Set;
import ladder.Ladder;
import log.MyLog;
import monkey.Monkey;

public class Strategy2 implements Strategy {
  /**
   * 优先选择没有猴子的梯子，若所有梯子上都有猴子，则在岸边等待，直到某个梯子空出来
   */
  @Override
  public Ladder execute(Monkey monkey, Set<Ladder> set) {
    while (true) {
      for (Ladder ladder : set) {
        synchronized (ladder) {
          if (ladder.getStateAndSet() == false) {
            ladder.setCnt(1);
            ladder.setDirection(monkey.getDirection());
            return ladder;
          }
        }
      }
      long temp = System.currentTimeMillis() - monkey.getBegin();
      if (temp > 0 && temp % 1000 == 0)
        MyLog.logger.info("猴子" + monkey.getID() + "已经等待" + temp + "ms");
    }
  }
}
