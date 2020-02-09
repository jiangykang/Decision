package withMaximin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TestComplexity2 {
	
	static List<List<ArrayList>> mVoteUpon = new ArrayList<>();
	static List<List<ArrayList>> mVoteDown = new ArrayList<>();
	static List<List<ArrayList>> mVoteUpon_beifen =  new ArrayList<>();
	static int index =-1;
	static int[]  score_vet = new int[]{5,4,3,2,1,0};
	//9,8,7,6,5,19,18,17,16,15,14,13,12,11,10,9,8,7,6,
	static final int m = 6;
	static final int nw_decision = 1;
	static final int nl_decision = 1;
	Data inputData;

	public Data getInputData() {
		return inputData;
	}

	public void setInputData(Data inputData) {
		this.inputData = inputData;
	}

	public long necessaryWinner() {
		
		inputData.readFile();
		String[] allCandidate = inputData.allCandidate;
		List<List<int[]>> list = inputData.list;	

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
			cal_new(list,i);
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
		int score_cw1 = 0;
		//下标从0开始
		int[] score_wd = new int[allCandidate.length];
		boolean jixu = true;

		for(int i=1;i<=allCandidate.length;i++) {
			if(jixu) {
				for(int j=1;j<=allCandidate.length;j++) {
					if(i!=nw_decision&&j!=i&&j!=nw_decision&&jixu) {
						//System.out.println("i="+i+" j="+j);
						//w = i,w1 = j		
						for (int v = 0; v < list.size(); v++) {
							boolean exist = false;
							for (int[] list2 : list.get(v)) {
//								System.out.print(list2[0]+","+list2[1]+" ");
								if(list2[0]==nw_decision&&list2[1]==j) {
									exist = true;
									break;
								}
									
							}
							//c>w1
							if(exist) {
								//System.out.println("c>w1");
								score_cw1++;
								for(int k=1;k<=allCandidate.length;k++) {
									
									if(k!=i&&!mVoteUpon.get(i-1).contains(k)) {
										//System.out.println("k="+k);
										score_wd[k-1]++;
										//System.out.println("score_wd["+(k-1)+"]="+score_wd[k-1]);
									}
								}
							}
							else {
								boolean ifGenxin = true;
								//System.out.println("c<w1");
								for (int[] k : list.get(v)) {
									if(k[0]==j&&k[1]==nw_decision) {
										ifGenxin = false;
									}
								}
								if(ifGenxin) {
									//更新upon
									int[] add = {j,nw_decision};
									List<List<int[]>> list_update = new ArrayList<>(list);
									list_update.get(v).add(add);									
										for(int x=0;x<list_update.get(v).size();x++) {
											int tail = ((int[])list_update.get(v).get(x))[1];
											for(int y=0;y<list_update.get(v).size();y++) {
												int head = ((int[])list_update.get(v).get(y))[0];
												if(tail==head) {
													int a = ((int[])list_update.get(v).get(x))[0];
													int b = ((int[])list_update.get(v).get(y))[1];
													int[] c = {a,b};
													boolean flag = true;
													for (int l = 0; l < list_update.get(v).size(); l++) {
														if(Arrays.toString(list_update.get(v).get(l)).equals(Arrays.toString(c))) {
															flag = false;
															break;
															
														}
														else
															continue;
														
													}
													if(flag) {
														list_update.get(v).add(c);
													}	
												}
											}
										}
										List<Integer> upOn = cal_partialOrderUpon(list_update.get(v), i-1);
										for(int k=1;k<=allCandidate.length;k++) {
											if(k!=i&&!upOn.contains(k)) {
												//System.out.println("k="+k);
												score_wd[k-1]++;
												//System.out.println("score_wd["+(k-1)+"]="+score_wd[k-1]);
											}
										}
//										mVoteDown.clear();
//										mVoteUpon.clear();
//										for (int p = 1; p <= allCandidate.length; p++) {			
//											cal_new(list_update,p);
//										}
								}
								else {
									for(int k=1;k<=allCandidate.length;k++) {
										if(k!=i&&!mVoteUpon.get(i-1).contains(k)) {
											//System.out.println("k="+k);
											score_wd[k-1]++;
											//System.out.println("score_wd["+(k-1)+"]="+score_wd[k-1]);
										}
									}
								}
								
									
								
							}
						}
						for (int list2 : score_wd) {
							if(list2>=score_cw1) {
								System.out.println(allCandidate[nw_decision-1]+"不是NW");
								flag_nw = true;
								if(list2>score_cw1) {
									System.out.println(allCandidate[nw_decision-1]+"不是NcW");
				
								}
								//是否跑遍循环
								jixu= false;
								break;
							}
						}			
					}

							
				}//内层循环结束
			}
			else {
				break;
			}
			
	}//外层循环结束
		if(!flag_nw) {
			System.out.println(allCandidate[nw_decision-1]+"是NW");
		}	
		long endTime1 = System.currentTimeMillis();
		System.out.println("处理NW问题的时间："+(endTime1-startTime1)+"ms");
		return endTime1-startTime1;

	}
	public long necessaryLoser() {
		inputData.readFile();
		String[] allCandidate = inputData.allCandidate;
		List<List<int[]>> list = inputData.list;	

		boolean flag_nw = false;
		boolean flag_nl = false;
		/*
		 * 扩展序列
		 */
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
			cal_new(list,i);
		}
		
//		int count=1;
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
		int score_cw1 = 0;
		//下标从0开始
		int[] score_wd = new int[allCandidate.length];
		boolean jixu = true;

		for(int i=1;i<=allCandidate.length;i++) {
			if(jixu) {
				for(int j=1;j<=allCandidate.length;j++) {
					if(i!=nl_decision&&j!=i&&j!=nl_decision&&jixu) {
						//System.out.println("i="+i+" j="+j);
						//v = i,v1 = j		
						for (int v = 0; v < list.size(); v++) {
							boolean exist = true;
							for (int[] list2 : list.get(v)) {
//								System.out.print(list2[0]+","+list2[1]+" ");
								if(list2[0]==j&&list2[1]==nl_decision) {
									exist = false;
									break;
								}
									
							}
							//     l不小于v1
							if(exist) {
								
								
								
								
								boolean ifGenxin = true;
								//System.out.println("l不小于v1");
								for (int[] k : list.get(v)) {
									if(k[0]==nl_decision&&k[1]==j) {
										ifGenxin = false;
									}
								}
								if(ifGenxin) {
									//更新upon
									int[] add = {nl_decision,j};
									List<List<int[]>> list_update = new ArrayList<>(list);
									list_update.get(v).add(add);									
										for(int x=0;x<list_update.get(v).size();x++) {
											int tail = ((int[])list_update.get(v).get(x))[1];
											for(int y=0;y<list_update.get(v).size();y++) {
												int head = ((int[])list_update.get(v).get(y))[0];
												if(tail==head) {
													int a = ((int[])list_update.get(v).get(x))[0];
													int b = ((int[])list_update.get(v).get(y))[1];
													int[] c = {a,b};
													boolean flag = true;
													for (int l = 0; l < list_update.get(v).size(); l++) {
														if(Arrays.toString(list_update.get(v).get(l)).equals(Arrays.toString(c))) {
															flag = false;
															break;
															
														}
														else
															continue;
														
													}
													if(flag) {
														list_update.get(v).add(c);
													}	
												}
											}
										}
										List<Integer> down = cal_partialOrderDown(list_update.get(v), i-1);
										for(int k=1;k<=allCandidate.length;k++) {
											if(k!=i&&!down.contains(k)) {
												//System.out.println("k="+k);
												score_wd[k-1]++;
												//System.out.println("score_wd["+(k-1)+"]="+score_wd[k-1]);
											}
										}
								}
								else {
									for(int k=1;k<=allCandidate.length;k++) {
										if(k!=i&&!mVoteDown.get(i-1).contains(k)) {
											//System.out.println("k="+k);
											score_wd[k-1]++;
											//System.out.println("score_wd["+(k-1)+"]="+score_wd[k-1]);
										}
									}
								}
																
							}
							else {    // l<v1
								
								score_cw1++;
								for(int k=1;k<=allCandidate.length;k++) {
									if(k!=i&&!mVoteDown.get(i-1).contains(k)) {
										//System.out.println("k="+k);
										score_wd[k-1]++;
										//System.out.println("score_wd["+(k-1)+"]="+score_wd[k-1]);
									}
								}			
								
							} //else结束
						}
						for (int list2 : score_wd) {
							if(list2<=score_cw1) {
								System.out.println(allCandidate[nl_decision-1]+"不是NL");
								flag_nw = true;
								if(list2<score_cw1) {
									System.out.println(allCandidate[nl_decision-1]+"不是NcL");
				
								}
								/*
								 * 是否跑遍循环
								 */
								jixu= false;
								break;
							}
						}			
					}

							
				}//内层循环结束
			}
			else {
				break;
			}
			
	}//外层循环结束
		if(!flag_nw) {
			System.out.println(allCandidate[nl_decision-1]+"是NL");
		}	
		long endTime1 = System.currentTimeMillis();
		System.out.println("处理NL问题的时间："+(endTime1-startTime1)+"ms");
		return endTime1-startTime1;
		
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
	public static List cal_partialOrderUpon(List<int[]> list,int aim) {
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			if(((int[])list.get(i))[1]==aim) {	
				result.add(((int[])list.get(i))[0]);
			}				
		}
		result.add(aim);
		return result;
		
	}
	public static List cal_partialOrderDown(List<int[]> list,int aim) {
		List<Integer> result = new ArrayList<>();
		result.add(aim);
		for (int i = 0; i < list.size(); i++) {
			if(((int[])list.get(i))[0]==aim) {	
				result.add(((int[])list.get(i))[1]);
			}				
		}
		return result;
		
	}
	public static void main(String[] args) {
		List<int[]> list = new ArrayList<>();
		list.add(new int[]{1,2});
		list.add(new int[]{1,3});
		list.add(new int[]{2,4});
		list.add(new int[]{4,5});
		for(int x=0;x<list.size();x++) {
			int tail = ((int[])list.get(x))[1];
			for(int y=0;y<list.size();y++) {
				int head = ((int[])list.get(y))[0];
				if(tail==head) {
					int a = ((int[])list.get(x))[0];
					int b = ((int[])list.get(y))[1];
					int[] c = {a,b};
					boolean flag = true;
					for (int l = 0; l < list.size(); l++) {
						if(Arrays.toString(list.get(l)).equals(Arrays.toString(c))) {
							flag = false;
							break;
							
						}
						else
							continue;
						
					}
					if(flag) {
						list.add(c);
					}	
				}
			}
		}
		List<Integer> result = cal_partialOrderUpon(list, 4);
		for (Integer integer : result) {
			System.out.println(integer);
		}
	}
	public static void cal_new(List<List<int[]>> list1,int aim) {
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
