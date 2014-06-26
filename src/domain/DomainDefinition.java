package domain;

import java.util.HashMap;
import java.util.Map;

public class DomainDefinition {
	public Map<String, String> xmlPaths = new HashMap<String, String>();

	public void addEntry(String name, String path) {
		xmlPaths.put(name, path);
	}

	public Map<String, String> getList() {
		return xmlPaths;
	}

}
