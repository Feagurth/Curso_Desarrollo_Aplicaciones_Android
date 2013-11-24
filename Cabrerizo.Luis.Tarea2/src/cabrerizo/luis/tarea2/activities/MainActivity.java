package cabrerizo.luis.tarea2.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import cabrerizo.luis.tarea2.R;
import cabrerizo.luis.tarea2.fragments.ComunidadFragment;
import cabrerizo.luis.tarea2.fragments.MarcoImagenesFragment;
import cabrerizo.luis.tarea2.fragments.TiendasContentFragment;

public class MainActivity extends ActionBarActivity{
	private ActionBar actionBar;
	
	private ListView drawerList;
	private DrawerLayout drawerLayout;
	private String[] drawerOptions;
	private Fragment[] fragments = new Fragment[]{
			new MarcoImagenesFragment(),
			new TiendasContentFragment(),
			new ComunidadFragment()};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		drawerList = (ListView)findViewById(R.id.leftDrawer);
		drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
		drawerOptions = getResources().getStringArray(R.array.drawer_options);
		
		drawerList.setAdapter(new ArrayAdapter<String> (this, R.layout.drawer_list_item, drawerOptions));
		
		drawerList.setItemChecked(0, true);
		drawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		FragmentManager manager = getSupportFragmentManager();
		
		manager.beginTransaction()
			   .add(R.id.contentFrame, fragments[0])
			   .add(R.id.contentFrame, fragments[1])
			   .add(R.id.contentFrame, fragments[2])			   
			   .commit();

		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		
		setContent(0);
		
	}
	
	public void setContent(int index)
	{
		FragmentManager manager = getSupportFragmentManager();
		
		switch (index)
		{
		case 0:
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			manager.beginTransaction()
			   .hide(fragments[1])
			   .hide(fragments[2])
			   .show(fragments[0])
			   .commit();
			drawerList.setItemChecked(0, true);
			break;
		case 1:
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			manager.beginTransaction()
			   .hide(fragments[0])
			   .hide(fragments[2])
			   .show(fragments[1])
			   .commit();
			drawerList.setItemChecked(1, true);
			
			break;
		case 2:
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			manager.beginTransaction()
			   .hide(fragments[0])
			   .hide(fragments[1])
			   .show(fragments[2])
			   .commit();
			drawerList.setItemChecked(2, true);
			
			break;			
		default:
			break;
		}
		
		actionBar.setTitle(drawerOptions[index]);
		drawerLayout.closeDrawer(drawerList);		
	}
	
	
	
	class DrawerItemClickListener implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			
			setContent(position);
			
		}
		
		
	}
}
