import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;


public class Test 
{



	public static void main(String[] args) 
	{
		 Scanner inputFile ;
	     PrintWriter outputFile;
	     
	     //outputFile.open(args[2]);
	     //inputFile.open(args[1]);
	     try 
	     {
			//files hardcoded for simplicity.
	    	inputFile = new Scanner(new FileReader("testInput.txt"));
			outputFile = new PrintWriter("output.txt");
			
	    	//inputFile = new Scanner(new FileReader(args[1]));
	    	//outputFile = new PrintWriter(args[2]);
	     
	     String line ;
	     String op;
	     TTTree tree = new TTTree();
	     int item  = -1;
	     while(inputFile.hasNextLine())
	     {
	    	  
	    	  StringTokenizer tokenizedLine = new StringTokenizer(inputFile.nextLine());
	    	  op = tokenizedLine.nextToken();
	    	 
	    	  if(tokenizedLine.hasMoreTokens())
	    		  item = Integer.parseInt(tokenizedLine.nextToken());
	         
	          if(op.equals("+"))
	          {
	        	  if(tree.add(item)) outputFile.println("successfully added item : " + item);
	        	  else  outputFile.println("item " + item + " already existent.");

	          }
	          if(op.equals("p"))
	          { 
	              tree.printToFile(outputFile);
	          }
	          if(op.equals("s"))
	          {
	        	  if(tree.search(item))
	     	    	 outputFile.println("item " + item + " found.");
	     	      else
	     	    	 outputFile.println("item " + item + " not found.");
	          }



	     	}//end of while
	     //test of the search method
	     
	     	outputFile.close();
	     }
	 	
	     catch (FileNotFoundException e) 
	     {
			e.printStackTrace();
	     }
	}

}
