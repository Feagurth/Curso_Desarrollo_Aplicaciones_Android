package cabrerizo.luis.tarea1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements OnItemClickListener {
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
	
		 	Intent intent = new Intent(getApplicationContext(), DetalleActivity.class);
	 	
	        intent.putExtra("valor", arg2);
	        startActivity(intent);
		
		
	}

}
