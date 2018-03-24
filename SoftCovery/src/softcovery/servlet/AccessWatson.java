package softcovery.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.google.gson.*;
import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.discovery.v1.model.*;

class WatsonDiscovery {
	private static final String environmentID = "system";
	private static final String collectionID = "news-en";
	private Discovery discovery = null;
	private String filterPrefix = "enriched_text.concepts.text:";
	private QueryOptions.Builder queryBuilder = null;
	private QueryResponse queryResponse = null;
	public WatsonDiscovery() {
		queryBuilder = new QueryOptions.Builder(environmentID, collectionID);
		discovery = new Discovery("2017-11-07");
		discovery.setEndPoint("https://gateway.watsonplatform.net/discovery/api");
		discovery.setUsernameAndPassword("6ebfc93a-4013-41d2-97bd-ba99b9295375", "264qC6Rc6kET");
		
	}
	public HashMap<String, String> queryDisocveryNews(String userQuery, String[] filters, String constraint) throws IOException {
		queryBuilder.naturalLanguageQuery(userQuery);
		if(filters!=null) {
			queryBuilder.filter(makeFilter(filters, constraint));
		}
		queryResponse = discovery.query(queryBuilder.build()).execute();
		return extractURLs(queryResponse.toString());
	}
	private HashMap<String, String> extractURLs(String response) throws IOException {
		HashMap<String, String> resultPair = new HashMap<String, String>();
		JsonParser parser = new JsonParser();
		JsonObject tempObj = parser.parse(response).getAsJsonObject();
		JsonArray tempArr = tempObj.get("results").getAsJsonArray();
		for(int i=0;i<tempArr.size();i++) {
			JsonObject serviceObj = tempArr.get(i).getAsJsonObject();
			resultPair.put(serviceObj.get("url").getAsString(), serviceObj.get("title").getAsString());
		}
		return resultPair;
	}
	private String makeFilter(String[] filters, String constraint) {
		String delim = constraint.equals("all")?",":"|";
		String filter = filterPrefix+"computer science";
		for(String value:filters) {
			filter.concat(delim+filterPrefix+value);
		}
		return filter;
	}
}
/**
 * Servlet implementation class AccessWatson
 */
@WebServlet(description = "This Servelt is used to access Watson Discovery Service", urlPatterns = { "/AccessWatson" })
public class AccessWatson extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String query = null;
	private String[] filters = null;
	private String constraint = "any";
	private HashMap<String, String> result = null;

	/**
	 * Default constructor.
	 */
	public AccessWatson() {
		// TODO Auto-generated constructor stub
		//result = new HashMap<String, String>();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
		this.query = request.getParameter("query");
		this.constraint = (request.getParameter("constraint") != null) ? "all" : "any";
		this.filters = request.getParameterValues("filter");
		WatsonDiscovery watsonDisocvery = new WatsonDiscovery(); 
		result = watsonDisocvery.queryDisocveryNews(this.query, this.filters, this.constraint);
		Iterator it = result.entrySet().iterator();
		writer.append("<html><body><table>");
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			writer.append("<tr><th>URL</th><td>"+pair.getKey()+"</td><th>Title</th><td>"+pair.getValue()+"</td></tr>");
		}
		writer.append("</table></body></html>");
		//displayResult(writer, result);
	}

	public void displayResult(PrintWriter writer, HashMap<String, String> result) {
		writer.append("<html>\n" + "<head><title> SoftCovery </title>\n"
				+ "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\r\n"
				+ "    <script type=\"text/javascript\" src=\"https://code.jquery.com/jquery-3.2.1.min.js\"></script>\r\n"
				+ "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js\"></script>\r\n"
				+ "    <link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">\r\n"
				+ "    <script type=\"text/javascript\"></head>");
		writer.append("<body>\n" + "<div class=\"row container\">\r\n" + "      <div class=\"col l12 m12 s12\">\r\n"
				+ "        <div class=\"row\">\r\n"
				+ "          <div class=\"col l10 offset-l1 m10 offset-m1 s10 offset-s1\">\r\n"
				+ "            <img src=\"images/Softcovery.png\" style=\"width: 100%; height: 20%;\"/>\r\n"
				+ "          </div>\r\n" + "		   <div class=\"section\">"
				+ "            <h4 class=\"light-blue-text text-darken-4\"> Results (Top 10 news) </h4>");
		Iterator<?> iterator = result.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<?, ?> pair = (Map.Entry<?, ?>) iterator.next();
			writer.append("<ul style=\"list-style-type: none;\">" + "<li><a style=\"text-decoration: none\" href=\""
					+ pair.getKey() + "\">" + pair.getValue() + "</a></li>");
		}
		writer.append("</ul>" + "          </div>" + "        </div>" + "		</div>" + "</div>" + "</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}