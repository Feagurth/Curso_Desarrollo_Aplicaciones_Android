package cabrerizo.luis.tarea4.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class Data {

	public static JSONArray ReadDataFromFile(String name, Context context) {
		StringBuilder buf = new StringBuilder();
		InputStream json;
		JSONArray jsonobj = null;
		try {

			json = context.getAssets().open(name);

			BufferedReader in = new BufferedReader(new InputStreamReader(json));
			String str;

			while ((str = in.readLine()) != null) {
				buf.append(str);
			}

			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			jsonobj = new JSONArray(buf.toString());
			return jsonobj;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonobj;
	}

	public static ArrayList<Comment> ParseComments(JSONArray comentarios) {

		ArrayList<Comment> listaComentarios = new ArrayList<Comment>();

		if (comentarios != null) {

			JSONObject obj = null;

			for (int j = 0; j < comentarios.length(); j++) {
				Comment comment = new Comment();

				try {
					obj = comentarios.getJSONObject(j);

					comment.setComentario(obj.getString("comment"));
					comment.setFecha(obj.getString("fecha"));
				} catch (JSONException e) {
					e.printStackTrace();
				}

				if (comment != null) {
					listaComentarios.add(comment);
				}
			}
		}
		return listaComentarios;
	}

	public static double[] parseLocation(JSONArray location) {

		double[] loc = new double[2];

		if (location != null) {
			JSONObject obj = null;

			for (int i = 0; i < location.length(); i++) {
				try {
					obj = location.getJSONObject(i);
					loc[0] = Double.parseDouble(obj.getString("lat"));
					loc[1] = Double.parseDouble(obj.getString("lon"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}

		return loc;
	}

	public static ArrayList<Store> ParseStore(String fileName, Context context) {

		ArrayList<Store> storeArray = new ArrayList<Store>();
		JSONArray json = new JSONArray();
		json = Data.ReadDataFromFile("data", context);

		try {
			for (int i = 0; i < json.length(); i++) {
				JSONObject element = json.getJSONObject(i);

				if (element.getString("type").equals("store")) {
					String name = element.getString("name");
					String address = element.getString("address");
					String phone = element.getString("phone");
					String hoursOfOperaion = element
							.getString("hoursOfOperation");
					String url = element.getString("url");
					String email = element.getString("email");
					String favorites = element.getString("favorites");

					Store store = new Store();

					store.setNombre(name);
					store.setDireccion(address);
					store.setTelefono(phone);
					store.setHorarios(hoursOfOperaion);
					store.setWebsite(url);
					store.setEmail(email);
					store.setNumeroFavoritos(Integer.parseInt(favorites));

					if (element.has("location")) {
						JSONArray obj = element.getJSONArray("location");

						double[] ubicacionGeografica = parseLocation(obj);
						store.setUbicacionGeografica(ubicacionGeografica);
					}

					if (element.has("comments")) {
						store.setListadoComentarios(Data.ParseComments(element
								.getJSONArray("comments")));
					}

					JSONObject fotoObj = element.getJSONObject("photo");
					Photo foto = new Photo();

					if (fotoObj != null) {

						String picUrl = fotoObj.getString("url");
						String picDesc = fotoObj.getString("description");
						String picFav = fotoObj.getString("favorites");

						if (fotoObj.has("comments")) {
							foto.setListaComentarios(Data.ParseComments(fotoObj
									.getJSONArray("comments")));
						}

						foto.setDescripcion(picDesc);
						foto.setNumeroFavoritos(Integer.parseInt(picFav));
						foto.setUrl(picUrl);
					}

					store.setFoto(foto);

					storeArray.add(store);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return storeArray;
	}
}
