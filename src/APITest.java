import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class APITest {
	final static String baseURL = "http://songle.jp/";

	public static void main(String[] args) throws ClientProtocolException, IOException {
		ArrayList<Song> song_data = query("livetune");
		System.out.println(song_data.get(0).getSongleJSONURL());
		System.out.println(song_data.get(0).getSongleData());
		
	}
	
	public static ArrayList<Song> query(String query) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		ArrayList<Song> queryres = new ArrayList<Song>();
		int page = 1;

		while (true) {
			String responseBody = Util.getHTML(baseURL+"songs/search.rss?q="+query+"&submit&page="+page);
			Document xml_doc = Jsoup.parse(responseBody, "", Parser.xmlParser());
			Elements query_res = xml_doc.select("item");
			
			if (query_res.size() == 0) break;
			else page++;
			
			for (Element e : query_res) {
				
				Song s = new Song();
				s.title = e.getElementsByTag("title").text();
				s.link = e.getElementsByTag("link").text();
				s.description = e.getElementsByTag("description").text();
				s.guid = e.getElementsByTag("guid").text();
				s.pupdate = e.getElementsByTag("pupdate").text();
				s.author = e.getElementsByTag("author").text();
				queryres.add(s);
			}
		}
		
		return queryres;
	}
}
