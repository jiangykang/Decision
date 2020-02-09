package garph_gnerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import garph_gnerator.RandomSort;
public class generator {
	
	public static int[] trans(int num) {
		int a[] = new int[num];
        for(int i=0;i<a.length;i++) {
        	a[i]=i+1;
        }
        RandomSort.randomSort(a);
		return a;
	}
	public static void writeTXT(String path,String title,String[] content,int N){
	    try {
	        // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
	        /* 写入Txt文件 */
	        File writename = new File(path);// 相对路径，如果没有则要建立一个新的output。txt文件
	        if(!writename.exists()){
	            writename.mkdirs();
	        }
	        writename = new File(path+"\\"+title+".txt");// 相对路径，如果没有则要建立一个新的output。txt文件
	        writename.createNewFile(); // 创建新文件
	        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
//	        out.write(content); // \r\n即为换行
	        String s = "";
	        for(int i=1;i<=N;i++) {
	        	s = s+i+" ";
	        }
	        out.write(s.trim()+"\r\n");
	        for(int i=0;i<content.length;i++) {
	        	if(i==content.length-1) {
	        		out.write(content[i]);
	        	}
	        	else {
	        		out.write(content[i]+"\r\n");
	        	}
	        	
	        }
	        out.flush(); // 把缓存区内容压入文件
	        out.close(); // 最后记得关闭文件

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public static String edgeConect(List<Integer> pre,List<Integer> now,int[] arr) {
		String content = "";
		//用于选择的随机数
		int n = pre.size();
		Random random = new Random();
		for(int i=0;i<now.size();i++) {
			int pro = 0;
			boolean tag = false;
			for(int j=0;j<pre.size();j++) {
				int select = random.nextInt(11-pro);
				if(j==pre.size()-1&&tag==false) {
					System.out.println(pre.get(j)+"-"+now.get(i));
					content += String.valueOf(arr[pre.get(j)-1])+"-"+String.valueOf(arr[now.get(i)-1])+" ";
					break;
				}
				if(select>5) {
					System.out.println(pre.get(j)+"-"+now.get(i));
					content += String.valueOf(arr[pre.get(j)-1])+"-"+String.valueOf(arr[now.get(i)-1])+" ";
					pro += 1;
					tag = true;
				}
			}
		}
		return content.trim();
	}
	
	public static String[] gen(int personNum,int num,int threshold,int choose){
		String[] text = new String[personNum];
		for(int j=0;j<personNum;j++) {
			int last = 1;
			int now =1;
			String content = "";
			//初始化第一层
			List<Integer> prelevel = new ArrayList<Integer>();
			prelevel.add(last);
			//生成当前层随机数
			int[] a = trans(choose);
			int[] b = Arrays.copyOf(a, num);
			System.out.println(Arrays.toString(b));
			while(now<num) {
				now+=1;
				Random r1 = new Random();
				List<Integer> nowlevel = new ArrayList<Integer>();
				nowlevel.add(now);
				if(now>num) break;
				if(now!=num) {
					for(int i=1;i<threshold;i++) {
						int tag = r1.nextInt(10);
						if(tag>=5) {
							now +=1;
							nowlevel.add(now);
						}
						if(now>=num) break;
					}
				}
				System.out.print("当前层次节点为:");
				System.out.println(nowlevel);
				//生成边
				String line = edgeConect(prelevel, nowlevel,b);
				content += line+" ";
				prelevel = nowlevel;
			}
			text[j]=content.trim();
			System.out.println(text[j]);
		}
	
		return text;		
	}
	
	public static void main(String[] args) throws IOException {
		String[] content = gen(10000,15, 3,15);
		writeTXT("E:\\workspace_decision\\dataset", "data10000m=15",content,15);
	}

}