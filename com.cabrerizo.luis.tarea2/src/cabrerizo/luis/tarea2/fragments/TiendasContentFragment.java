package cabrerizo.luis.tarea2.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cabrerizo.luis.tarea2.R;
import cabrerizo.luis.tarea2.activities.MainActivity;

public class TiendasContentFragment extends Fragment
									implements TabListener{
	
		Fragment[] fragments = new Fragment[]{new ListadoFragment(), new MapaFragment()};

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			ActionBar actionbar = ((MainActivity)getActivity()).getSupportActionBar();
			
			actionbar.addTab(actionbar.newTab()
			 		.setText(R.string.title_fragment_Listado)
			 		.setTabListener(this));

			actionbar.addTab(actionbar.newTab()
			 		.setText(R.string.title_fragment_Mapa)
			 		.setTabListener(this));
			
			
			FragmentManager manager = getActivity().getSupportFragmentManager();
			manager.beginTransaction()
			   .add(R.id.MainContent, fragments[0])
			   .add(R.id.MainContent, fragments[1])
			   .commit();

		}

		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			Fragment esconder = null;
			Fragment mostrar = null;
			
			switch (tab.getPosition())
			{
			case 0:
				esconder = fragments[1];
				mostrar = fragments[0];
				break;
			case 1:
				esconder = fragments[0];
				mostrar = fragments[1];
			default:
				break;
			}
			
			ft.hide(esconder)
			  .show(mostrar);		
		}

		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			return inflater.inflate(R.layout.fragment_tiendas_content, container, false);
		}
}
