package withPositionalScoringRule;

public class Test {

	public static void main(String[] args) {
		f2();
	}

	public static void f2() {
		long[] result1 = new long[10];
		long[] result2 = new long[10];
		long cal = 0;
		Data data = new Data("E:/workspace_decision/dataset_scoring/data10000m=15.txt");
		TestComplexity tc3 = new TestComplexity(data.allCandidate,data.list);
		for (int i = 0; i < 10; i++) {
			System.out.println("---------------��"+(i+1)+"��------------------------------");
			long time1 = tc3.necessaryWinner();
			long time2 = tc3.necessaryLoser();
			result1[i] = time1;
			result2[i] = time2;
		}
		System.out.println("10��ʵ���NWʱ��ֱ�Ϊ��");
		for (long l : result1) {
			cal += l;
			System.out.print(l+" ");
		}
		System.out.println();
		long average1 = cal/10;
		cal = 0;
		System.out.println("10��ʵ���NLʱ��ֱ�Ϊ��");
		for (long l : result2) {
			cal += l;
			System.out.print(l+" ");
		}
		System.out.println();
		long average2 = cal/10;
		System.out.println("NWƽ��ʱ�䣺"+average1+"    NLƽ��ʱ�䣺"+average2);
	}

}
