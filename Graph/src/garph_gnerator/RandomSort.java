package garph_gnerator;

import java.util.Arrays;
import java.util.Random;

public class RandomSort {

	private static Random rand = new Random();

    public static <T> void swap(int[] a, int i, int j){
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static <T> void randomSort(int[] a) {
        int length = a.length;
        for ( int i = length; i > 0; i-- ){
            int randInd = rand.nextInt(i);
            swap(a, randInd, i - 1);
        }
    }

    public static void main(String[] args) {
    	int a[] = new int[20];
        for(int i=0;i<a.length;i++) {
        	a[i]=i+1;
        }
        System.out.println(Arrays.toString(a));
        randomSort(a);
        System.out.println(Arrays.toString(a));
    }

}
