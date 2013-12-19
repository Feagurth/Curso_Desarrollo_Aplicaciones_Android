package cabrerizo.luis.tarea4.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cabrerizo.luis.tarea4.data.models.Comment;
import cabrerizo.luis.tarea4.data.models.Photo;
import cabrerizo.luis.tarea4.data.models.Store;

public class DBAdapter {
	private DBHelper dbHelper;
	private static final String DATABASE_NAME = "kmall.db";
	private static final int DATABASE_VERSION = 9;

	public DBAdapter(Context context) {
		dbHelper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	public int insertStore(Store store) {
		int result = -1;

		ContentValues values = buildfromStore(store);

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try {
			result = (int) db.insertWithOnConflict("STORE", null, values,
					SQLiteDatabase.CONFLICT_ROLLBACK);
		} catch (Exception e) {
			result = -1;
		} finally {
			db.close();
		}

		return result;
	}

	public int insertPhoto(Photo foto, int IdStore) {
		int result = -1;

		ContentValues values = buildfromPhoto(foto, IdStore);

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			result = (int) db.insertWithOnConflict("PHOTO", null, values,
					SQLiteDatabase.CONFLICT_ROLLBACK);
		} catch (Exception e) {
			result = -1;
		} finally {
			db.close();
		}

		return result;
	}

	public int insertCommentStore(Comment comentario, int IdStore) {
		int result = -1;

		ContentValues values = buildfromCommentsStore(comentario, IdStore);

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			result = (int) db.insertWithOnConflict("COMMENTSSTORE", null,
					values, SQLiteDatabase.CONFLICT_ROLLBACK);
		} catch (Exception e) {
			result = -1;
		} finally {
			db.close();
		}

		return result;
	}

	public int insertCommentPhoto(Comment comentario, int IdPhoto) {
		int result = -1;

		ContentValues values = buildfromCommentsPhoto(comentario, IdPhoto);

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			result = (int) db.insertWithOnConflict("COMMENTSPHOTO", null,
					values, SQLiteDatabase.CONFLICT_ROLLBACK);
		} catch (Exception e) {
			result = -1;
		} finally {
			db.close();
		}

		return result;
	}

	public boolean hasValues(String nombreTabla) {
		boolean result = false;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		try {
			Cursor cursor = db.query(nombreTabla, null, null, null, null, null,
					null);
			if (cursor.getCount() >= 1) {
				result = true;
			}

		} catch (Exception e) {
			result = false;
		} finally {
			db.close();
		}
		return result;
	}

	public ContentValues buildfromStore(Store store) {
		ContentValues values = new ContentValues();

		values.put("name", store.getNombre());
		values.put("address", store.getDireccion());
		values.put("phone", store.getTelefono());
		values.put("hoursOfOperation", store.getHorarios());
		values.put("url", store.getWebsite());
		values.put("email", store.getEmail());
		values.put("favorites", store.getNumeroFavoritos());
		values.put("genre", store.getTipoTienda());
		values.put(
				"location",
				store.getUbicacionGeografica()[0] + "|"
						+ store.getUbicacionGeografica()[1]);
		values.put("isfavorite", store.getEsFavorito());

		return values;

	}

	public ContentValues buildfromPhoto(Photo foto, int IdStore) {
		ContentValues values = new ContentValues();

		values.put("url", foto.getUrl());
		values.put("descripcion", foto.getDescripcion());
		values.put("favorites", foto.getNumeroFavoritos());
		values.put("IdStore", IdStore);
		values.put("isfavorite", foto.getEsFavorito());

		return values;
	}

	public ContentValues buildfromCommentsStore(Comment comentario, int IdStore) {
		ContentValues values = new ContentValues();

		values.put("comment", comentario.getComentario());
		values.put("datecomment", comentario.getFecha());
		values.put("IdStore", IdStore);

		return values;
	}

	public ContentValues buildfromCommentsPhoto(Comment comentario, int IdPhoto) {
		ContentValues values = new ContentValues();

		values.put("comment", comentario.getComentario());
		values.put("datecomment", comentario.getFecha());
		values.put("IdPhoto", IdPhoto);

		return values;
	}

	public Cursor readStore(String IdStore) {
		Cursor cursor;
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		if (IdStore == null) {
			cursor = db.query("STORE", null, null, null, null, null, null);
		} else {
			cursor = db.query("STORE", null, "IdStore" + " = " + IdStore, null,
					null, null, null);
		}

		return cursor;
	}

	public Cursor readPhoto(String IdStore) {
		Cursor cursor;
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		if (IdStore == null) {
			cursor = db.query("PHOTO", null, null, null, null, null, null);
		} else {
			cursor = db.query("PHOTO", null, "IdStore = " + IdStore, null,
					null, null, null);

		}

		return cursor;
	}

	public Cursor readCommentsPhoto(String IdPhoto) {
		Cursor cursor;
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		if (IdPhoto == null) {
			cursor = db.query("COMMENTSPHOTO", null, null, null, null, null,
					null);
		} else {
			cursor = db.query("COMMENTSPHOTO", null, "IdPhoto = " + IdPhoto,
					null, null, null, null);

		}

		return cursor;
	}

	public Cursor readCommentsStore(String IdStore) {
		Cursor cursor;
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		if (IdStore == null) {
			cursor = db.query("COMMENTSSTORE", null, null, null, null, null,
					null);
		} else {
			cursor = db.query("COMMENTSSTORE", null, "IdStore = " + IdStore,
					null, null, null, null);
		}

		return cursor;
	}

	public int updateStore(Store store) {
		int resultado = -1;

		ContentValues values = buildfromStore(store);

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try {
			resultado = db.updateWithOnConflict("STORE", values, "IdStore =?",
					new String[] { String.valueOf(store.getId()) },
					SQLiteDatabase.CONFLICT_ROLLBACK);
		} catch (Exception e) {
			resultado = -1;
		} finally {
			db.close();
		}
		return resultado;
	}
	
	public int updatePhoto(Photo foto, int IdStore) {
		int resultado = -1;

		ContentValues values = buildfromPhoto(foto, IdStore);

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try {
			resultado = db.updateWithOnConflict("PHOTO", values, "IdPhoto =?",
					new String[] { String.valueOf(foto.getIdfoto()) },
					SQLiteDatabase.CONFLICT_ROLLBACK);
		} catch (Exception e) {
			resultado = -1;
		} finally {
			db.close();
		}
		return resultado;
	}
	
	public int deleteComment(int id, boolean esFoto)
	{
		int resultado = -1;
		String tabla;
		String clave;

		if(esFoto)
		{
			tabla = "CommentsPhoto";
			clave = "IdCommentPhoto";
		}
		else
		{
			tabla = "CommentsStore";
			clave = "IdCommentStore";
		}

		SQLiteDatabase db = dbHelper.getWritableDatabase();

		try {
			resultado = db.delete(tabla, clave + " =?", new String[]{String.valueOf(id)});
		} catch (Exception e) {
			resultado = -1;
		} finally {
			db.close();
		}
		return resultado;		
	}
}
