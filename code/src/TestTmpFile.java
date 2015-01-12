import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.AbstractDocument.BranchElement;

public class TestTmpFile {
	
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader("./tmp/2010.txt"));
		String tmp;
		String line;
		List<String> list = new ArrayList<String>();
		
		while ((line = bufferedReader.readLine()) != null) {
			String[] content = line.split(",");
			if(list.contains(content[0])){
				System.out.println(content[0]);
			}else{
				list.add(content[0]);
			}
		}
		bufferedReader.close();
	}
}
