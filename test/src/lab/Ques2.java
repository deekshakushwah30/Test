
package lab;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Ques2 {	

	//For each string cache result in a HashMap<Node, Boolean>
	class Point {
		int start;
		int end;
		
		public Point(int start, int end) {
			this.start = start;
			this.end = end;
		}
		@Override
		public boolean equals(Object m) {
			if (!(m instanceof Point))
	            		return false;
	        	if (m == this)
	            		return true;
	
	        	Point a = (Point) m;
	        	return ((this.start == a.start) && (this.end == a.end));
		}
	
		//Prime numbers in hashcode to minimise collision
		@Override
		public int hashCode() {
			int h1 = 7;
			h1 = 101 * h1 + Integer.valueOf(this.start).hashCode();
			h1 = 161 * h1 + Integer.valueOf(this.end).hashCode();
			return h1;
		}
		
		public String toString() {
			return this.start + " " + this.end;
		}
	}
		
	ArrayList<String> slist; 
	ArrayList<String> llength; 
	int smallest;
	
	public Ques2() {
		slist = new ArrayList<String>();
		llength = new ArrayList<String>();
		
	}
	
	//remove the string when processing from the sorted arraylist
	void delete(String str){
		int pos = Binsrch(str, this.slist, 0, this.slist.size()-1);
		this.slist.remove(pos);
	}
	
	//read the strings from input file and put in an arraylist
	void read(String path){		
		String str = null;
		BufferedReader reader = null;
		
		smallest = Integer.MAX_VALUE;
		try {		
			reader = new BufferedReader(new FileReader(path));
			while (( str = reader.readLine()) != null){
				if(str.length() == 0) {
					continue;
				}
				if (str.length() < smallest)
					smallest = str.length();
				
				slist.add(str);
			}
			 reader.close();
		} catch (IOException e) {
			System.out.println("file not containing");
		}
	}
	
	//Binary search for the substring in the sorted list, return the index where found.
	int Binsrch(String searchString, ArrayList<String> stringsArr, int start, int end) {
		if(start > end || searchString.length() < smallest)
			return -1;
		int middle = start + (end - start)/2;
		
		int result = stringsArr.get(middle).compareTo(searchString);
		if(result == 0)
			return middle;
		else if(result < 0)
			return Binsrch(searchString, stringsArr, middle+1, end);
		else 	
			return Binsrch(searchString, stringsArr, start, middle-1);
	}
	
	//Wrapper for Binary Search
	boolean CheckIfPresent(String str) {
		
		if(Binsrch(str, this.slist, 0,this. slist.size()-1) == -1) {
			return false;
		}
		
		return true;
	}
		
	//Generate substring combinations for the string and find if present in sorted list
	boolean generateCombinations(String str, int beg, int last,  HashMap<Point, Boolean> cache) {
		
		if(str.length() == 0) {
			return true;
		}
		
		if (str.length() < smallest)
			return false;
		
		
		Point n = new Point(beg, last);
		if(cache.containsKey(n)) {
			return cache.get(n);
		}
		
		if (CheckIfPresent(str)) {
			cache.put(n, true);
			return true;
		}
		
		int start = 0;
		int end = str.length();
		for(int i = start; i < end-1; i++) {
			if (CheckIfPresent(str.substring(start, i+1)) && generateCombinations(str.substring(i+1), i+beg + 1,  i+last, cache)){
				cache.put(n, true);
				return true;
			}
		}
		
		cache.put(n, false);	
		return false;
	}
	
	//Compare strings based on length in the descending order
	static class myComparator implements Comparator<String> {
		@Override
		public int compare(String s1, String s2) {
			return Integer.valueOf(s2.length()).compareTo(Integer.valueOf(s1.length()));
		}
	} 
	
	//Get the first, second longest string and count of strings that are formed from strings present in the sorted list
	void getOutput(String path) {
		read(path);
		this.llength = new ArrayList<String>(this.slist);
		Collections.sort(this.llength, new myComparator());
		int counter = 0;
		while(this.llength.size() > 0) {
			String str = this.llength.remove(0);
			this.delete(str);
			
			HashMap<Point, Boolean> cache = new HashMap<Point, Boolean>(); 
			
			if(this.generateCombinations(str, 0, str.length()-1, cache)) {
				counter += 1;
				if(counter == 1) 
					System.out.println(" largest compound word out of 100,000+items is   : "+str);
				else if(counter == 2)
					System.out.println("and seclargest compound word is   : "+str);
			}		
		}
		/*System.out.println("Total number of words: " +counter);*/
	}

	public static void main(String[] args) {
		Ques2 q = new Ques2();
		String path = "C:\\Users\\pc\\Desktop\\p1.txt";
		q.getOutput(path);
	}
}