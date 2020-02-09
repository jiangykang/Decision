package withPositionalScoringRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TestComplexity {
	
//	static List<List<ArrayList>> mVoteUpon = new ArrayList<>();
//	static List<List<ArrayList>> mVoteDown = new ArrayList<>();
	static int index =-1;
	static int[]  score_vet = new int[]{14,13,12,11,10,9,8,7,6,5,4,3,2,1,0};
	//9,8,7,6,5,19,18,17,16,15,14,13,12,11,10,9,8,7,6,5,
	static final int m = 15;
	static final int nw_decision = 1;
	static final int nl_decision = 1;
	String[] allCandidate;
	List<List<int[]>> list;

	public long necessaryWinner() {
		
		List<List<ArrayList>> mVoteUpon = new ArrayList<>();
		List<List<ArrayList>> mVoteDown = new ArrayList<>();
		boolean flag_nw = false;
		boolean flag_nl = false;
		for(int k=0;k<list.size();k++) {
			for(int i=0;i<list.get(k).size();i++) {
				int tail = ((int[])list.get(k).get(i))[1];
				for(int j=0;j<list.get(k).size();j++) {
					int head = ((int[])list.get(k).get(j))[0];
					if(tail==head) {
						int a = ((int[])list.get(k).get(i))[0];
						int b = ((int[])list.get(k).get(j))[1];
						int[] c = {a,b};
						boolean flag = true;
						for (int l = 0; l < list.get(k).size(); l++) {
							if(Arrays.toString(list.get(k).get(l)).equals(Arrays.toString(c))) {
								flag = false;
								break;
								
							}
							else
								continue;
							
						}
						if(flag) {
							list.get(k).add(c);
						}	
					}
				}
			}
		}
		
		long startTime1 = System.currentTimeMillis();
		for (int i = 1; i <= allCandidate.length; i++) {			
			cal_new(list,i,mVoteUpon,mVoteDown);
		}
		
		int count=1;
//		System.out.println(mVoteUpon.size());
//		for (List<ArrayList> allUpon : mVoteUpon) {
//			System.out.println("关于候选人c"+count+++"的upon集合");
//			for (ArrayList string : allUpon) {
//				System.out.println(string);
//			}
//		}
//		count=1;
//		for (List<ArrayList> allDown : mVoteDown) {
//			System.out.println("关于候选人c"+count+++"的down集合");
//			for (ArrayList string : allDown) {
//				System.out.println(string);
//			}
//		}
		
		//计算必要性
		
//		long startTime1 = System.currentTimeMillis();
		for(int can=1;can<=allCandidate.length;can++) {
			int score_aim=0;
			int score_compareto=0;
			if(can==nw_decision) {
				continue;
			}
			System.out.println("现在的c为"+nw_decision+" w为"+can);
			for (int i = 0; i < list.size(); i++) {
				boolean exist = false;
//				for (List is : list) {
//					for (int[] is2 : (List<int[]>)is) {
//						if(is2[0]==nw_decision&&is2[1]==can)
//							exist = true;
//					}
//				}
//				System.out.println("当前的选票为：");
				for (int[] list2 : list.get(i)) {
//					System.out.print(list2[0]+","+list2[1]+" ");
					if(list2[0]==nw_decision&&list2[1]==can) {
						exist = true;
						break;
					}
						
					
				}
//				System.out.println();
//				System.out.println(exist);
					//1.如果c排在w后面
					if(!exist) {
						//System.out.println(m+1-mVoteDown.get(nw_decision-1).get(i).size()-1);
						//System.out.println(mVoteUpon.get(can-1).get(i).size()-1);
						score_aim += score_vet[m+1-mVoteDown.get(nw_decision-1).get(i).size()-1];
						score_compareto += score_vet[mVoteUpon.get(can-1).get(i).size()-1];
//						System.out.println("score_aim:"+score_aim+"  score_compareto"+score_compareto);
					}
					//2.如果c排在w前面
					else {
						//Block的下界
						Set<String> set_aim = new HashSet<>(mVoteDown.get(nw_decision-1).get(i));
						//System.out.println("down:"+mVoteDown.get(nw_decision-1).get(i));
						Set<String> set = new HashSet<>(mVoteUpon.get(can-1).get(i));
						//System.out.println("Upon:"+mVoteUpon.get(can-1).get(i));
						
//						System.out.println("目标候选人的Down:"+mVoteDown.get(nw_decision-1).get(i));
//						System.out.println("Upon:"+mVoteUpon.get(can-1).get(i));
						
						Set<String> set1 = new HashSet<>();
						Set<String> set2 = new HashSet<>();
						Set<String> set3 = new HashSet<>();
						//System.out.println("set_aim:"+set_aim);
						//System.out.println("set:"+set);
						set1.addAll(set_aim);
						set1.removeAll(set);
//						System.out.println("下界差集的结果"+set1+" 元素个数"+(set1.contains(null) ? 0:set1.size()));
						//Block的上界
						set2.addAll(set);
						set2.removeAll(set_aim);
//						System.out.println("上界差集的结果"+set2+" 元素个数"+(set2.contains(null) ? 0:set2.size()));
						set3.addAll(set_aim);
						set3.retainAll(set);
//						System.out.println("交集："+set3.toString());
						int lower = m - set1.size();
						int higher = set2.size() + 1;
//						System.out.println("block的范围:"+higher+"到"+lower);
						int argmax = -999;
						int score_aim1=0;
						int score_compareto1=0; 
						int a = set3.size()==0? 0:set3.size()-1;
						for(int index=higher-1;index<=lower-1-a;index++) {
//							System.out.println("index="+index);
//							System.out.println("index+set3.size()-2="+(index+a));
							
							if(score_vet[index+a]-score_vet[index]>argmax) {
								argmax = score_vet[index+a]-score_vet[index];
								score_aim1 = score_vet[index];
								score_compareto1 = score_vet[index+a];
							}
						}
						//System.out.println(" score_aim1:"+score_aim1);
						//System.out.println("score_compareto1:"+score_compareto1);
						score_aim += score_aim1;
						score_compareto += score_compareto1;
//						System.out.println("score_aim:"+score_aim+"  score_compareto"+score_compareto);
					}					
			}
			
//			System.out.println("比较的候选人"+allCandidate[can-1]+"NW计算结果是score_aim:"+score_aim+"    score_compareto:"+score_compareto);
			if(score_compareto>=score_aim) {
//				System.out.println(allCandidate[nw_decision-1]+"不是NW");
				flag_nw = true;
				if(score_compareto>score_aim) {
//					System.out.println(allCandidate[nw_decision-1]+"不是NcW");

				}
//				break;
			}	
		}
		long endTime1 = System.currentTimeMillis();			
		
		if(!flag_nw) {
			System.out.println(allCandidate[nw_decision-1]+"是NW");
		}
		
		
		System.out.println("---------------------处理NW问题的时间："+(endTime1-startTime1)+"ms");
		return endTime1-startTime1;

	}
	public TestComplexity(String[] allCandidate, List<List<int[]>> list) {
	super();
	this.allCandidate = allCandidate;
	this.list = list;
}
	public String[] getAllCandidate() {
		return allCandidate;
	}
	public void setAllCandidate(String[] allCandidate) {
		this.allCandidate = allCandidate;
	}
	public List<List<int[]>> getList() {
		return list;
	}
	public void setList(List<List<int[]>> list) {
		this.list = list;
	}
	public long necessaryLoser() {
		List<List<ArrayList>> mVoteUpon = new ArrayList<>();
		List<List<ArrayList>> mVoteDown = new ArrayList<>();
		boolean flag_nw = false;
		boolean flag_nl = false;
		for(int k=0;k<list.size();k++) {
			for(int i=0;i<list.get(k).size();i++) {
				int tail = ((int[])list.get(k).get(i))[1];
				for(int j=0;j<list.get(k).size();j++) {
					int head = ((int[])list.get(k).get(j))[0];
					if(tail==head) {
						int a = ((int[])list.get(k).get(i))[0];
						int b = ((int[])list.get(k).get(j))[1];
						int[] c = {a,b};
						boolean flag = true;
						for (int l = 0; l < list.get(k).size(); l++) {
							if(Arrays.toString(list.get(k).get(l)).equals(Arrays.toString(c))) {
								flag = false;
								break;
								
							}
							else
								continue;
							
						}
						if(flag) {
							list.get(k).add(c);
						}	
					}
				}
			}
		}
		
		long startTime2 = System.currentTimeMillis();
		for (int i = 1; i <= allCandidate.length; i++) {			
			cal_new(list,i,mVoteUpon,mVoteDown);
		}
		
		for(int can=1;can<=allCandidate.length;can++) {
			int score_aim=0;
			int score_compareto=0;
			if(can==nl_decision) {
				continue;
			}
			System.out.println("现在的c为"+nw_decision+" w为"+can);
			for (int i = 0; i < list.size(); i++) {
				boolean exist = false;
//				System.out.println("当前的选票为：");
				for (int[] list2 : list.get(i)) {
//					System.out.print(list2[0]+","+list2[1]+" ");
					if(list2[0]==nl_decision&&list2[1]==can) {
						exist = true;
						break;
					}
						
				}
//				System.out.println();				
//				System.out.println(exist);			
					//1.如果v排在l后面l>v
					if(exist) {
						
						score_aim += score_vet[mVoteUpon.get(nl_decision-1).get(i).size()-1];
						score_compareto += score_vet[m+1-mVoteDown.get(can-1).get(i).size()-1];
					}
					//2.如果l排在v后面
					else {
//						System.out.println("当前序列中，v排在l前面");
						//Block的下界
						Set<String> set_aim = new HashSet<>(mVoteDown.get(can-1).get(i));
						Set<String> set = new HashSet<>(mVoteUpon.get(nl_decision-1).get(i));
						Set<String> set1 = new HashSet<>();
						Set<String> set2 = new HashSet<>();
						Set<String> set3 = new HashSet<>();
//						System.out.println("set_aim:"+set_aim);
//						System.out.println("set:"+set);
						set1.addAll(set_aim);
						set1.removeAll(set);
						//System.out.println("下界差集的结果"+set1+" 元素个数"+(set1.contains(null) ? 0:set1.size()));
						//Block的上界
						set2.addAll(set);
						set2.removeAll(set_aim);
						//System.out.println("上界差集的结果"+set2+" 元素个数"+(set2.contains(null) ? 0:set2.size()));
						set3.addAll(set_aim);
						set3.retainAll(set);
//						System.out.println("交集："+set3.toString());
						int lower = m - set1.size();
						int higher = set2.size() + 1;
						//System.out.println("block的范围:"+higher+"到"+lower);
						int argmax = -999;
						int score_aim1=0;
						int score_compareto1=0; 
						int a = set3.size()==0? 0:set3.size()-1;
						for(int index=higher-1;index<=lower-1-a;index++) {
//							System.out.println("index="+index);
//							System.out.println("index+set3.size()-2="+(index+set3.size()-2));
							if(score_vet[index+a]-score_vet[index]>argmax) {
								
								argmax = score_vet[index+a]-score_vet[index];
								score_compareto1 = score_vet[index];
								score_aim1 = score_vet[index+a];
							}
						}
//						System.out.println("NL计算中的score_aim1:"+score_aim1);
//						System.out.println("NL计算中的score_compareto1:"+score_compareto1);
						score_aim += score_aim1;
						score_compareto += score_compareto1;
					}			
									
			}
				
			//System.out.println("比较的候选人是"+allCandidate[can-1]+" NL计算结果是score_aim:"+score_aim+"    score_compareto:"+score_compareto);
			if(score_compareto<=score_aim) {
				//System.out.println(allCandidate[nl_decision-1]+"不是NL");
				flag_nl = true;
				if(score_compareto<score_aim) {
					//System.out.println(allCandidate[nl_decision-1]+"不是NcL");
				}
//				break;
				
			}				
		}
		long endTime2 = System.currentTimeMillis();
		System.out.println("------------------处理NL问题的时间："+(endTime2-startTime2)+"ms");
		return endTime2-startTime2;
		
	}
	
	public static int findIndex(String[] s,String aim) {
		for (int i = 0; i < s.length; i++) {
			if(s[i].equals(aim)) {
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
		//mVoteUpon.add(allUpon);
		//mVoteDown.add(allDown);
	}	
	public static void cal_new(List<List<int[]>> list1,int aim, List<List<ArrayList>> mVoteUpon, List<List<ArrayList>> mVoteDown) {
		int count=1;
		List<ArrayList> allUpon = new ArrayList<ArrayList>();
		List<ArrayList> allDown = new ArrayList<ArrayList>();
		for (List is : list1) {
			List<Integer> upon = new ArrayList<Integer>();
			List<Integer> down = new ArrayList<Integer>();
//			for (int[] is2 : (List<int[]>)is) {
//				System.out.print("序列队为:"+is2[0]+","+is2[1]+" ");
//			}
//			System.out.println();
			down.add(aim);
			for (int i = 0; i < is.size(); i++) {
				if(((int[])is.get(i))[0]==aim) {
					down.add(((int[])is.get(i))[1]);
				}
				if(((int[])is.get(i))[1]==aim) {	
					upon.add(((int[])is.get(i))[0]);
				}				
			}
			upon.add(aim);
//			System.out.println("upon:"+upon);
//			System.out.println("down:"+down);
			allUpon.add((ArrayList) upon);
			allDown.add((ArrayList) down);	
		
		}
//		System.out.println("allupon"+allUpon);
		mVoteUpon.add(allUpon);
		mVoteDown.add(allDown);
	}

}
