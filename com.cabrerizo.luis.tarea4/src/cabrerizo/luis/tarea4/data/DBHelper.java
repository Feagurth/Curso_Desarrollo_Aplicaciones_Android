package cabrerizo.luis.tarea4.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	public String CREATE_TABLE_STORE = "CREATE TABLE Store ("
			+ "IdStore INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL, "
			+ "name TEXT NOT NULL, " + "address TEXT NOT NULL, "
			+ "phone TEXT NOT NULL, " + "hoursOfOperation TEXT NOT NULL, "
			+ "url TEXT NOT NULL, " + "email TEXT NOT NULL, "
			+ "favorites INTEGER NOT NULL, " + "genre INTEGER NOT NULL, "
			+ "location TEXT NOT NULL, isfavorite INTEGER NOT NULL)";

	public String CREATE_TABLE_PHOTO = "CREATE TABLE Photo("
			+ "IdPhoto INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ "url TEXT NOT NULL, "
			+ "descripcion TEXT NOT NULL, "
			+ "favorites int NOT NULL, "
			+ "isfavorite INTEGER NOT NULL,"
			+ "IdStore INTEGER NOT NULL, "
			+ "FOREIGN KEY (IdStore) REFERENCES Store(IdStore) ON DELETE CASCADE ON UPDATE CASCADE);";

	public String CREATE_TABLE_COMMENTSSTORE = "CREATE TABLE CommentsStore ("
			+ "IdCommentStore INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ "comment TEXT NOT NULL, "
			+ "datecomment TEXT NOT NULL,  "
			+ "IdStore INTEGER NOT NULL, "
			+ "FOREIGN KEY (IdStore) REFERENCES Store(IdStore) ON DELETE CASCADE ON UPDATE CASCADE);";

	public String CREATE_TABLE_COMMENTSPHOTO = "CREATE TABLE CommentsPhoto ("
			+ "IdCommentPhoto INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
			+ "comment TEXT NOT NULL, "
			+ "datecomment TEXT NOT NULL, "
			+ "IdPhoto INTEGER NOT NULL, "
			+ "FOREIGN KEY (IdPhoto) REFERENCES Photo(IdPhoto) ON DELETE CASCADE ON UPDATE CASCADE );";

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_PHOTO);
		db.execSQL(CREATE_TABLE_STORE);
		db.execSQL(CREATE_TABLE_COMMENTSPHOTO);
		db.execSQL(CREATE_TABLE_COMMENTSSTORE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS COMMENTSPHOTO");
		db.execSQL("DROP TABLE IF EXISTS COMMENTSSTORE");
		db.execSQL("DROP TABLE IF EXISTS PHOTO");
		db.execSQL("DROP TABLE IF EXISTS STORE");
		onCreate(db);

	}

}
