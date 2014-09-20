import java.io.BufferedReader;
import java.io.FileReader;


public class ParseTsv {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readTsv();

	}
	public static void readTsv() {
		try
		{
			BufferedReader br=new BufferedReader(new FileReader("/mnt/572/computrabajo-ar-20121127.tsv"));
			String line=null;
			while((line=br.readLine())!=null)
			{
				System.out.println("Line: "+line);
			}
			br.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}

