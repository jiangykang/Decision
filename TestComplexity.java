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
			//System.out.println("现在计算候选人"+candidate);
		}
		for (int i = 0; i < allCandidate.length; i++) {
			cal(list,allCandidate[i]);
		}
		int count=1;
		//System.out.println(mVoteUpon.size());
		for (List<ArrayList> allUpon : mVoteUpon) {
			//System.out.println("关于候选人c"+count+++"的upon集合");
			for (ArrayList string : allUpon) {
				//System.out.println(string);
			}
		}
		count=1;
		for (List<ArrayList> allDown : mVoteDown) {
			//System.out.println("关于候选人c"+count+++"的down集合");
			for (ArrayList string : allDown) {
				//System.out.println(string);
			}
		}
		
		//计算必要性
		
		long startTime1 = System.currentTimeMillis();
		for(int can=0;can<allCandidate.length;can++) {
			int score_aim=0;
			int score_compareto=0;
			if(can==nw_decision) {
				continue;
			}
			for (int i = 0; i < list.size(); i++) {
				//flag1被计算NW的目标候选人
				int flag1 = findIndex(list.get(i),allCandidate[nw_decision]);
				int flag2 = findIndex(list.get(i),allCandidate[can]);
				if(flag1!=-1&&flag2!=-1) {
//					System.out.println("当前计算的c为-->c1");
//					System.out.println("当前计算的w为-------->"+allCandidate[can]);
//					System.out.println("当前的部分序列为：");
//					for (String string : list.get(i)) {
//						System.out.print(string+" ");
//					}
//					System.out.println();
					//1.如果c排在w后面
					if(flag1>flag2) {
						//System.out.println("sdfsfs"+mVoteDown.get(0).get(i).size());
						score_aim = score_vet[m+1-mVoteDown.get(nw_decision).get(i).size()-1];
						score_compareto = score_vet[mVoteUpon.get(can).get(i).size()-1];
						//System.out.println("score_aim:"+score_aim+" score_compareto:"+score_compareto);
						score_aim += score_vet[m+1-mVoteDown.get(nw_decision).get(i).size()-1];
						score_compareto += score_vet[mVoteUpon.get(can).get(i).size()-1];
					}
					//2.如果c排在w后面
					else {
						//Block的下界
						Set<String> set_aim = new HashSet<>(mVoteDown.get(nw_decision).get(i));
						Set<String> set = new HashSet<>(mVoteUpon.get(can).get(i));
						Set<String> set1 = new HashSet<>();
						Set<String> set2 = new HashSet<>();
						//System.out.println("set_aim:"+set_aim);
						//System.out.println("set:"+set);
						set1.addAll(set_aim);
						set1.removeAll(set);
						//System.out.println("下界差集的结果"+set1+" 元素个数"+(set1.contains(null) ? 0:set1.size()));
						//Block的上界
						set2.addAll(set);
						set2.removeAll(set_aim);
						//System.out.println("上界差集的结果"+set2+" 元素个数"+(set2.contains(null) ? 0:set2.size()));
						
						int lower = m - (set1.contains(null) ? 0:set1.size());
						int higher = (set2.contains(null) ? 0:set2.size()) + 1;
						//System.out.println("block的范围:"+higher+"到"+lower);
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
			
//			System.out.println("比较的候选人"+allCandidate[can]+"NW计算结果是score_aim:"+score_aim+"    score_compareto:"+score_compareto);
			if(score_compareto>=score_aim) {
				System.out.println(allCandidate[nw_decision]+"不是NW");
				flag_nw = true;
				if(score_compareto>score_aim) {
					System.out.println(allCandidate[nw_decision]+"不是NcW");

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
//				System.out.println("当前计算的l为"+allCandidate[nl_decision]);
//				System.out.println("当前计算的v为-------->"+allCandidate[can]);
//				System.out.println("当前的部分序列为：");
//				for (String string : list.get(i)) {
//					System.out.print(string+" ");
//				}
//				System.out.println();
				//flag1被计算NW的目标候选人
				int flag1 = findIndex(list.get(i),allCandidate[nl_decision]); //l
				int flag2 = findIndex(list.get(i),allCandidate[can]);//v
				if(flag1!=-1&&flag2!=-1) {
					//1.如果v排在l后面l>v
					if(flag1<flag2) {
//						System.out.println("当前序列中，v排在l后面");
						//System.out.println("sdfsfs"+mVoteDown.get(can).get(i).size());
						//score_aim = score_vet[m+1-mVoteDown.get(can).get(i).size()-1];
						//score_compareto = score_vet[mVoteUpon.get(nl_decision).get(i).size()-1];
						//System.out.println("score_aim:"+score_aim+" score_compareto:"+score_compareto);
//						score_aim += score_vet[m+1-mVoteDown.get(can).get(i).size()-1];
//						score_compareto += score_vet[mVoteUpon.get(nl_decision).get(i).size()-1];
						score_aim += score_vet[mVoteUpon.get(nl_decision).get(i).size()-1];
						score_compareto += score_vet[m+1-mVoteDown.get(can).get(i).size()-1];
					}
					//2.如果l排在v后面
					else {
//						System.out.println("当前序列中，v排在l前面");
						//Block的下界
						Set<String> set_aim = new HashSet<>(mVoteDown.get(can).get(i));
						Set<String> set = new HashSet<>(mVoteUpon.get(nl_decision).get(i));
						Set<String> set1 = new HashSet<>();
						Set<String> set2 = new HashSet<>();
						//System.out.println("set_aim:"+set_aim);
						//System.out.println("set:"+set);
						set1.addAll(set_aim);
						set1.removeAll(set);
						//System.out.println("下界差集的结果"+set1+" 元素个数"+(set1.contains(null) ? 0:set1.size()));
						//Block的上界
						set2.addAll(set);
						set2.removeAll(set_aim);
						//System.out.println("上界差集的结果"+set2+" 元素个数"+(set2.contains(null) ? 0:set2.size()));
						int lower = m - (set1.contains(null) ? 0:set1.size());
						int higher = (set2.contains(null) ? 0:set2.size()) + 1;
						//System.out.println("block的范围:"+higher+"到"+lower);
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
//						System.out.println("NL计算中的score_aim1:"+score_aim1);
//						System.out.println("NL计算中的score_compareto1:"+score_compareto1);
						score_aim += score_aim1;
						score_compareto += score_compareto1;
					}			
				}						
			}
				
			System.out.println("比较的候选人是"+allCandidate[can]+"NL计算结果是score_aim:"+score_aim+"    score_compareto:"+score_compareto);
			if(score_compareto<=score_aim) {
				System.out.println(allCandidate[nl_decision]+"不是NL");
				flag_nl = true;
				if(score_compareto<score_aim) {
					System.out.println(allCandidate[nl_decision]+"不是NcL");
				}
				break;
				
			}
			
			
			
			
			
		}
		long endTime2 = System.currentTimeMillis();
		
		if(!flag_nw) {
			System.out.println(allCandidate[nw_decision]+"是NW");
		}
		
		if(!flag_nl) {
			System.out.println(allCandidate[nl_decision]+"是NL");
		}
		System.out.println("处理NW问题的时间："+(endTime1-startTime1)+"ms");
		System.out.println("处理NL问题的时间："+(endTime2-startTime2)+"ms");
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

					
//			//System.out.println("第"+count+"位投票中"+aim+"的upon集合是:");
//			for (ArrayList arrayList : allUpon) {				
//				//System.out.println(arrayList);
//			}
//			//System.out.println("第"+count+++"位投票中"+aim+"的down集合是:");
//			for (ArrayList arrayList : allDown) {				
//				//System.out.println(arrayList);
//			}			
		}
		mVoteUpon.add(allUpon);
		mVoteDown.add(allDown);
	}

}
