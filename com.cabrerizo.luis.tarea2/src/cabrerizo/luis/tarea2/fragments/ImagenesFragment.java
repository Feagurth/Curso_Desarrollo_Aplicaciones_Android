package cabrerizo.luis.tarea2.fragments;

import com.cabrerizo.luis.tarea2.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImagenesFragment extends Fragment {

	final public static String RESOURCE = "resource";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View vista = inflater.inflate(R.layout.fragment_imagen, container, false);
		
		ImageView imageView =(ImageView)vista.findViewById(R.id.ImageView);
		
		Bundle args = getArguments();

		imageView.setImageResource(args.getInt(RESOURCE));
		
		return vista;
	}
	
}
