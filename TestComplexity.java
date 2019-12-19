package withPositionalScoringRule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TestComplexity {
	
	static List<List<ArrayList>> mVoteUpon = new ArrayList<>();
	static List<List<ArrayList>> mVoteDown = new ArrayList<>();
	static int index =-1;
	static int[]  score_vet = new int[]{4,3,2,1,0};
	static final int m = 5;
	static final int nw_decision = 2;
	static final int nl_decision = 2;

	public static void main(String[] args) {
		
		List<String[]> list = new ArrayList<String[]>();
		String[] allCandidate = {"c1","c2","c3","c4","c5"};
		String[] v1= {"c1","c2","c5"};
		String[] v2= {"c2","c4","c5"};
		String[] v3= {"c1","c3","c5"};
		String[] v4= {"c1","c4","c5"};
		String[] v5= {"c1","c3","c4","c5"};
		list.add(v1);
		list.add(v2);
		list.add(v3);
		list.add(v4);
		list.add(v5);
		boolean flag_nw = false;
		boolean flag_nl = false;
		for (String[] string : list) {
			for (String string2 : string) {
				//System.out.print(string2 +" ");
			}
			//System.out.println();
		}
		//System.out.println();
		for (String candidate : allCandidate) {
			//System.out.println("���ڼ����ѡ��"+candidate);
		}
		for (int i = 0; i < allCandidate.length; i++) {
			cal(list,allCandidate[i]);
		}
		int count=1;
		//System.out.println(mVoteUpon.size());
		for (List<ArrayList> allUpon : mVoteUpon) {
			//System.out.println("���ں�ѡ��c"+count+++"��upon����");
			for (ArrayList string : allUpon) {
				//System.out.println(string);
			}
		}
		count=1;
		for (List<ArrayList> allDown : mVoteDown) {
			//System.out.println("���ں�ѡ��c"+count+++"��down����");
			for (ArrayList string : allDown) {
				//System.out.println(string);
			}
		}
		
		//�����Ҫ��
		
		long startTime1 = System.currentTimeMillis();
		for(int can=0;can<allCandidate.length;can++) {
			int score_aim=0;
			int score_compareto=0;
			if(can==nw_decision) {
				continue;
			}
			for (int i = 0; i < list.size(); i++) {
				//flag1������NW��Ŀ���ѡ��
				int flag1 = findIndex(list.get(i),allCandidate[nw_decision]);
				int flag2 = findIndex(list.get(i),allCandidate[can]);
				if(flag1!=-1&&flag2!=-1) {
//					System.out.println("��ǰ�����cΪ-->c1");
//					System.out.println("��ǰ�����wΪ-------->"+allCandidate[can]);
//					System.out.println("��ǰ�Ĳ�������Ϊ��");
//					for (String string : list.get(i)) {
//						System.out.print(string+" ");
//					}
//					System.out.println();
					//1.���c����w����
					if(flag1>flag2) {
						//System.out.println("sdfsfs"+mVoteDown.get(0).get(i).size());
						score_aim = score_vet[m+1-mVoteDown.get(nw_decision).get(i).size()-1];
						score_compareto = score_vet[mVoteUpon.get(can).get(i).size()-1];
						//System.out.println("score_aim:"+score_aim+" score_compareto:"+score_compareto);
						score_aim += score_vet[m+1-mVoteDown.get(nw_decision).get(i).size()-1];
						score_compareto += score_vet[mVoteUpon.get(can).get(i).size()-1];
					}
					//2.���c����w����
					else {
						//Block���½�
						Set<String> set_aim = new HashSet<>(mVoteDown.get(nw_decision).get(i));
						Set<String> set = new HashSet<>(mVoteUpon.get(can).get(i));
						Set<String> set1 = new HashSet<>();
						Set<String> set2 = new HashSet<>();
						//System.out.println("set_aim:"+set_aim);
						//System.out.println("set:"+set);
						set1.addAll(set_aim);
						set1.removeAll(set);
						//System.out.println("�½��Ľ��"+set1+" Ԫ�ظ���"+(set1.contains(null) ? 0:set1.size()));
						//Block���Ͻ�
						set2.addAll(set);
						set2.removeAll(set_aim);
						//System.out.println("�Ͻ��Ľ��"+set2+" Ԫ�ظ���"+(set2.contains(null) ? 0:set2.size()));
						
						int lower = m - (set1.contains(null) ? 0:set1.size());
						int higher = (set2.contains(null) ? 0:set2.size()) + 1;
						//System.out.println("block�ķ�Χ:"+higher+"��"+lower);
						int argmax = -999;
						int score_aim1=0;
						int score_compareto1=0; 
						for(int index=higher-1;index<=lower+flag1-flag2-1;index++) {
							//System.out.println("index="+index);
							//System.out.println("index+flag2-flag1="+(index+flag2-flag1));
							if(score_vet[index+flag2-flag1]-score_vet[index]>argmax) {
								argmax = score_vet[index+flag2-flag1]-score_vet[index];
								score_aim1 = score_vet[index];
								score_compareto1 = score_vet[index+flag2-flag1];
							}
						}
						//System.out.println(" score_aim1:"+score_aim1);
						//System.out.println("score_compareto1:"+score_compareto1);
						score_aim += score_aim1;
						score_compareto += score_compareto1;
					}
					
				
				
				}
				
				
				
			}
			
//			System.out.println("�Ƚϵĺ�ѡ��"+allCandidate[can]+"NW��������score_aim:"+score_aim+"    score_compareto:"+score_compareto);
			if(score_compareto>=score_aim) {
				System.out.println(allCandidate[nw_decision]+"����NW");
				flag_nw = true;
				if(score_compareto>score_aim) {
					System.out.println(allCandidate[nw_decision]+"����NcW");

				}
				break;
			}
			
			
			
			
			
		}
		long endTime1 = System.currentTimeMillis();			
		long startTime2 = System.currentTimeMillis();
		for(int can=0;can<allCandidate.length;can++) {
			int score_aim=0;
			int score_compareto=0;
			if(can==nl_decision) {
				continue;
			}
			for (int i = 0; i < list.size(); i++) {
//				System.out.println("��ǰ�����lΪ"+allCandidate[nl_decision]);
//				System.out.println("��ǰ�����vΪ-------->"+allCandidate[can]);
//				System.out.println("��ǰ�Ĳ�������Ϊ��");
//				for (String string : list.get(i)) {
//					System.out.print(string+" ");
//				}
//				System.out.println();
				//flag1������NW��Ŀ���ѡ��
				int flag1 = findIndex(list.get(i),allCandidate[nl_decision]); //l
				int flag2 = findIndex(list.get(i),allCandidate[can]);//v
				if(flag1!=-1&&flag2!=-1) {
					//1.���v����l����l>v
					if(flag1<flag2) {
//						System.out.println("��ǰ�����У�v����l����");
						//System.out.println("sdfsfs"+mVoteDown.get(can).get(i).size());
						//score_aim = score_vet[m+1-mVoteDown.get(can).get(i).size()-1];
						//score_compareto = score_vet[mVoteUpon.get(nl_decision).get(i).size()-1];
						//System.out.println("score_aim:"+score_aim+" score_compareto:"+score_compareto);
//						score_aim += score_vet[m+1-mVoteDown.get(can).get(i).size()-1];
//						score_compareto += score_vet[mVoteUpon.get(nl_decision).get(i).size()-1];
						score_aim += score_vet[mVoteUpon.get(nl_decision).get(i).size()-1];
						score_compareto += score_vet[m+1-mVoteDown.get(can).get(i).size()-1];
					}
					//2.���l����v����
					else {
//						System.out.println("��ǰ�����У�v����lǰ��");
						//Block���½�
						Set<String> set_aim = new HashSet<>(mVoteDown.get(can).get(i));
						Set<String> set = new HashSet<>(mVoteUpon.get(nl_decision).get(i));
						Set<String> set1 = new HashSet<>();
						Set<String> set2 = new HashSet<>();
						//System.out.println("set_aim:"+set_aim);
						//System.out.println("set:"+set);
						set1.addAll(set_aim);
						set1.removeAll(set);
						//System.out.println("�½��Ľ��"+set1+" Ԫ�ظ���"+(set1.contains(null) ? 0:set1.size()));
						//Block���Ͻ�
						set2.addAll(set);
						set2.removeAll(set_aim);
						//System.out.println("�Ͻ��Ľ��"+set2+" Ԫ�ظ���"+(set2.contains(null) ? 0:set2.size()));
						int lower = m - (set1.contains(null) ? 0:set1.size());
						int higher = (set2.contains(null) ? 0:set2.size()) + 1;
						//System.out.println("block�ķ�Χ:"+higher+"��"+lower);
						int argmax = -999;
						int score_aim1=0;
						int score_compareto1=0; 
						for(int index=higher-1;index<=lower+flag2-flag1-1;index++) {
							//System.out.println("index="+index);
							//System.out.println("index+flag1-flag2="+(index+flag1-flag2));
							if(score_vet[index+flag1-flag2]-score_vet[index]>argmax) {
								argmax = score_vet[index+flag1-flag2]-score_vet[index];
								score_compareto1 = score_vet[index];
								score_aim1 = score_vet[index+flag1-flag2];
							}
						}
//						System.out.println("NL�����е�score_aim1:"+score_aim1);
//						System.out.println("NL�����е�score_compareto1:"+score_compareto1);
						score_aim += score_aim1;
						score_compareto += score_compareto1;
					}			
				}						
			}
				
			System.out.println("�Ƚϵĺ�ѡ����"+allCandidate[can]+"NL��������score_aim:"+score_aim+"    score_compareto:"+score_compareto);
			if(score_compareto<=score_aim) {
				System.out.println(allCandidate[nl_decision]+"����NL");
				flag_nl = true;
				if(score_compareto<score_aim) {
					System.out.println(allCandidate[nl_decision]+"����NcL");
				}
				break;
				
			}
			
			
			
			
			
		}
		long endTime2 = System.currentTimeMillis();
		
		if(!flag_nw) {
			System.out.println(allCandidate[nw_decision]+"��NW");
		}
		
		if(!flag_nl) {
			System.out.println(allCandidate[nl_decision]+"��NL");
		}
		System.out.println("����NW�����ʱ�䣺"+(endTime1-startTime1)+"ms");
		System.out.println("����NL�����ʱ�䣺"+(endTime2-startTime2)+"ms");
	}
	
	public static int findIndex(String[] s,String aim) {
		for (int i = 0; i < s.length; i++) {
			if(s[i]==aim) {
				return i;
			}
		}
		return -1;
	}
	public static void cal(List<String[]> list,String aim) {
		int count=1;
		ArrayList<ArrayList> allUpon = new ArrayList<ArrayList>();
		ArrayList<ArrayList> allDown = new ArrayList<ArrayList>();
		for (String[] string : list) {		
			List<String> upon = new ArrayList<String>();
			List<String> down = new ArrayList<String>();
//			allUpon = new ArrayList<ArrayList>();
//			allDown = new ArrayList<ArrayList>();
			index = findIndex(string,aim);
//			//System.out.println("index="+index);
			if(index==-1) {
//				continue;
				upon.add(null);
				down.add(null);
			}
			else {				
				for(int i=0;i<=index;i++) {				
					upon.add(string[i]);					
				}									
				for(int j=index;j<string.length;j++) {
					down.add(string[j]);
				}
			}			
			allUpon.add((ArrayList) upon);
			allDown.add((ArrayList) down);

					
//			//System.out.println("��"+count+"λͶƱ��"+aim+"��upon������:");
//			for (ArrayList arrayList : allUpon) {				
//				//System.out.println(arrayList);
//			}
//			//System.out.println("��"+count+++"λͶƱ��"+aim+"��down������:");
//			for (ArrayList arrayList : allDown) {				
//				//System.out.println(arrayList);
//			}			
		}
		mVoteUpon.add(allUpon);
		mVoteDown.add(allDown);
	}

}
