package com.mobithink.velo.carbon;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.mobithink.velo.carbon.database.model.TripDTO;
import com.mobithink.velo.carbon.driving.DrivingActivity;
import com.mobithink.velo.carbon.managers.CarbonApplicationManager;
import com.mobithink.velo.carbon.managers.DatabaseManager;
import com.mobithink.velo.carbon.managers.RetrofitManager;
import com.mobithink.velo.carbon.preparation.ParametersActivity;
import com.mobithink.velo.carbon.recyclerviewutils.MotsRecyclerViewAdapter;
import com.mobithink.velo.carbon.webservices.TechnicalService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mobithink.velo.carbon.driving.DrivingActivity.CODE_ENVOI_TAG;
import static com.mobithink.velo.carbon.driving.DrivingActivity.ENVOI_DECLINE;
import static com.mobithink.velo.carbon.driving.DrivingActivity.ENVOI_KO;
import static com.mobithink.velo.carbon.driving.DrivingActivity.ENVOI_OK;

/**
 * MobiThink Velo
 * Activité principal.
 */
public class SplashScreenActivity extends AppCompatActivity {

    private static final int CHANGE_PARAMETER_ACTION = 3;
    private static final int ANALYSE_LINE_ACTION = 2;
    private static final int MY_REQUEST_CODE = 0;

    private int serverCallsKO=0;
    private final int SERVER_CALLS_LIMITE=5;
    private MotsRecyclerViewAdapter mRecyclerViewAdapter;
    private boolean isInfoWindowVisible=false;

    @BindView(R.id.splashactivity_rootview2)
    View mRootView;
    @BindView(R.id.analyzeButton)
    Button mAnalyzeButton;
    @BindView(R.id.server_status_iv)
    ImageView mServerStatusView;
    @BindView(R.id.mobithinkLogo)
    ImageView mMobiThinkLogo;
    @BindView(R.id.app_version_textview)
    TextView mAppVersionTextView;
    @BindView(R.id.info_window_container)
    View infoWindow;
    @BindView(R.id.close_fab)
    FloatingActionButton closeFAB;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //ButterKnife bind des composants de la vue
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fabric.with(this, new Crashlytics());

        //On fixe l'orientation de la vue
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setUpButtons();
        showInfoWindow(false);

        try {

            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            String versionText = getString(R.string.version_string).concat(version);
            mAppVersionTextView.setText(versionText);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_CODE);
        }

        //SetUp the RecyclerView
        RecyclerView mRecyclerView = findViewById(R.id.mots_recycler_view);
        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter
        mRecyclerViewAdapter = new MotsRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkServerStatus();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.general_settings:
                launchParametersActivity();
                return true;
            case R.id.voice_settings:
                showInfoWindow(true);
                return true;
            /*case R.id.upload_trips:
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showInfoWindow(boolean b) {
        if (b){
            mRecyclerViewAdapter.mDataSet(getListMots());
            infoWindow.setVisibility(View.VISIBLE);
            infoWindow.setClickable(true);
            closeFAB.show();
            isInfoWindowVisible=true;
        }else {

            infoWindow.setVisibility(View.GONE);
            infoWindow.setClickable(false);
            closeFAB.hide();
            isInfoWindowVisible=false;
        }

    }

    /**
     * Alimentation da la liste de mots enregistre pour la commande vocale
     * @return Liste avec les labels inclus
     */
    private ArrayList<String> getListMots() {
        ArrayList<String>list= new ArrayList<>();
        String[] array;
        list.add(getString(R.string.probleme));
        //Récupération de la liste d’évènements prédéfinis du type probeleme
        array = getResources().getStringArray(R.array.problemes_array);
        for (String s : array){
            list.add(s);
        }
        list.add(getString(R.string.amenagement));
        //Récupération de la liste d’évènements prédéfinis du type amenagement
        array = getResources().getStringArray(R.array.amenagement_array);
        for (String s : array){
            list.add(s);
        }
        list.add(getString(R.string.ressenti));
        //Récupération de la liste d’évènements prédéfinis du type ressenti
        array = getResources().getStringArray(R.array.ressenti_array);
        for (String s : array){
            list.add(s);
        }

        return list;
    }

    /**
     * Méthode pour paramétrer les boutons et leur Listeners
     */
    private void setUpButtons(){
        mAnalyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAnalyse();
            }
        });

        closeFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoWindow(false);
            }
        });
    }
    /**
     * Verification de l'etat du serveur
     */
    private void checkServerStatus() {
        TechnicalService technicalService = RetrofitManager.build().create(TechnicalService.class);

        Call<Void> call = technicalService.checkStatus();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 200:
                        Log.d(this.getClass().getName(),getString(R.string.is_up));
                        mServerStatusView.setImageDrawable(getDrawable(R.drawable.server_online_circle_status));
                        break;
                    default:
                        mServerStatusView.setImageDrawable(getDrawable(R.drawable.server_offline_circle_status));
                        //Control afin d'eviter un boucle infini si le serveurs est KO
                        serverCallsKO++;
                        if (serverCallsKO<SERVER_CALLS_LIMITE){
                            checkServerStatus();
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mServerStatusView.setImageDrawable(getDrawable(R.drawable.server_offline_circle_status));
                checkServerStatus();
            }
        });
    }

    /**
     * Lancer DrivingActivity pou commencer un nouveau analyse
     */
    public void launchAnalyse() {

        TripDTO tripDTO = new TripDTO();
        tripDTO.setStartTime(System.currentTimeMillis());

        DatabaseManager db = DatabaseManager.getInstance();
        db.startNewTrip();
        db.updateStatus(CarbonApplicationManager.getInstance().getCurrentTripId(),false);
        Bundle bundle = new Bundle();

        Intent startDriving = new Intent(this, DrivingActivity.class);
        startDriving.putExtras(bundle);
        this.startActivityForResult(startDriving, ANALYSE_LINE_ACTION);
    }

    /**
     * Lancer l'ativite du parametrage
     */
    public void launchParametersActivity() {
        Intent chooseParameters = new Intent(this, ParametersActivity.class);
        this.startActivityForResult(chooseParameters, CHANGE_PARAMETER_ACTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHANGE_PARAMETER_ACTION:
                //Les paramètres ont été bien enregistrées
                if (resultCode == Activity.RESULT_OK) {
                    Snackbar.make(mRootView, getString(R.string.maj_parametres), Snackbar.LENGTH_LONG).show();
                }
                break;
            case ANALYSE_LINE_ACTION:
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getExtras()!=null && data.hasExtra(CODE_ENVOI_TAG)){
                        controlMessageAfficher(data.getIntExtra(CODE_ENVOI_TAG,ENVOI_KO));
                    }else {
                        controlMessageAfficher(ENVOI_KO);
                    }
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    Snackbar.make(mRootView,R.string.trajet_anule , Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
     * Selection de messager a aficher a l'utilisateur suite a la completition du trajet
     * @param codeEnvoi Code d'envoi proventant du Driving Activity
     */
    private void controlMessageAfficher(int codeEnvoi){
        String message;
        switch (codeEnvoi){
            case ENVOI_KO:
                message=getString(R.string.evoi_echoue);
            break;
            case ENVOI_OK:
                message=getString(R.string.evoi_echoue);
                break;
            case ENVOI_DECLINE:
                message=getString(R.string.evoi_echoue);
                break;
             default:
                 message=getString(R.string.evoi_echoue);

        }
        Snackbar.make(mRootView,message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
     //Control d’affichage de infoView afin d’éviter sortir de la app si le user clique sur le bouton back.
     if (isInfoWindowVisible){
         showInfoWindow(false);
     }else{
         Intent i=new Intent(this,SplashScreenActivity.class);
         i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         finish();
        }

    }
}
