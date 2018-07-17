package P1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MagicSquare {

	public static void main(String[] args) {
		MagicSquare test = new MagicSquare();
		boolean rig=MagicSquare.generateMagicSquare(3);
		test.isLegalMagicSquare("src/P1/txt/1.txt");
		test.isLegalMagicSquare("src/P1/txt/2.txt");
		test.isLegalMagicSquare("src/P1/txt/3.txt");
		test.isLegalMagicSquare("src/P1/txt/4.txt");
		test.isLegalMagicSquare("src/P1/txt/5.txt");
		if(rig==true)test.isLegalMagicSquare("src/P1/txt/6.txt");
//		Scanner in = new Scanner(System.in);
//		String s ="";
//		System.out.println("请输入测试文件相对路径,测试结束请输入“#”");
//		s = in.nextLine();
//		while (!s.equals("#")) {
//			test.isLegalMagicSquare(s);
//			s = in.nextLine();
//		}
//		System.out.println("请输入要生成的magicsquare的阶数");
//		int n=Integer.parseInt(in.nextLine());
//		boolean rig=MagicSquare.generateMagicSquare(n);
//		
//		if(rig==true) {
//		System.out.println("请输入放置magicsquare的位置");
//		s=in.nextLine();
//		test.isLegalMagicSquare(s);
//		}
		
	}
	public static boolean generateMagicSquare(int n) {
		if (n % 2 == 0) {
			System.out.println("false");
			return false;
		}
		if (n <= 0) {
			System.out.println("false");
			return false;
		}
		int magic[][] = new int[n][n];
		int row = 0, col = n / 2, i, j, square = n * n;
		for (i = 1; i <= square; i++) {
			magic[row][col] = i;
			if (i % n == 0)
				row++;
			else {
				if (row == 0)
					row = n - 1;
				else
					row--;
				if (col == (n - 1))
					col = 0;
				else
					col++;
			}
		}
		try {
			FileWriter fw = new FileWriter("src/P1/txt/6.txt");
			for (i = 0; i < n; i++) {
				for (j = 0; j < n; j++)
					fw.write(magic[i][j] + "\t");
				fw.write("\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	boolean isLegalMagicSquare(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		int line = 0;
		int lie = 0;
		int[][] testNum = new int[1000][1000];
		String[] temp;
		int lie1 = 0;
		int sum = 0;
		int cmp = 0;

		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				temp = tempString.split("\t");
				lie = temp.length;
				if (line == 0) {
					lie1 = lie;
				}
				if (lie1 != lie) {
					System.out.println("不是矩阵");
					return false;
				}
				for (int i = 0; i < lie; i++) {
					if (temp[i].contains(".")) {
						System.out.println("有小数点");
						return false;
					}
					if (temp[i].contains("-")) {
						System.out.println("有负数");
						return false;
					}
					boolean result = temp[i].matches("[0-9]+");
					if (result == false) {
						System.out.println("有非table");
						return false;
					}
					try {
						if (Integer.valueOf(temp[i]) == 0) {
							System.out.println("有0");
							return false;
						}
						testNum[line][i] = Integer.valueOf(temp[i]);
					} catch (NumberFormatException e) {
						System.out.println("有非法数字");
						return false;
					}
				}
				line++;
			}
			if (line != lie) {
				System.out.println("行列数不相等" + line + " " + lie);
				return false;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int j = 0; j < lie; j++) {
			sum = 0;
			for (int k = 0; k < line; k++) {
				sum = sum + testNum[k][j];
			}
			if (j == 0)
				cmp = sum;
			if (sum != cmp) {
				System.out.println("列不相等");
				return false;
			}
		}

		for (int j = 0; j < line; j++) {
			sum = 0;
			for (int k = 0; k < lie; k++) {
				sum = sum + testNum[j][k];
			}
			if (j == 0)
				cmp = sum;
			if (sum != cmp) {
				System.out.println("行不相等");
				return false;
			}
		}

		sum = 0;
		for (int i = 0; i < line; i++) {

			sum = sum + testNum[i][i];
		}
		if (sum != cmp) {
			System.out.println("对角线不等");
			return false;
		}

		sum = 0;
		for (int i = 0; i < line; i++) {

			sum = sum + testNum[i][line - 1 - i];
		}
		if (sum != cmp) {
			System.out.println("对角线不等");
			return false;
		}

		System.out.println("相等");
		return true;
	}
}
