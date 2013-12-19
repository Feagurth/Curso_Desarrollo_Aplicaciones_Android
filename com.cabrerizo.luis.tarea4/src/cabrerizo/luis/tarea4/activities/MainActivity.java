package cabrerizo.luis.tarea4.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cabrerizo.luis.tarea4.data.ListViewAdapter;
import cabrerizo.luis.tarea4.fragments.ComunidadFragment;
import cabrerizo.luis.tarea4.fragments.FotoDialogFragment.NoticeDialogListener;
import cabrerizo.luis.tarea4.fragments.MarcoImagenesFragment;
import cabrerizo.luis.tarea4.fragments.TiendasContentFragment;
import cabrerizo.luis.tarea4.global.Utiles;

import com.cabrerizo.luis.tarea4.R;

public class MainActivity extends ActionBarActivity implements
		NoticeDialogListener {

	private static final int LOAD_IMAGE = 1;
	private static final int CAMARA = 2;

	private ActionBar actionBar;
	private ActionBarDrawerToggle drawerToggle;
	private ListView drawerList;
	private DrawerLayout drawerLayout;
	private String[] drawerOptions;
	private Fragment[] fragments = new Fragment[] {
			new MarcoImagenesFragment(), new TiendasContentFragment(),
			new ComunidadFragment() };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.activity_main);

		drawerList = (ListView) findViewById(R.id.leftDrawer);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		drawerOptions = getResources().getStringArray(R.array.drawer_options);

		drawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, drawerOptions));

		drawerList.setItemChecked(0, true);
		drawerList.setOnItemClickListener(new DrawerItemClickListener());

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_navigation_drawer, R.string.Drawer_Open,
				R.string.Drawer_Close) {

			public void onDrawerClosed(View view) {
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);

			}

			public void onDrawerOpened(View drawerView) {
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);
			}
		};

		drawerLayout.setDrawerListener(drawerToggle);

		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		FragmentManager manager = getSupportFragmentManager();

		manager.beginTransaction().add(R.id.contentFrame, fragments[0])
				.add(R.id.contentFrame, fragments[1])
				.add(R.id.contentFrame, fragments[2]).commit();

		setContent(0);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (drawerLayout.isDrawerOpen(drawerList)) {
				drawerLayout.closeDrawer(drawerList);
				return true;
			} else {
				drawerLayout.openDrawer(drawerList);
				return true;

			}
		}
		return false;
	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	public void setContent(int index) {
		FragmentManager manager = getSupportFragmentManager();

		switch (index) {
		case 0:
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			manager.beginTransaction().hide(fragments[1]).hide(fragments[2])
					.show(fragments[0]).commit();
			drawerList.setItemChecked(0, true);
			break;
		case 1:
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			manager.beginTransaction().hide(fragments[0]).hide(fragments[2])
					.show(fragments[1]).commit();
			drawerList.setItemChecked(1, true);

			break;
		case 2:
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			manager.beginTransaction().hide(fragments[0]).hide(fragments[1])
					.show(fragments[2]).commit();
			drawerList.setItemChecked(2, true);
			break;
		default:
			break;
		}

		actionBar.setTitle(drawerOptions[index]);
		drawerLayout.closeDrawer(drawerList);
	}

	class DrawerItemClickListener implements ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {

			setContent(position);

		}

	}

	@Override
	public void onDialogPositiveClick() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		startActivityForResult(intent, CAMARA);

	}

	@Override
	public void onDialogNegativeClick() {
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

		startActivityForResult(intent, LOAD_IMAGE);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK && data != null) {
			switch (requestCode) {
			case LOAD_IMAGE:
				deGaleria(data);

				break;
			case CAMARA:
				deCamara(data);
				break;

			}

			FragmentManager manager = getSupportFragmentManager();

			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			manager.beginTransaction().hide(fragments[0]).hide(fragments[1])
					.show(fragments[2]).commit();
		}

	}

	private void deCamara(Intent data) {
		Bundle extras = data.getExtras();

		if (extras != null) {
			ListViewAdapter lstView = (ListViewAdapter) ((ListView) fragments[2]
					.getActivity().findViewById(R.id.listaImagenes))
					.getAdapter();

			File fichero = Utiles.saveFile((Bitmap) extras.get("data"), "");

			lstView.addImage(fichero.getAbsolutePath());
		}
	}

	private void deGaleria(Intent data) {
		Uri selectedImagen = data.getData();

		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = getContentResolver().query(selectedImagen,
				filePathColumn, null, null, null);

		if (cursor.moveToFirst()) {
			int columIndex = cursor.getColumnIndex(filePathColumn[0]);

			String picturePath = cursor.getString(columIndex);

			cursor.close();

			ListViewAdapter lstView = (ListViewAdapter) ((ListView) fragments[2]
					.getActivity().findViewById(R.id.listaImagenes))
					.getAdapter();

			Bitmap bm = readBitmap(Uri.fromFile(new File(picturePath)));

			File fichero = Utiles.saveFile(bm, "");

			lstView.addImage(fichero.getAbsolutePath());
		}
	}

	public Bitmap readBitmap(Uri selectedImage) {
		Bitmap bm = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 5;
		AssetFileDescriptor fileDescriptor = null;
		try {
			fileDescriptor = getContentResolver().openAssetFileDescriptor(
					selectedImage, "r");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				bm = BitmapFactory.decodeFileDescriptor(
						fileDescriptor.getFileDescriptor(), null, options);
				fileDescriptor.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bm;
	}
}
