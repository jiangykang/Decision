package withMaximin;

public class Test {

	public static void main(String[] args) {
//		long[] result1 = new long[5];
//		long[] result2 = new long[5];
//		long cal = 0;
//		TestComplexity tc3 = new TestComplexity();
//		tc3.inputData= new Data("E:/workspace_decision/dataset/data10000m=5.txt");		
//		for(int i=0;i<5;i++) {
//			System.out.println("------------------第"+(i+1)+"次------------------------");
//			long time1 = tc3.necessaryWinner();
//			long time2 = tc3.necessaryLoser();
//			result1[i] = time1;
//			result2[i] = time2;
//		}
//		System.out.println("5次实验的NW时间分别为：");
//		for (long l : result1) {
//			cal += l;
//			System.out.print(l+" ");
//		}
//		System.out.println();
//		long average1 = cal/5;
//		cal = 0;
//		System.out.println("5次实验的NL时间分别为：");
//		for (long l : result2) {
//			cal += l;
//			System.out.print(l+" ");
//		}
//		System.out.println();
//		long average2 = cal/5;
//		System.out.println("NW平均时间："+average1+"    NL平均时间："+average2); 
		Data data = new Data("E:/workspace_decision/dataset1/10000m=10.txt");
		TestComplexity tc3 = new TestComplexity(data.allCandidate,data.list);
		
		tc3.necessaryWinner();
		tc3.necessaryLoser();

	}

}
