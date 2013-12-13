package cabrerizo.luis.tarea3.data;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.cabrerizo.luis.tarea3.R;
import cabrerizo.luis.tarea3.fragments.ImagenesFragment;

public class ImagenesFragmentAdapter extends FragmentPagerAdapter {

	public ImagenesFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		
		int[] arrayImagenes = new int[]{
				R.drawable.viewpager_imagen1,
				R.drawable.viewpager_imagen2,
				R.drawable.viewpager_imagen3,
				R.drawable.viewpager_imagen4,
				R.drawable.viewpager_imagen5};

		Fragment fragmento = new ImagenesFragment();
		Bundle args = new Bundle();
		
		args.putInt(ImagenesFragment.RESOURCE, arrayImagenes[position]);
		
		fragmento.setArguments(args);
		
		return fragmento;
	}

	@Override
	public int getCount() {
		return 5;
	}

}
