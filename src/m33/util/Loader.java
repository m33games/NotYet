package m33.util;

import java.net.URL;

public class Loader {

	public Loader() {

	}

	public URL getURL(String s) {
		URL url = null;
		try {
			url = this.getClass().getResource("/data/images/"+s);
		} catch (Exception e) {
		}
		return url;
	}

}
