package P3;

import java.io.IOException;
import java.util.Scanner;

public class RoutePlannerGUI {
	
	public static void main(String []args) throws IOException {
		RoutePlannerBuilder routePlannerBuilder=new RoutePlannerBuilder();
		Scanner sb = new Scanner(System.in);
		
		System.out.println("--------------请输入出发站点以及目标站点的名字和经纬度(用逗号隔开)，格式如下：-------------------");
		System.out.println("name,latitude,longtitude");
		String[] temp1=null;
		String[] temp2=null;
		try {
		String String1=sb.nextLine();
		temp1=String1.split(",");
		String String2=sb.nextLine();
		temp2=String2.split(",");
		}catch (Exception e) {
			System.out.println("输入信息有误，请检查后重新输入");
			System.exit(0);
		}
		Stop src=new Stop("",temp1[0],Double.valueOf(temp1[1]),Double.valueOf(temp1[2]),0);
		Stop dest=new Stop("",temp2[0],Double.valueOf(temp2[1]),Double.valueOf(temp2[2]),0);
		System.out.println("--------------------------------请输入准备出发时间---------------------------------------");
		int time=sb.nextInt();
		Itinerary ans=routePlannerBuilder.build("src/P3/inputdata.txt", 1200).computeRoute(src, dest, time);
		sb.close();
		System.out.println(ans.getInstructions());
		
	}
}
