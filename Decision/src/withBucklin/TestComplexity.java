package withBucklin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TestComplexity {
	
//	static List<List<ArrayList>> mVoteUpon = new ArrayList<>();
//	static List<List<ArrayList>> mVoteDown = new ArrayList<>();
	
	static final int m = 20;
	static final int n = 2500;
	static final int nw_decision = 1;
	static final int nl_decision = 1;
	String[] allCandidate;
	List<List<int[]>> list;

	
	
	public TestComplexity(String[] allCandidate, List<List<int[]>> list) {
		super();
		this.allCandidate = allCandidate;
		this.list = list;
	}

	public long necessaryWinner() {	
		List<List<ArrayList>> mVoteUpon = new ArrayList<>();
		List<List<ArrayList>> mVoteDown = new ArrayList<>();
		boolean flag_nw = false;
		boolean flag_nl = false;
		long total = 0;
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
		
//		long calendtime = System.currentTimeMillis();
//		System.out.println("计算cal的时间："+(calendtime-startTime1));
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
		int c=0;
//		long startTime1 = System.currentTimeMillis();
		for(int can=1;can<=allCandidate.length;can++) {
			if(can==nw_decision) {
				continue;
			}
			System.out.println("现在的c为"+nw_decision+" w为"+can);
			int upperw = 0;
//			int lowerc = 0;
//			int lengthcw = 2;			
//			int upperc = 0;
			int[] lowerc = new int[n+1];
			int[] lengthcw = new int[n+1];
			int[] upperc = new int[n+1];
			
			
			int[] s3ic = new int[m+1];
			int[] s3iw = new int[m+1];
			int[] ucw = new int[m+1];
	
			
			for (int i = 0; i < list.size(); i++) {
				boolean exist = false;
				//System.out.println("当前的选票为：");
				for (int[] list2 : list.get(i)) {
					//System.out.print(list2[0]+","+list2[1]+" ");
					if(list2[0]==nw_decision&&list2[1]==can) {
						exist = true;
						break;
					}
						
				}
				//System.out.println();
				//System.out.println(exist);
					//1.如果c排在w后面
					if(!exist) {
						upperw = mVoteUpon.get(can-1).get(i).size();
						lowerc[i+1] = m + 1 - mVoteDown.get(nw_decision-1).get(i).size();
					}
					//2.如果c排在w前面
					else {
						c++;
						Set<String> set1 = new HashSet<>();
						Set<String> set2 = new HashSet<>();
						Set<String> set3 = new HashSet<>();
						//Block的下界
						Set<String> set_aim = new HashSet<>(mVoteDown.get(nw_decision-1).get(i));
						//System.out.println("down:"+mVoteDown.get(nw_decision-1).get(i));
						Set<String> set = new HashSet<>(mVoteUpon.get(can-1).get(i));
						//System.out.println("Upon:"+mVoteUpon.get(can-1).get(i));
						set3.addAll(set_aim);
						set3.retainAll(set);
						lengthcw[i+1] = set3.size();	
						//System.out.println("set3"+set3);
						set2.addAll(set);
						set2.removeAll(set_aim);
						//System.out.println("set2"+set2);
						upperc[i+1] = set2.size()+1;
						lowerc[i+1] = m + 1 - set_aim.size();
						//System.out.println("upperc="+upperc+"  lowerc="+lowerc);
					}					
			}
			long time = System.currentTimeMillis();
			flag_nw = f1(s3iw,s3ic,ucw,allCandidate,upperc,lowerc,lengthcw);
			//f2(s3iw,s3ic,ucw,allCandidate,upperc,lowerc,lengthcw);
			long time1 = System.currentTimeMillis();
			total +=time1-time;
//			if(flag_nw) {
//				break;
//			}
		}
		long endTime1 = System.currentTimeMillis();			
		
		if(!flag_nw) {
			System.out.println(allCandidate[nw_decision-1]+"是NW");
		}
		//System.out.println("处理NW问题的时间："+total+"ms");
		System.out.println("进入集合的次数"+c);
		System.out.println("处理NW问题的时间："+(endTime1-startTime1)+"ms");
		return endTime1-startTime1;
	}
	
	private static boolean f1(int[] s3iw,int[] s3ic,int[] ucw,String[] allCandidate,int[] upperc,int[] lowerc,int[] lengthcw) {
		for(int j=1;j<=n;j++) {
			for(int i=1;i<=m;i++) {
				if(upperc[j]<i&&lowerc[j]<i&&upperc[j]+lengthcw[j]-1>i) {
					s3ic[i-1] += 1;
				}
				if(upperc[j]<i&&lowerc[j]<i&&upperc[j]+lengthcw[j]-1<=i) {
					s3ic[i-1] += 1;
					s3iw[i] += 1;
				}
				if(upperc[j]<i&&lowerc[j]>=i&&upperc[j]+lengthcw[j]-1<=i) {
					ucw[i] += 1;
				}
				
			}
		}
		boolean flag1 = s3iw[1]+ucw[1]>n/2;
		boolean flag2 = false;
		for(int k=2;k<=m;k++) {
			if(s3iw[k]>s3ic[k-1]&&s3ic[k-1]<=n/2&&s3iw[k]+ucw[k]>n/2) {
				flag2 = true;
				break;
			}
			
		}
		if(flag1||flag2) {
			System.out.println(allCandidate[nw_decision-1]+"不是NW");
			return true;
			
		}
		return false;
	}
	private static void f2(int[] s3iw,int[] s3ic,int[] ucw,String[] allCandidate,int[] upperc,int[] lowerc,int[] lengthcw) {
		for(int j=1;j<=n;j++) {
			for(int i=1;i<=m;i++) {
				if(upperc[j]<=i&&lowerc[j]<=i&&upperc[j]+lengthcw[j]-1>i) {
					s3ic[i-1] += 1;
				}
				if(upperc[j]<=i&&lowerc[j]<=i&&upperc[j]+lengthcw[j]-1<=i) {
					s3ic[i-1] += 1;
					s3iw[i] += 1;
				}
				if(upperc[j]<=i&&lowerc[j]>=(i+1)&&upperc[j]+lengthcw[j]-1<=i) {
					ucw[i] += 1;
				}
				
			}
		}
		boolean flag3 = false;
		boolean flag4 = false;
		for(int p=0;p<ucw[1];p++) {
			if(s3iw[1]+p>n/2&&n/2>=s3ic[1]+p) {
				flag3 = true;
				break;
			}
		}
		for(int q=2;q<=m;q++) {
			for(int k=0;k<=ucw[q];k++) {
				if(s3iw[q]+k>n/2&&n/2>=s3ic[q]+k) {
					flag4 = true;
					break;
				}
			}
		}
		if(flag3&&flag4) {
			System.out.println(allCandidate[nw_decision-1]+"不是NcW");
		}
	}
public long necessaryLoser() {
		List<List<ArrayList>> mVoteUpon = new ArrayList<>();
		List<List<ArrayList>> mVoteDown = new ArrayList<>();
		boolean flag_nl = false;
		long total = 0;
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
			cal_new(list,i, mVoteUpon, mVoteDown);
		}
		
		int count=1;
		//System.out.println(mVoteUpon.size());
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
			if(can==nl_decision) {
				continue;
			}
			System.out.println("现在的l为"+nl_decision+" v为"+can);
			int[] upperl = new int[n+1];
			int lowerv = 0;
			int[] lengthlv = new int[n+1];			
			int[] lowerl = new int[n+1];
			int[] s4ic = new int[m+1];
			int[] s4iw = new int[m+1];
			int[] ulv = new int[m+1];
			for(int i = 0;i<=m;i++) {
				s4ic[i]=0;
				s4iw[i]=0;
				ulv[i]=0;
			}
			
			for (int i = 0; i < list.size(); i++) {
				boolean exist = false;
				//System.out.println("当前的选票为：");
				for (int[] list2 : list.get(i)) {
					//System.out.print(list2[0]+","+list2[1]+" ");
					if(list2[0]==nl_decision&&list2[1]==can) {
						exist = true;
						break;
					}
						
				}
				//System.out.println();
				//System.out.println(exist);
					//1.如果c排在w后面
					if(exist) {
						lowerv = mVoteDown.get(can-1).get(i).size();
						upperl[i+1] = m + 1 - mVoteUpon.get(nl_decision-1).get(i).size();
					}
					//2.如果c排在w前面
					else {
						Set<String> set1 = new HashSet<>();
						Set<String> set2 = new HashSet<>();
						Set<String> set3 = new HashSet<>();
						//Block的下界
						Set<String> set_aim = new HashSet<>(mVoteUpon.get(nl_decision-1).get(i));
						//System.out.println("down:"+mVoteDown.get(nw_decision-1).get(i));
						Set<String> set = new HashSet<>(mVoteDown.get(can-1).get(i));
						//System.out.println("Upon:"+mVoteUpon.get(can-1).get(i));
						set3.addAll(set_aim);
						set3.retainAll(set);
						lengthlv[i+1] = set3.size();	
						//System.out.println("set3"+set3);
						set2.addAll(set);
						set2.removeAll(set_aim);
						//System.out.println("set2"+set2);
						lowerl[i+1] = set2.size()+1;
						upperl[i+1] = m + 1 - set_aim.size();
						//System.out.println("upperc="+upperc+"  lowerc="+lowerc);
					}					
			}	
			long time = System.currentTimeMillis();
			flag_nl = f3(s4iw,s4ic,ulv,allCandidate,upperl,lowerl,lengthlv);
			//f4(s4iw,s4ic,ulv,allCandidate,upperl,lowerl,lengthlv);
			long time1 = System.currentTimeMillis();
			total += time1 - time;
//			if(flag_nl) {
//				break;
//			}
		}
		
		long endTime1 = System.currentTimeMillis();			
		
		if(!flag_nl) {
			//System.out.println(allCandidate[nl_decision-1]+"是NL");
		}
		System.out.println("处理NL问题的时间："+(endTime1-startTime1)+"ms");
		//System.out.println("处理NL问题的时间："+total+"ms");
		return endTime1-startTime1;
	}
	
	private static boolean f3(int[] s4iw,int[] s4ic,int[] ulv,String[] allCandidate,int[] upperl,int[] lowerl,int[] lengthlv) {
		for(int j=1;j<=n;j++) {
			for(int i=1;i<=m;i++) {
				if(upperl[j]>=m-i&&lowerl[j]>=m-i&&lowerl[j]+lengthlv[j]-1<=m-i) {
					s4iw[i-1] += 1;
				}
				if(upperl[j]>=m-i&&lowerl[j]>=m-i&&lowerl[j]+lengthlv[j]-1>m-i) {
					s4ic[i-1] += 1;
					s4iw[i] += 1;
				}
				if(lowerl[j]>=m-i&&upperl[j]<m-i&&lowerl[j]+lengthlv[j]-1>m-i) {
					ulv[i] += 1;
				}
				
			}
		}
		boolean flag1 = s4iw[1]+ulv[1]>n/2;
		boolean flag2 = false;
		for(int k=2;k<=m;k++) {
			if(s4iw[k]>s4ic[k-1]&&s4ic[k-1]<=n/2&&s4iw[k]+ulv[k]>n/2) {
				flag2 = true;
				break;
			}
			
		}
		if(flag1||flag2) {
			//System.out.println(allCandidate[nl_decision-1]+"不是NL");
			return true;
			
		}
		return false;
	}
	private static void f4(int[] s4iw,int[] s4ic,int[] ulv,String[] allCandidate,int[] upperl,int[] lowerl,int[] lengthlv) {
		for(int j=1;j<=n;j++) {
			for(int i=1;i<=m;i++) {
				if(upperl[j]>m-i&&lowerl[j]>m-i&&lowerl[j]+lengthlv[j]-1<=m-i) {
					s4ic[i-1] += 1;
				}
				if(upperl[j]>m-i&&lowerl[j]>m-i&&lowerl[j]+lengthlv[j]-1>m-i) {
					s4ic[i-1] += 1;
					s4iw[i] += 1;
				}
				if(lowerl[j]>m-i&&upperl[j]<(m-i-1)&&lowerl[j]+lengthlv[j]-1>m-i) {
					ulv[i] += 1;
				}
				
			}
		}
		boolean flag3 = false;
		boolean flag4 = false;
		for(int p=0;p<ulv[1];p++) {
			if(s4ic[1]+p>n/2&&n/2>=s4iw[1]+p) {
				flag3 = true;
				break;
			}
		}
		for(int q=2;q<=m;q++) {
			for(int k=0;k<=ulv[q];k++) {
				if(s4ic[q]+k>=n/2&&n/2>s4iw[q]+k) {
					flag4 = true;
					break;
				}
			}
		}
		if(flag3&&flag4) {
			//System.out.println(allCandidate[nw_decision-1]+"不是NcL");
		}
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
			allUpon.add((ArrayList) upon);
			allDown.add((ArrayList) down);	
		
		}
		mVoteUpon.add(allUpon);
		mVoteDown.add(allDown);
	}

}
