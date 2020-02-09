package withPositionalScoringRule;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GenerateData {
	public static void main(String args[]) {
//		for (int i = 0; i < 10; i++) {
//    		int random = new Random().nextInt(10);
//        	System.out.println(random);
//		}
		
		int m = 5;//10个候选人
		int n = 500000;//100个投票者
		String[] data=new String[n];
		for (int i = 0; i < n; i++) {
			int length = new Random().nextInt(4)+2;
			//System.out.println("下一个preference长度为："+length);
			String s="";
			Set<Integer> set = new HashSet<Integer>();
			for (int j = 0; j < length; j++) {
				
				Integer random = new Random().nextInt(5)+1;
				
				if(!set.contains(random)) {
					set.add(random);
					s = s+"c"+random+" ";
				}
				else {
					j--;
				}
				
			}
			s=s.trim();
			System.out.println(s);
			data[i]=s;
		}
        writeFile(data);
    }
    /**
     * 写入TXT文件
     */
    public static void writeFile(String[] data) {
    	int count = 0;
    	
    	
        try {
            File writeName = new File("data.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
            	out.write("c1 c2 c3 c4 c5\r\n");
            	for (String string : data) {
					out.write(string+"\r\n");
				}
//                out.write("我会写入文件啦1\r\n"); // \r\n即为换行
//                out.write("我会写入文件啦2\r\n"); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("写入成功");
    }

}
