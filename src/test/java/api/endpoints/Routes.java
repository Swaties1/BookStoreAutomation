package api.endpoints;

public class Routes {

	public static String base_url="https://demoqa.com";
	
	//Account module
	public static String postUser_url=base_url+"/Account/v1/User";
	public static String generateToken_url = base_url + "/Account/v1/GenerateToken";
	public static String authorizedUser_url = base_url + "/Account/v1/Authorized";
	public static String getUser_url = base_url + "/Account/v1/User/"; 
	public static String deleteUser_url = base_url + "/Account/v1/User/";//{UUID}";
	
	
	
}
