package LecteurFichiers;

import java.io.FileReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSON {
	
	private String StrJson = "";

    public String getStrJson() {
		return StrJson;
	}

	public void setStrJson(String strJson) {
		StrJson = strJson;
	}

	public void test()
    {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader("employees.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray employeeList = (JSONArray) obj;
            System.out.println(employeeList);
             
            //Iterate over employee array
            
            employeeList.forEach( emp -> {getJSONData2( (JSONObject) emp ); });
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
 
    public String getJSONData2(JSONObject JsonObj)
    {
        //Get employee object within list
        JSONObject employeeObject = (JSONObject) JsonObj.get("employee");
         
        //Get employee first name
        String firstName = (String) JsonObj.get("firstName");   
        System.out.println(firstName);
         
        //Get employee last name
        String lastName = (String) JsonObj.get("lastName"); 
        System.out.println(lastName);
         
        //Get employee website name
        String website = (String) JsonObj.get("website");   
        System.out.println(website);
        return website;
    }
    
    public void addElement(JSONObject JsonObj) {
    	StrJson += ";"+JsonObj.get("ingredients");
    }
    
    public String getList(String JsonData)
    {
    	
        try {
        	
        	//JSON parser object to parse read file
            JSONParser jsonParser = new JSONParser();
        	//Read JSON file
			Object JsonObj = jsonParser.parse(JsonData);
			
			JSONArray JsonList = (JSONArray) JsonObj;
			
			//Iterate over employee array
			JsonList.forEach( jlist -> addElement((JSONObject) jlist) );
			
			
		} catch (ParseException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
        
        return StrJson;
    	
    	/*List<List<String>> rows = Arrays.asList(
    		    Arrays.asList("Jean", "author", "Java"),
    		    Arrays.asList("David", "editor", "Python"),
    		    Arrays.asList("Scott", "editor", "Node.js")
    		);
    	return rows;
    	*/
    }
}
