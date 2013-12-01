package cabrerizo.luis.tarea3.activities;

import android.content.res.Configuration;
import android.os.Bundle;
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
import cabrerizo.luis.tarea2.R;
import cabrerizo.luis.tarea3.fragments.ComunidadFragment;
import cabrerizo.luis.tarea3.fragments.MarcoImagenesFragment;
import cabrerizo.luis.tarea3.fragments.TiendasContentFragment;

public class MainActivity extends ActionBarActivity{
	private ActionBar actionBar;
	private ActionBarDrawerToggle drawerToggle;
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
		
		drawerToggle = new ActionBarDrawerToggle(this, 
												 drawerLayout, 
												 R.drawable.ic_navigation_drawer, 
												 R.string.Drawer_Open, 
												 R.string.Drawer_Close){
			
			public void onDrawerClosed(View view)
			{
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);
				
				
			}

			public void onDrawerOpened(View drawerView)
			{
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);
			}
		};
		
		drawerLayout.setDrawerListener(drawerToggle);
		
		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		
		
		FragmentManager manager = getSupportFragmentManager();
		
		manager.beginTransaction()
			   .add(R.id.contentFrame, fragments[0])
			   .add(R.id.contentFrame, fragments[1])
			   .add(R.id.contentFrame, fragments[2])			   
			   .commit();

		setContent(0);
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		if(item.getItemId() == android.R.id.home)
		{
			if(drawerLayout.isDrawerOpen(drawerList))
			{
				
				drawerLayout.closeDrawer(drawerList);
			}
			else
			{
				drawerLayout.openDrawer(drawerList);
				
			}
		}
		return true;
	}
	
	
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}
	
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();					
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
