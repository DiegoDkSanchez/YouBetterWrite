package sv.dk.com.youbetterwrite;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.dk.com.youbetterwrite.Adapters.HistoriasAdapter;
import sv.dk.com.youbetterwrite.Modelos.ResponseData;
import sv.dk.com.youbetterwrite.Modelos.Story;

public class Seach extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements MaterialSearchBar.OnSearchActionListener, HistoriasAdapter.ItemClickListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServicioHistorias.base_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        boolean callExecute = true;
        List<Story> listaHistorias;
        List<Story> filtro;
        RecyclerView recyclerView;
        MaterialSearchBar searchBar;
        HistoriasAdapter adaptador;
        ServicioHistorias apiService = retrofit.create(ServicioHistorias.class);
        Call<ResponseData> call = apiService.getHistorias();
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_seach, container, false);
            //
            searchBar = rootView.findViewById(R.id.searchBar);
            recyclerView = rootView.findViewById(R.id.reciclerViewSearch);

            searchBar.setSpeechMode(false);
            searchBar.setOnSearchActionListener(this);

            // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            String page = ARG_SECTION_NUMBER;
            return rootView;
        }

        @Override
        public void onSearchStateChanged(boolean enabled) {

        }

        @Override
        public void onSearchConfirmed(CharSequence text) {
            int page = getArguments().getInt(ARG_SECTION_NUMBER);
            if(callExecute){
                if(page == 1){
                    callStories(searchBar.getText());
                    callExecute = false;
                }else if(page == 2){
                    callStoriesByCategory(searchBar.getText());
                    callExecute = false;
                }
            }

        }

        private void callStoriesByCategory(final String category) {
            apiService = retrofit.create(ServicioHistorias.class);
            call = apiService.getHistorias();
            call.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    if(response.body() != null){
                        listaHistorias = response.body().getData();
                    }
                    filtro = new ArrayList<>();
                    for(Story tmp : listaHistorias){
                        if(tmp.getCategory().getName().equals(category)){
                            filtro.add(tmp);
                        }
                    }
                    if(filtro.size() != 0){
                        adaptador = new HistoriasAdapter(getContext(), filtro);
                    }else {
                        adaptador = new HistoriasAdapter(getContext(), listaHistorias);
                    }
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
                    recyclerView.setAdapter(adaptador);
                    adaptador.setClickListener(PlaceholderFragment.this);
                    callExecute = true;
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Log.d("UDBLOG:Error",t.getMessage());
                    Toast desconectado = Toast.makeText(getContext(), "Desconectado", Toast.LENGTH_LONG);
                    desconectado.show();
                }
            });
        }

        private void callStories(final String parameter) {
            apiService = retrofit.create(ServicioHistorias.class);
            call = apiService.getHistorias();
            call.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    if(response.body() != null){
                        listaHistorias = response.body().getData();
                    }
                    filtro = new ArrayList<>();
                    for(Story tmp : listaHistorias){
                        if(tmp.getName().equals(parameter)){
                            filtro.add(tmp);
                        }
                    }
                    if(filtro.size() != 0){
                        adaptador = new HistoriasAdapter(getContext(), filtro);
                    }else {
                        adaptador = new HistoriasAdapter(getContext(), listaHistorias);
                    }
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
                    recyclerView.setAdapter(adaptador);
                    adaptador.setClickListener(PlaceholderFragment.this);
                    callExecute = true;
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Log.d("UDBLOG:Error",t.getMessage());
                    Toast desconectado = Toast.makeText(getContext(), "Desconectado", Toast.LENGTH_LONG);
                    desconectado.show();
                }
            });
        }

        @Override
        public void onButtonClicked(int buttonCode) {

        }

        @Override
        public void onItemClick(View view, int position) {
            Story historia;
            if(filtro.size() != 0){
                 historia = filtro.get(position);
            }else {
                 historia = listaHistorias.get(position);
            }
            Intent intent = new Intent(getContext(), HistoriaDetalle.class);
            intent.putExtra("historia", historia);
            startActivity(intent);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    public void navegacionInicio(View view) {
        Intent intent = new Intent(Seach.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    public void navegacionBuscar(View view) {

    }

    public void navegacionFavoritos(View view) {
        Intent intent = new Intent(Seach.this, Favoritos.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    public void navegacionPerfil(View view){
        Intent intent = new Intent(Seach.this, Perfil.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }
}
