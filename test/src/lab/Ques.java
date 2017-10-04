package lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
/*
import lab.Ques1.CompareLengthOfWord;
*/
class Ques
{
	public static void main(String args[])
	{
		try 
		{
			String fname;
			 Scanner scan = new Scanner(System.in);
		       
		        System.out.print("Enter the file name u want open with its extension ");
		        fname = scan.nextLine();
		        
		BufferedReader in = new BufferedReader(new FileReader(fname));
		String str;
		
			
		List<String> li = new ArrayList<String>();
		while((str = in.readLine()) != null){
		    li.add(str);
		}

		String[] words = li.toArray(new String[0]);
	/*	System.out.println("\n"+li);
		System.out.println("\n");*/
			large(words);
		}
		
		
		
		catch(Exception e)
		{
		
			System.out.println("not found");
			
			
		}
	
		
	
	}



	public static void large(String[] sourceWords) {




        String largeString = sourceWords[0];
        String seclarge = sourceWords[0];
        List<String> stringList = listAllCompoundWords(sourceWords);



        for (String word : stringList) {

            if (word.length() >= largeString.length()) {
                seclarge=largeString;
                largeString = word;
              
             /*	if (word.length() > Seclarge.length())
             	{
             		Seclarge=word;
             	}*/
            }
            else if(word.length() >=seclarge.length())
            {
            	seclarge = word;
            }

        }
System.out.println("largest compound word is:      "+largeString);
System.out.println("second largest compund word is:    "+seclarge);

    }
  
 
 

       public static List<String> listAllCompoundWords(String[] sourceWords) {



        List<String> listCompoundWords = new ArrayList<>();

        Arrays.sort(sourceWords, new CompareLengthOfWord());



//        Comparator via lambda expressions, but longer run time.

//        Arrays.sort(words, ((o1, o2) -> o2.length() - o1.length()));



        for (String word : sourceWords) {

            if (isCompoundWord(word, sourceWords)) {

                listCompoundWords.add(word);

            }

        }

        return listCompoundWords;

    }
    public static boolean isCompoundWord(String word, String[] words) {



        String checkedWord = word;



        for (String str : words) {

            if (str.equals(word)) {

            } else if (checkedWord.contains(str)) {

                checkedWord = checkedWord.replaceAll(str, "");

                if (checkedWord.isEmpty()) {

                    return true;

                }

            }

        }

        return false;

    }
    public static class CompareLengthOfWord implements Comparator<String> {



        public int compare(String o1, String o2) {

            return o2.length() - o1.length();

        }

    }
}
