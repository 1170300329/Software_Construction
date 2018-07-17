package src.vertex;

import src.exception.AttrErrorException;
import src.exception.InvalidCmdException;
import src.log.MyLog;

public class Movie extends Vertex {
  private static final long serialVersionUID = 1L;
  private int year;
  private String position;
  private double IMDb;
  // Abstraction function:
  // the year represents the year when the movie was showed firstly
  // the position is a String representing the position where the movie was made
  // the IMDb represents the score of IMDb
  // Representation invariant:
  // the year shoud ranges from 1900 to 2018([1900,2018)),the position should be a
  // string which
  // is not null,the IMDb shoud ranges form 0 to 10 and with at most two decimal
  // places.
  // Safety from rep exposure:
  // all the fields are private and immutable.


  /**
   * new a movie with label
   * 
   * @param label
   */
  public Movie(String label) {
    super(label);
  }

  /**
   * fill the information of a vertex with args
   * 
   * @throws Exception
   */
  @Override
  public void fillVertexInfo(String[] args) throws Exception {
    // if (args[0].length() != 4) {
    // MyLog.logger.error("AttrErrorException:属性不合法");
    // throw new AttrErrorException("属性不合法");
    // }
    int year;
    try {
      year = Integer.valueOf(args[0]);
    } catch (Exception e) {
      MyLog.logger.error("AttrErrorException:属性不合法");
      throw new AttrErrorException("属性不合法");
    }
    if (year >= 1900 && year < 2018) {
      this.year = year;
    } else {
      MyLog.logger.error("AttrErrorException:属性不合法");
      throw new AttrErrorException("属性不合法");
    }

    this.position = args[1];
    // this.IMDb=Double.valueOf(args[2]);
    if (!args[2].matches("(([1-9][0-9]*)|(([0]\\.\\d{1,2}|[1-9][0-9]*\\.\\d{1,2})))")) {
      MyLog.logger.error("AttrErrorException:属性不合法");
      throw new AttrErrorException("属性不合法");
    }
    double IMDb;
    try {
      IMDb = Double.valueOf(args[2]);
    } catch (Exception e) {
      MyLog.logger.error("AttrErrorException:属性不合法");
      throw new AttrErrorException("属性不合法");
    }
    if (IMDb > 0 && IMDb < 10) {
      this.IMDb = IMDb;
    } else {
      MyLog.logger.error("AttrErrorException:属性不合法");
      throw new AttrErrorException("属性不合法");
    }
  }

  /**
   * get the year
   * 
   * @return year
   */
  public int getYear() {
    return year;
  }

  /**
   * get the position
   * 
   * @return position
   */
  public String getPosition() {
    return position;
  }

  /**
   * get the IMDb
   * 
   * @return IMDb
   */
  public double getIMDb() {
    return IMDb;
  }

  /**
   * override the toString() to show the detail information of the movie
   */
  @Override
  public String toString() {
    return "Movie [label=" + this.getLabel() + " year=" + year + ", position=" + position
        + ", IMDb=" + IMDb + "]";
  }

  /**
   * change the attrs in vertex
   * 
   * @param group
   * @param group2
   * @throws InvalidCmdException
   */
  @Override
  public void changeAttr(String group, String group2) throws InvalidCmdException {
    if (group.equals("year")) {
      if (group2.length() != 4) {
        MyLog.logger.error("InvalidCmdException:修改后的参数不合法");
        throw new InvalidCmdException("修改后的参数不合法");
      }
      int year;
      try {
        year = Integer.valueOf(group2);
      } catch (Exception e) {
        MyLog.logger.error("InvalidCmdException:修改后的参数不合法");
        throw new InvalidCmdException("修改后的参数不合法");
      }
      if (year >= 1900 && year < 2018) {
        this.year = year;
      } else {
        MyLog.logger.error("InvalidCmdException:修改后的参数不合法");
        throw new InvalidCmdException("修改后的参数不合法");
      }
    } else if (group.equals("position")) {
      this.position = group2;
    } else if (group.equals("IMDb")) {
      double IMDb;
      // this.IMDb=Double.valueOf(group2);
      if (!group2.matches("(([1-9][0-9]*)|(([0]\\.\\d{1,2}|[1-9][0-9]*\\.\\d{1,2})))")) {
        MyLog.logger.error("InvalidCmdException:修改后的参数不合法");
        throw new InvalidCmdException("修改后的参数不合法");
      }
      try {
        IMDb = Double.valueOf(group2);
      } catch (Exception e) {
        MyLog.logger.error("InvalidCmdException:修改后的参数不合法");
        throw new InvalidCmdException("修改后的参数不合法");
      }
      if (IMDb > 0 && IMDb < 10) {
        this.IMDb = IMDb;
      } else {
        MyLog.logger.error("InvalidCmdException:修改后的参数不合法");
        throw new InvalidCmdException("修改后的参数不合法");
      }
    } else {
      MyLog.logger.error("InvalidCmdException:修改后的参数不合法");
      throw new InvalidCmdException("要修改的属性不存在");
    }
  }

}
