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
		
		int m = 5;//10����ѡ��
		int n = 500000;//100��ͶƱ��
		String[] data=new String[n];
		for (int i = 0; i < n; i++) {
			int length = new Random().nextInt(4)+2;
			//System.out.println("��һ��preference����Ϊ��"+length);
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
     * д��TXT�ļ�
     */
    public static void writeFile(String[] data) {
    	int count = 0;
    	
    	
        try {
            File writeName = new File("data.txt"); // ���·�������û����Ҫ����һ���µ�output.txt�ļ�
            writeName.createNewFile(); // �������ļ�,��ͬ�����ļ��Ļ�ֱ�Ӹ���
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
            	out.write("c1 c2 c3 c4 c5\r\n");
            	for (String string : data) {
					out.write(string+"\r\n");
				}
//                out.write("�һ�д���ļ���1\r\n"); // \r\n��Ϊ����
//                out.write("�һ�д���ļ���2\r\n"); // \r\n��Ϊ����
                out.flush(); // �ѻ���������ѹ���ļ�
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("д��ɹ�");
    }

}
