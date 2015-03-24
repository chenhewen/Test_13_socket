import net.sf.json.JSONObject;

import com.google.gson.Gson;


public class DataParser {
	
	public ProtocolModle parseProtocolModle(String jsonStr) {
		Gson gson = new Gson();
		ProtocolModle modle = gson.fromJson(jsonStr.toString(), ProtocolModle.class);
		return modle;
	}
}
