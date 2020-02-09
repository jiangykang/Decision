package withBucklin;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Data {
	String[] allCandidate;
	//List<String[]> list;
	List<List<int[]>> list;
	List<int[]> list_temp;
	String pathname;
	public Data(String pathname) {
		super();
		this.pathname = pathname;
		readFile();
	}

	public static void main(String args[]) {
		Data data = new Data("data_test.txt");
        data.readFile();
        for (String string : data.allCandidate) {
			System.out.println(string);
		}
        for (List is : data.list) {
        	
			for (int[] is2 : (List<int[]>)is) {
				System.out.println("序列对为:"+is2[0]+","+is2[1]);
			}
			System.out.println();
			
		}
    }

    /**
     * 读入TXT文件
     */
    public void readFile() {
    	list = new ArrayList<List<int[]>>();
    	int count =0;
  //      String pathname = "data.txt"; // 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271       
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
        	allCandidate = br.readLine().split(" ");
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
//                System.out.println(line);
                String[] input = line.split(" ");
                list_temp = new ArrayList<int[]>();
                for (int i = 0; i < input.length; i++) {
					String s = input[i];
					String[] a = s.split("-");
					int[] b = new int[2];
					b[1] = Integer.parseInt(a[1]);
					b[0] = Integer.parseInt(a[0]);
					list_temp.add(b);		
				}
                list.add(list_temp);
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        System.out.println("读取数据成功，数据条数："+count);
        System.out.println("数据集："+pathname);
    }
}
