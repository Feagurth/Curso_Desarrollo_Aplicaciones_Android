package cabrerizo.luis.tarea2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar.Tab;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import cabrerizo.luis.tarea2.R;


public class MainActivity extends ActionBarActivity implements OnItemClickListener {
	ListView lista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        lista = (ListView) findViewById(R.id.listaTiendas); 
        String[] listaTiendas = getResources().getStringArray(R.array.ListaTiendas);
        lista.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listaTiendas));
        lista.setOnItemClickListener(this);
                
        android.support.v7.app.ActionBar actionbar = getSupportActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tablistener = new ActionBar.TabListener() {
        
   		@Override
			public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
   			Toast.makeText(getApplicationContext(), arg0.getText(), Toast.LENGTH_SHORT).show();
				
			}

			@Override
			public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub
				
			}			
		};

		actionbar.addTab(actionbar.newTab()
		 		.setText("Listado")
		 		.setTabListener(tablistener));

		actionbar.addTab(actionbar.newTab()
		 		.setText("Mapa")
		 		.setTabListener(tablistener));


     
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		 	Intent intent = new Intent(getApplicationContext(), DetalleActivity.class);
	 	
	        intent.putExtra("valor", arg2);
	        startActivity(intent);
	}

}
