package data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class DataReader {
    public   List<HashMap<String, String>> loadLogInData() throws IOException {
        //read Json to String
        String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\data\\LogIn.json"), StandardCharsets.UTF_8);

        //String to HashMap
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> logInDataList = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
        return logInDataList;
    }
    public   List<HashMap<String, String>> loadCreateAccountData() throws IOException {
        //read Json to String
        String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir")+"\\src\\test\\java\\data\\CreateAccount.json"), StandardCharsets.UTF_8);

        //String to HashMap
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> accountDataList = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
        return accountDataList;
    }
}
