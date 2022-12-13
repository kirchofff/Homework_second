package JSON;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jade.core.AID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "cfg")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JSONclass {

    @XmlElement
    private String agentName;

    @XmlElement
    private String nameOfPlatform;

    @XmlElement
    private String adressOfPlatform;

}
