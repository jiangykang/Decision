package withMaximin;

public class Test {

	public static void main(String[] args) {
//		long[] result1 = new long[5];
//		long[] result2 = new long[5];
//		long cal = 0;
//		TestComplexity tc3 = new TestComplexity();
//		tc3.inputData= new Data("E:/workspace_decision/dataset/data10000m=5.txt");		
//		for(int i=0;i<5;i++) {
//			System.out.println("------------------��"+(i+1)+"��------------------------");
//			long time1 = tc3.necessaryWinner();
//			long time2 = tc3.necessaryLoser();
//			result1[i] = time1;
//			result2[i] = time2;
//		}
//		System.out.println("5��ʵ���NWʱ��ֱ�Ϊ��");
//		for (long l : result1) {
//			cal += l;
//			System.out.print(l+" ");
//		}
//		System.out.println();
//		long average1 = cal/5;
//		cal = 0;
//		System.out.println("5��ʵ���NLʱ��ֱ�Ϊ��");
//		for (long l : result2) {
//			cal += l;
//			System.out.print(l+" ");
//		}
//		System.out.println();
//		long average2 = cal/5;
//		System.out.println("NWƽ��ʱ�䣺"+average1+"    NLƽ��ʱ�䣺"+average2); 
		Data data = new Data("E:/workspace_decision/dataset1/10000m=10.txt");
		TestComplexity tc3 = new TestComplexity(data.allCandidate,data.list);
		
		tc3.necessaryWinner();
		tc3.necessaryLoser();

	}

}
