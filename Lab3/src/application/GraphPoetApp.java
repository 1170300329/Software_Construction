package application;

import java.util.Scanner;

import factory.GraphFactory;
import graph.ConcreteGraph;
import helper.ParseCommandHelper;

public class GraphPoetApp {
	public static void main(String[] args) throws Exception {
//		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test3.txt");
//		System.out.println(g.toString());
//		Word word=new Word("seek");
//		System.out.println(GraphMetrics.closenessCentrality(g,word));
//		System.out.println(GraphMetrics.betweennessCentrality(g,word));
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
//			System.out.println("Exit");
//		}else if(temp.equals("exit")) {
//			sb.close();
//			System.out.println("Exit");
//		}else {
//			System.out.println("请重新输入'#'或者exit");
//		}
		sb.close();
//		GraphVisualizationHelper.visualize(g);
//		System.out.println("___________");
//		System.out.println(GraphMetrics1.closenessCentrality(g,word));
//		System.out.println(GraphMetrics1.betweennessCentrality(g,word));
		return;
	}
}
