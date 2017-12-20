package JSON;

import com.alibaba.fastjson.JSON;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadJSON {
    public static ArrayList<String> load(String path) throws FileNotFoundException {
        String json = new Scanner(new File(path)).useDelimiter("\\Z").next();
        ArrayList<String> statuses = new ArrayList<>();
        statuses.addAll(JSON.parseArray(json, String.class));
        return statuses;
    }
}
