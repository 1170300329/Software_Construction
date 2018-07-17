package strategy;

import java.util.Set;
import ladder.Ladder;
import monkey.Monkey;

public interface Strategy {
  public Ladder execute(Monkey monkey, Set<Ladder> set);
}
