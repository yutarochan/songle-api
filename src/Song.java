import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Song {
	static String title;
	static String link;
	static String description;
	static String guid;
	static String pupdate;
	static String author;
	
	public static int getSongleID() throws ClientProtocolException, IOException {
		String htmlData = Util.getHTML(link);
		Document html_doc = Jsoup.parse(htmlData);
		Element query_res = html_doc.getElementById("swf");
		System.out.println(query_res);
		return Integer.parseInt(query_res.attr("data-song-id").toString());
	}
	
	public static String getSongleJSONURL() throws ClientProtocolException, IOException {
		String htmlData = Util.getHTML(link);
		Document html_doc = Jsoup.parse(htmlData);
		Element query_res = html_doc.getElementById("swf");
		return query_res.attr("data-json-url").toString();
	}
	
	public static String getSongleData() throws ClientProtocolException, IOException {
		String jsonData = Util.getHTML("http://songle.jp"+getSongleJSONURL().replace("%2F", "/"));
		return jsonData;
	}
}
