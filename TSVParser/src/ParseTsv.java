import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseTsv {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readTsv();
	}
	public static void readTsv() {
		try
		{
			BufferedReader br=new BufferedReader(new FileReader("computrabajo-ar-20121221.tsv"));
			String line=null;
			int i=0;
			while((line=br.readLine())!=null)
			{
				i++;
				//System.out.println("Line : "+line);
				String[] job=line.split("\t");
				//determineFields(job);
				//System.out.print("Tabs :");
				for(int j=0;j<job.length;j++)
				{
					if(i==2)
					System.out.print(j+")"+job[j]+"\n");
				}
				System.out.println();
				
			}
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public static void determineFields(String[] job)
	{
		int j=0;
		String[] dates=new String[4];
		for(int i=0;i<job.length;i++)
		{
			
			if (job[i].matches("\\d{4}-\\d{2}-\\d{2}")) {
				dates[j]=job[i];
				j++;
			}
			else if(job[i].matches("\\d{4}-\\d{1}-\\d{2}"))
			{
				dates[j]=job[i];
				j++;
				//System.out.print(job[i]+ " is a date field");
			}	    
		}
		for(int p=0;p<dates.length;p++)
		{
			//System.out.println(dates[p]);
			if(dates[p]!=null)
			{
				DateFormat formatter ; 
				Date date; 
				formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date[] job_date=new Date[4];
				try { 
					date = formatter.parse(dates[p]);
					System.out.println(formatter.format(date));
					//job_date[p]=formatter.format(date);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		System.out.println();
	}
}