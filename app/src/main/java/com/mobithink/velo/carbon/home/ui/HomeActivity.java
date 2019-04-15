package com.mobithink.velo.carbon.home.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.mobithink.velo.carbon.R;
import com.mobithink.velo.carbon.core.ui.AbstractActivity;
import com.mobithink.velo.carbon.database.model.TripDTO;
import com.mobithink.velo.carbon.driving.DrivingActivity;
import com.mobithink.velo.carbon.managers.CarbonApplicationManager;
import com.mobithink.velo.carbon.managers.DatabaseManager;
import com.mobithink.velo.carbon.preparation.ParametersActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static com.mobithink.velo.carbon.driving.DrivingActivity.CODE_ENVOI_TAG;
import static com.mobithink.velo.carbon.driving.DrivingActivity.ENVOI_DECLINE;
import static com.mobithink.velo.carbon.driving.DrivingActivity.ENVOI_KO;
import static com.mobithink.velo.carbon.driving.DrivingActivity.ENVOI_OK;

/**
 * MobiThink Velo
 * Activité principal.
 */
public class HomeActivity extends AbstractActivity {

    private static final int CHANGE_PARAMETER_ACTION = 3;
    private static final int ANALYSE_LINE_ACTION = 2;
    private static final int MY_REQUEST_CODE = 0;

    private static final String VOCAL_COMAND_LIST_DIALOG_FRAGMENT = "VOCAL_COMAND_LIST_DIALOG_FRAGMENT";

    @BindView(R.id.splashactivity_rootview2)
    View mRootView;

    @BindView(R.id.mobithinkLogo)
    ImageView mMobiThinkLogo;

    @OnClick(R.id.analyzeButton)
    public void onClickAnalyseButton(){
        launchAnalyse();
    }

    @OnClick(R.id.info_image_button)
    public void onClickInfoButton(){
        showInfoWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //ButterKnife bind des composants de la vue
        ButterKnife.bind(this);

        setTranslucideStatusBar();
        setDarkStatusIcon();

        Fabric.with(this, new Crashlytics());

        //On fixe l'orientation de la vue
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_REQUEST_CODE);
        }
    }


    private void showInfoWindow() {
        new VocalComandListDialogFragment()
                .show(getSupportFragmentManager(), VOCAL_COMAND_LIST_DIALOG_FRAGMENT);

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

        this.finishAffinity();

    }
}
