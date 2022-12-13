package JSON;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jade.core.AID;
import lombok.SneakyThrows;

import java.util.NoSuchElementException;

public class FromJSON {
    @SneakyThrows
    public JSONclass parse (AID aid){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            aid.getHap();
            JSONclass parseAID = new JSONclass(aid.getLocalName(),aid.getHap(), aid.getAllAddresses().next().toString());
            String str = gson.toJson(parseAID, JSONclass.class);
            return parseAID;
        } catch (NoSuchElementException exp){
            JSONclass parseAID = new JSONclass(aid.getLocalName(),aid.getHap(), "AID_don't_have_address");
            return parseAID;
        }
    }
}
