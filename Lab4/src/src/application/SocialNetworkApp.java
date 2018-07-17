package src.application;

import java.util.Scanner;

import src.factory.GraphFactory;
import src.graph.ConcreteGraph;
import src.helper.ParseCommandHelper;
import src.log.MyLog;

public class SocialNetworkApp {
	public static void main(String[] args) throws Exception {
		System.out.println(">------输入\"exit\"退出,\"cmd --help\"可以查看帮助。(字符串之间用空格隔开，请不要用引号！)-----");
		Scanner sb = new Scanner(System.in);
		String filepath=null;
			while(true) {
				System.out.println(">请输入读入文件路径：");
				System.out.print(">");
				filepath=sb.nextLine();
				ConcreteGraph g=null;
				try {
					g=(ConcreteGraph) GraphFactory.createGraph(filepath);
					MyLog.logger.info("读取文件："+filepath);
				}catch (Exception e) {
					System.out.println(e.getMessage());
					continue;
				}
				while(true) {
				String temp=null;
				System.out.print(">");
				temp=sb.nextLine();
				if(!temp.equals("exit")) {
					try {
					ParseCommandHelper.parseAndExecuteCommand(temp, g,sb);
					}catch (Exception e) {
						System.out.println(e.getMessage());
						continue;
					}
				}else {
					System.out.println("Exit");
					sb.close();
					return;
				}
				}
			}
	}
}