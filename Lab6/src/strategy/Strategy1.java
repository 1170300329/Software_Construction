package strategy;

import java.util.Set;
import ladder.Ladder;
import log.MyLog;
import monkey.Monkey;

public class Strategy1 implements Strategy {
  /**
   * 优先选择没有猴子的梯子，如果所有梯子上都有猴子， 则随机选择一个上面猴子与自己运动方向相同的梯子。 如果满足条件 的梯子有很多，则随机选择。如果满足条件的梯子没有，则原地等待。
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
          } else if (ladder.getDiection().equals(monkey.getDirection())
              && ladder.getList().get(1).getState() == false) {
            ladder.setCnt(ladder.getCnt() + 1);
            return ladder;
          }
        }
      }
      long temp = System.currentTimeMillis() - monkey.getBegin();
      if (temp % 500 == 0)
        MyLog.logger.info("猴子" + monkey.getID() + "已经等待" + temp + "ms");
    }
  }
}
