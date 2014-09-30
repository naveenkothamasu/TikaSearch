import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ParseTsv {
	
	private static int indx = 0;
	private static ArrayList<String> headers = new ArrayList<String>();
	
	public static void main(String[] args) {
		
		String dataFile = args[0];
		String headerFile = args[1];
		String line = null;
		try {
			BufferedReader br=new BufferedReader(new FileReader(headerFile));
			
			while((line = br.readLine()) != null){
				headers.add(line);
			}
			br.close();
			br=new BufferedReader(new FileReader(dataFile));
			indx = 0;
			if((line = br.readLine()) != null){
				readTsv(dataFile, line, headers);
				indx++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void readTsv(String fileName, String line, ArrayList<String> headers) {
		try
		{
			PrintWriter writer = new PrintWriter(fileName+"_"+indx+".xhtml", "UTF-8");
			writer.println("<html>");
			writer.println("<body>");
			writer.println("<table>");
			for(String th : headers){
				writer.println("<th>"+th+"</th>");
			}
			writer.println("<tr>");
			String[] job=line.split("\t");
			for(int j=0;j<job.length;j++)
			{
				writer.println("<td>"+job[j]+"</td>");
			}
			writer.println("</tr>");
			writer.println("</table>");
			writer.println("</body>");
			writer.println("</html>");
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}