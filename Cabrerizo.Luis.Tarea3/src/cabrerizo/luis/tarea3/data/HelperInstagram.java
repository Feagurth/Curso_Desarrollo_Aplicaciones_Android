package cabrerizo.luis.tarea3.data;

public class HelperInstagram {
	
	public final static String INSTAGRAM_CLIENT_ID = "c557db33cff74815a6aff322144dccb9";
	public final static String BASE_API_URL = "https://api.instagram.com/v1";
	
	public static String getRecentMediaUrl(String tag){
		return BASE_API_URL + "/tags/" + tag + "/media/recent?client_id=" + INSTAGRAM_CLIENT_ID;
	}	
}
