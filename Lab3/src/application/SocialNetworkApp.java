package application;

import java.util.Scanner;

import factory.GraphFactory;
import graph.ConcreteGraph;
import helper.ParseCommandHelper;

public class SocialNetworkApp {
	public static void main(String[] args) throws Exception {
//		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test2.txt");
//		System.out.println(g.toString());
//		Person person=new Person("Emma");
//		Person person2=new Person("Edith");
//		System.out.println("_____"+GraphMetrics.distance(g, person, person2));
//		System.out.println("closenessCentrality of "+person.getLabel()+":"+GraphMetrics.closenessCentrality(g,person));
//		System.out.println("betweennessCentrality of "+person.getLabel()+":"+GraphMetrics.betweennessCentrality(g,person));
//		System.out.println("degreeCentrality:"+GraphMetrics.degreeCentrality(g));
//		System.out.println("degreeCentrality of "+person.getLabel()+":"+GraphMetrics.degreeCentrality(g, person));
//		System.out.println("diameter:"+GraphMetrics.diameter(g));
//		System.out.println("eccentricity of "+person.getLabel()+":"+GraphMetrics.eccentricity(g, person));
//		System.out.println("inDegreeCentrality of "+person.getLabel()+":"+GraphMetrics.inDegreeCentrality(g, person));
//		System.out.println("outDegreeCentrality of "+person.getLabel()+":"+GraphMetrics.outDegreeCentrality(g, person));
//		System.out.println("radius:"+GraphMetrics.radius(g));
		System.out.println(">------输入\"exit\"退出,\"cmd --help\"可以查看帮助。(字符串之间用空格隔开，请不要用引号！)-----");
		Scanner sb = new Scanner(System.in);
		String filepath=null;
		System.out.println(">请输入读入文件路径：");
		System.out.print(">");
		filepath=sb.nextLine();
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph(filepath);
//		System.out.print(">");
		String temp=null;
//		if(temp.equals("#")) {
			while(true) {
				System.out.print(">");
				temp=sb.nextLine();
				if(!temp.equals("exit")) {
					ParseCommandHelper.parseAndExecuteCommand(temp, g,sb);
				}else {
					System.out.println("Exit");
					break;
				}
			}
		sb.close();
		//GraphVisualizationHelper.visualize(g);
//		System.out.println("___________");
//		System.out.println(GraphMetrics1.closenessCentrality(g,person));
//		System.out.println(GraphMetrics1.betweennessCentrality(g,person));
//		System.out.println("_____"+GraphMetrics1.distance(g, person, person2));
		return;
	}
}
