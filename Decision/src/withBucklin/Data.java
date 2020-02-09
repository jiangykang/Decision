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
				System.out.println("���ж�Ϊ:"+is2[0]+","+is2[1]);
			}
			System.out.println();
			
		}
    }

    /**
     * ����TXT�ļ�
     */
    public void readFile() {
    	list = new ArrayList<List<int[]>>();
    	int count =0;
  //      String pathname = "data.txt"; // ����·�������·�������ԣ�д���ļ�ʱ��ʾ���·��,��ȡ����·����input.txt�ļ�
        //��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw;
        //���ر��ļ��ᵼ����Դ��й¶����д�ļ���ͬ��
        //Java7��try-with-resources�������Źر��ļ����쳣ʱ�Զ��ر��ļ�����ϸ���https://stackoverflow.com/a/12665271       
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) // ����һ�����������ļ�����ת�ɼ�����ܶ���������
        ) {
        	allCandidate = br.readLine().split(" ");
            String line;
            //�����Ƽ����Ӽ���д��
            while ((line = br.readLine()) != null) {
                // һ�ζ���һ������
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
       
        System.out.println("��ȡ���ݳɹ�������������"+count);
        System.out.println("���ݼ���"+pathname);
    }
}
