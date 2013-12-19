package cabrerizo.luis.tarea4.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cabrerizo.luis.tarea4.App;
import cabrerizo.luis.tarea4.data.models.Comment;
import cabrerizo.luis.tarea4.data.models.Photo;
import cabrerizo.luis.tarea4.data.models.Store;

import com.google.android.gms.maps.model.Marker;

import android.content.Context;
import android.database.Cursor;

public class Data {

	public static JSONArray ReadDataFromFile(String name, Context context) {
		StringBuilder buf = new StringBuilder();
		InputStream json;
		JSONArray jsonobj = null;
		try {

			json = context.getAssets().open(name);

			BufferedReader in = new BufferedReader(new InputStreamReader(json,
					"UTF-8"));
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
		json = Data.ReadDataFromFile(fileName, context);

		try {
			for (int i = 0; i < json.length(); i++) {
				JSONObject element = json.getJSONObject(i);

				if (element.getString("type").equals("store")) {

					String id = element.getString("id");
					String name = element.getString("name");
					String address = element.getString("address");
					String phone = element.getString("phone");
					String hoursOfOperaion = element
							.getString("hoursOfOperation");
					String url = element.getString("url");
					String email = element.getString("email");
					String tipoTienda = element.getString("genre");
					String favorites = element.getString("favorites");
					String esFavorito = element.getString("isfavorite");

					Store store = new Store();

					store.setId(Integer.parseInt(id));
					store.setNombre(name);
					store.setDireccion(address);
					store.setTelefono(phone);
					store.setHorarios(hoursOfOperaion);
					store.setWebsite(url);
					store.setEmail(email);
					store.setTipoTienda(Integer.parseInt(tipoTienda));
					store.setNumeroFavoritos(Integer.parseInt(favorites));
					store.setEsFavorito(Integer.parseInt(esFavorito));

					if (element.has("location")) {
						JSONArray obj = element.getJSONArray("location");

						double[] ubicacionGeografica = parseLocation(obj);
						store.setUbicacionGeografica(ubicacionGeografica);
					}

					if (element.has("comments")) {
						store.setListaComentarios(Data.ParseComments(element
								.getJSONArray("comments")));
					}

					JSONObject fotoObj = element.getJSONObject("photo");
					Photo foto = new Photo();

					if (fotoObj != null) {

						String picUrl = fotoObj.getString("url");
						String picDesc = fotoObj.getString("description");
						String picFav = fotoObj.getString("favorites");
						String picId = fotoObj.getString("idfoto");
						String picEsFavorito = fotoObj.getString("isfavorite");

						if (fotoObj.has("comments")) {
							foto.setListaComentarios(Data.ParseComments(fotoObj
									.getJSONArray("comments")));
						}

						foto.setIdfoto(Integer.parseInt(picId));
						foto.setDescripcion(picDesc);
						foto.setNumeroFavoritos(Integer.parseInt(picFav));
						foto.setUrl(picUrl);
						foto.setEsFavorito(Integer.parseInt(picEsFavorito));
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

	public static ArrayList<Store> ParseFromDatabase(DBAdapter db) {
		ArrayList<Store> resultado = new ArrayList<Store>();

		Cursor cursor = db.readStore(null);
		Store store = null;
		Photo photo = null;
		Comment comment;

		if (cursor.getCount() > 0) {

			while (cursor.moveToNext()) {
				store = new Store();

				store.setId(cursor.getInt(cursor.getColumnIndex("IdStore")));
				store.setNombre(cursor.getString(cursor.getColumnIndex("name")));
				store.setDireccion(cursor.getString(cursor
						.getColumnIndex("address")));
				store.setTelefono(cursor.getString(cursor
						.getColumnIndex("phone")));
				store.setHorarios(cursor.getString(cursor
						.getColumnIndex("hoursOfOperation")));
				store.setWebsite(cursor.getString(cursor.getColumnIndex("url")));
				store.setEmail(cursor.getString(cursor.getColumnIndex("email")));
				store.setNumeroFavoritos(cursor.getInt(cursor
						.getColumnIndex("favorites")));
				store.setTipoTienda(cursor.getInt(cursor
						.getColumnIndex("genre")));
				store.setEsFavorito(cursor.getInt(cursor
						.getColumnIndex("isfavorite")));

				double[] location = new double[2];

				StringTokenizer tokens = new StringTokenizer(
						cursor.getString(cursor.getColumnIndex("location")),
						"|");

				location[0] = Double.parseDouble(tokens.nextToken());
				location[1] = Double.parseDouble(tokens.nextToken());

				store.setUbicacionGeografica(location);

				Cursor cursorCommentsStore = db.readCommentsStore(String
						.valueOf(store.getId()));

				if (cursorCommentsStore.getCount() > 0) {
					ArrayList<Comment> listaComentarios = new ArrayList<Comment>();

					while (cursorCommentsStore.moveToNext()) {
						comment = new Comment();

						comment.setIdComentario(cursorCommentsStore
								.getInt(cursorCommentsStore
										.getColumnIndex("IdCommentStore")));

						comment.setComentario(cursorCommentsStore
								.getString(cursorCommentsStore
										.getColumnIndex("comment")));
						comment.setFecha(cursorCommentsStore
								.getString(cursorCommentsStore
										.getColumnIndex("datecomment")));

						listaComentarios.add(comment);
					}

					store.setListaComentarios(listaComentarios);
				}

				Cursor cursorPhoto = db
						.readPhoto(String.valueOf(store.getId()));

				if (cursorPhoto.getCount() > 0) {
					while (cursorPhoto.moveToNext()) {
						photo = new Photo();

						photo.setIdfoto(cursorPhoto.getInt(cursorPhoto
								.getColumnIndex("IdPhoto")));
						photo.setUrl(cursorPhoto.getString(cursorPhoto
								.getColumnIndex("url")));
						photo.setDescripcion(cursorPhoto.getString(cursorPhoto
								.getColumnIndex("descripcion")));
						photo.setNumeroFavoritos(cursorPhoto.getInt(cursorPhoto
								.getColumnIndex("favorites")));
						photo.setEsFavorito(cursorPhoto.getInt(cursorPhoto
								.getColumnIndex("isfavorite")));
					}

					Cursor cursorCommentsPhoto = db.readCommentsPhoto(String
							.valueOf(photo.getIdfoto()));

					if (cursorCommentsPhoto.getCount() > 0) {
						ArrayList<Comment> listaComentarios = new ArrayList<Comment>();

						while (cursorCommentsPhoto.moveToNext()) {
							comment = new Comment();

							comment.setIdComentario(cursorCommentsPhoto
									.getInt(cursorCommentsPhoto
											.getColumnIndex("IdCommentPhoto")));

							comment.setComentario(cursorCommentsPhoto
									.getString(cursorCommentsPhoto
											.getColumnIndex("comment")));
							comment.setFecha(cursorCommentsPhoto
									.getString(cursorCommentsPhoto
											.getColumnIndex("datecomment")));

							listaComentarios.add(comment);
						}

						photo.setListaComentarios(listaComentarios);
					}

					store.setFoto(photo);
				}
				resultado.add(store);
			}
		}

		return resultado;
	}

	public static ArrayList<Store> JsonToDatabase(String filename, Context context, DBAdapter db)
	{
		ArrayList<Store> storeArray;
		int IdStore;
		int IdPhoto;
	
		storeArray = Data.ParseStore(filename, context);

		for (Store tienda : storeArray) {

			IdStore = db.insertStore(tienda);

			IdPhoto = db.insertPhoto(tienda.getFoto(), IdStore);

			for (Comment comentario : tienda.getListaComentarios()) {
				db.insertCommentStore(comentario, IdStore);
			}

			for (Comment comentario : tienda.getFoto()
					.getListaComentarios()) {
				db.insertCommentPhoto(comentario, IdPhoto);
			}	
		}
		
		return storeArray;
	}
	
	public static Store locateStore(Context context, int valorID) {
		for (Store tienda : ((App) context).getStoreArray()) {
			if (tienda.getId() == valorID) {
				return tienda;

			}
		}
		return null;
	}

	public static Store locateStore(Context context, Marker marker) {
		int id = Integer.parseInt(((App) context).getMarkers().get(marker));

		for (Store tienda : ((App) context).getStoreArray()) {
			if (tienda.getId() == id) {
				return tienda;

			}
		}
		return null;
	}

	public static void updateStore(Context context, Store store) {
		ArrayList<Store> lista = ((App) context).getStoreArray();

		for (int i = 0; i < lista.size(); i++) {
			if (store.getId() == lista.get(i).getId()) {
				lista.set(i, store);
				break;
			}
		}
	}

	public static void updatePhoto(Context context, Photo foto, int IdStore) {
		ArrayList<Store> lista = ((App) context).getStoreArray();

		for (int i = 0; i < lista.size(); i++) {
			if (IdStore == lista.get(i).getId()) {
				lista.get(i).setFoto(foto);
				break;
			}
		}
	}

	public static ArrayList<Comment> updateCommentsFromDatabase(Context context, int id, boolean esFoto)
	{
		ArrayList<Comment> listaComentarios = new ArrayList<Comment>();
		Comment comment;
		String columnaId;
		Cursor cursor;
		
		if(esFoto)
		{
			cursor = ((App)context).getDb().readCommentsPhoto(String.valueOf(id));
			columnaId = "IdCommentPhoto";
			
		}
		else
		{
			cursor = ((App)context).getDb().readCommentsStore(String.valueOf(id));
			columnaId = "IdCommentStore";
		}

		if (cursor.getCount() > 0) {

			while (cursor.moveToNext()) {
				comment = new Comment();

				comment.setIdComentario(cursor
						.getInt(cursor
								.getColumnIndex(columnaId)));

				comment.setComentario(cursor
						.getString(cursor
								.getColumnIndex("comment")));
				comment.setFecha(cursor
						.getString(cursor
								.getColumnIndex("datecomment")));

				listaComentarios.add(comment);
			}				
				
		}

		return listaComentarios;
	}
}
