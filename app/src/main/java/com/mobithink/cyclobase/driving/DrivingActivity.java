package com.mobithink.cyclobase.driving;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;
import androidx.core.app.ActivityCompat;

import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mobithink.cyclobase.database.DatabaseOpenHelper;
import com.mobithink.cyclobase.ui.DrivingButton;
import com.mobithink.velo.carbon.R;
import com.mobithink.cyclobase.core.ui.AbstractActivity;
import com.mobithink.cyclobase.database.model.EventDTO;
import com.mobithink.cyclobase.database.model.TripDTO;
import com.mobithink.cyclobase.managers.CarbonApplicationManager;
import com.mobithink.cyclobase.managers.DatabaseManager;
import com.mobithink.cyclobase.managers.RetrofitManager;
import com.mobithink.cyclobase.services.LocationService;
import com.mobithink.cyclobase.utils.CustomPopup;
import com.mobithink.cyclobase.webservices.TechnicalService;
import com.mobithink.cyclobase.webservices.TripService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DrivingActivity extends AbstractActivity {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 001;
    private static final int SPEACH_RECOGNITION_TAG =100;
    public static final int MY_REQUEST_CODE = 1234;

    public static final String CODE_ENVOI_TAG="code_envoi_tag";
    public static final int ENVOI_KO = 0;
    public static final int ENVOI_DECLINE=1;
    public static final int ENVOI_OK=2;

    private int serverCallsKO=0;
    private final int SERVER_CALLS_LIMITE=5;

    private boolean isFabEmotExtended = false;

    private View mRootView;

    private long tripId;

    private ArrayList<String> listeProblemesEvenements;
    private ArrayList<String> listeAmenagementEvenements;
    private ArrayList<String> listeRessentiEvenements;

    private HashMap<String, EventDTO> eventInProgress = new HashMap<>();

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private Location mLocation = null;

    int event = 0;

    @BindView(R.id.finish_trip)
    ImageButton finishTripIb;

    @BindView(R.id.voie_partage)
    DrivingButton voiePartageBt;
    @BindView(R.id.bande)
    DrivingButton bandeBt;
    @BindView(R.id.contresens)
    DrivingButton contreSensBt;
    @BindView(R.id.couloir_bus)
    DrivingButton couloirBusBt;
    @BindView(R.id.piste)
    DrivingButton pisteBt;
    @BindView(R.id.voie_verte)
    DrivingButton voieVerte;

    @BindView(R.id.vocal)
    ImageButton vocalBt;

    @BindView(R.id.simpleChronometer)
    Chronometer chronometer;
    @BindView(R.id.start_textview)
    TextView starttextView;
    @BindView(R.id.event_number_textview)
    TextView eventNumbertextView;

    @BindView(R.id.warning_fab)
    FloatingActionButton emotFab;
    @BindView(R.id.fab_warning1)
    View fab1;
    @BindView(R.id.fab_warning2)
    View fab2;
    @BindView(R.id.fab_warning3)
    View fab3;
    @BindView(R.id.fab_warning4)
    View fab4;

    private void animateFab() {
        if(isFabEmotExtended){
            fab1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close));
            fab2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close));
            fab3.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close));
            fab4.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close));

            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);

            isFabEmotExtended = false;

            emotFab.setImageDrawable(getDrawable(R.drawable.ic_warning));

        } else {

            fab1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open));
            fab2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open));
            fab3.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open));
            fab4.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open));

            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);

            isFabEmotExtended = true;

            emotFab.setImageDrawable(getDrawable(R.drawable.ic_cross));


        }
    }

    CustomPopup customPopup;

    View.OnClickListener fabClicklistener = v -> {

        int id = v.getId();
        EventDTO eventDTO;
        switch (id){
            case R.id.warning_fab:
                break;

            case R.id.fab_warning1:
                eventDTO = createEvent(getString(R.string.warning_label1), null, getString(R.string.even_type_incident));
                eventInProgress.put(getString(R.string.warning_label1), eventDTO);
                break;

            case R.id.fab_warning2:
                eventDTO = createEvent(getString(R.string.warning_label2), null, getString(R.string.even_type_incident));
                eventInProgress.put(getString(R.string.warning_label2), eventDTO);
                break;

            case R.id.fab_warning3:
                eventDTO = createEvent(getString(R.string.warning_label3), null, getString(R.string.even_type_incident));
                eventInProgress.put(getString(R.string.warning_label3), eventDTO);
                break;

            case R.id.fab_warning4:
                eventDTO = createEvent(getString(R.string.warning_label4), null, getString(R.string.even_type_incident));
                eventInProgress.put(getString(R.string.warning_label4), eventDTO);
                break;

        }

        animateFab();
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_driving);
        ButterKnife.bind(this);

        EmojiCompat.Config config = new BundledEmojiCompatConfig(this)
                .setEmojiSpanIndicatorEnabled(true);
        EmojiCompat.init(config);

        chronometer.start();

        emotFab.setOnClickListener(fabClicklistener);
        fab1.setOnClickListener(fabClicklistener);
        fab2.setOnClickListener(fabClicklistener);
        fab3.setOnClickListener(fabClicklistener);
        fab4.setOnClickListener(fabClicklistener);


        SimpleDateFormat sdf = new SimpleDateFormat("HH'h'mm");

        starttextView.setText(sdf.format(Calendar.getInstance().getTime()));

        tripId=CarbonApplicationManager.getInstance().getCurrentTripId();


        AlertDialog.Builder builder = new AlertDialog.Builder(DrivingActivity.this);


        customPopup = new CustomPopup(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_REQUEST_CODE);
        }

        mRootView = findViewById(R.id.rootview);

        setUpButtons();
        setUpLocationServices();
        setUpListesEvenement();

        Intent serviceIntent = new Intent(this, LocationService.class);
        startService(serviceIntent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                startLocationUpdates();
                getLastKnownLocation();
        }
    }

    /**
     * Méthode pour paramétrer les boutons et leur Listeners
     */
    private void setUpButtons(){

        vocalBt.setOnLongClickListener(v -> {
            getSpeechInput();
            return true;
        });

        couloirBusBt.setOnLongClickListener(drivingButtonLongClicListener);
        voieVerte.setOnLongClickListener(drivingButtonLongClicListener);
        pisteBt.setOnLongClickListener(drivingButtonLongClicListener);
        contreSensBt.setOnLongClickListener(drivingButtonLongClicListener);
        bandeBt.setOnLongClickListener(drivingButtonLongClicListener);
        voiePartageBt.setOnLongClickListener(drivingButtonLongClicListener);
        finishTripIb.setOnLongClickListener(v -> {
            chronometer.stop();
            endTrip();
            return true;
        });

    }


    View.OnLongClickListener drivingButtonLongClicListener = v -> {

        String name = ((DrivingButton) v).getname();

        if(v.isSelected()){
            v.setSelected(false);

            EventDTO eventToUpdate = eventInProgress.get(name);

            if(null != eventToUpdate){
                updateEvent(eventToUpdate);
                eventInProgress.remove(eventToUpdate);
            }

        }else{
            v.setSelected(true);

            EventDTO eventDTO = createEvent(name, null, getString(R.string.event_type_amenagement));
            eventInProgress.put(name, eventDTO);
        }

        return false;
    };


    /**
     * Creation du menu emergent pour afficher les emojis

    private void customPopupButonSetUp() {
        customPopup.build();
        EmojiButton paniqueButton = customPopup.getEmojiButton(R.id.panique);
        StringBuffer stringBuffer = new StringBuffer(new String(Character.toChars(0x1F625	)));
        Log.d("SB", stringBuffer.toString());
        paniqueButton.setText(stringBuffer);
        paniqueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiButtonClick(v.getId());
            }
        });

        EmojiButton anxieuxButton = customPopup.getEmojiButton(R.id.anxieux);
        stringBuffer = new StringBuffer(new String(Character.toChars(0x1F613	)));
        Log.d("SB", stringBuffer.toString());
        anxieuxButton.setText(stringBuffer);
        anxieuxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiButtonClick(v.getId());
            }
        });

        EmojiButton agaceButton = customPopup.getEmojiButton(R.id.agace);
        stringBuffer = new StringBuffer(new String(Character.toChars(0x1F623	)));
        Log.d("SB", stringBuffer.toString());
        agaceButton.setText(stringBuffer);
        agaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiButtonClick(v.getId());
            }
        });


        EmojiButton deconcerteButton = customPopup.getEmojiButton(R.id.deconcerte);
        stringBuffer = new StringBuffer(new String(Character.toChars(0x1F616	)));
        Log.d("SB", stringBuffer.toString());
        deconcerteButton.setText(stringBuffer);
        deconcerteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiButtonClick(v.getId());
            }
        });
    }

    private void emojiButtonClick(int id) {
        String emoji= null;
        switch (id){
            case R.id.panique:
                emoji=getString(R.string.panique).toLowerCase();
                break;
            case R.id.anxieux:
                emoji=getString(R.string.anxieux).toLowerCase();
                break;

            case R.id.agace:
                emoji=getString(R.string.agace).toLowerCase();
                break;

            case R.id.deconcerte:
                emoji=getString(R.string.deconcentre).toLowerCase();
                break;

             default:
                 Log.e("error", "unsuported emoji");
                 break;
        }

        if (emoji!=null){
            createEvent(emoji, null, getString(R.string.ressenti));
        }

    }*/

    /**
     * Methode pour recuperer les listes de commands vocales
     */
    private void setUpListesEvenement() {
        listeProblemesEvenements= new ArrayList<>();
        listeAmenagementEvenements = new ArrayList<>();
        listeRessentiEvenements = new ArrayList<>();
        String[] array;
        //Récupération de la liste d’évènements prédéfinis du type probeleme
        array = getResources().getStringArray(R.array.problemes_array);
        for (String s : array){
            listeProblemesEvenements.add(s);
        }
        //Récupération de la liste d’évènements prédéfinis du type amenagement
        array = getResources().getStringArray(R.array.amenagement_array);
        for (String s : array){
            listeAmenagementEvenements.add(s);
        }

        //Récupération de la liste d’évènements prédéfinis du type ressenti
        array = getResources().getStringArray(R.array.ressenti_array);
        for (String s : array){
            listeRessentiEvenements.add(s);
        }
    }

    /**
     * Methode pour parametrer le services d'hubication
     */
    private void setUpLocationServices(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                mLocation = locationResult.getLastLocation();
                startTrip();
            }

        };

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEACH_RECOGNITION_TAG:
                traitementControlVocal(resultCode, data);
                break;
        }
    }

    /**
     * Ajout d’un évènement dans la base de donnes local
     * @param name Nom d'evenemet
     * @param memoVocal not Null si l'eventment est du type voice_memo
     * @param eventType Type d'evenement
     */
    private EventDTO createEvent(String name, String memoVocal, String eventType) {

        EventDTO eventDTO = new EventDTO();
        eventDTO.setname(name);
        eventDTO.setEventType(eventType);
        eventDTO.setStartTime(System.currentTimeMillis());

        event++;
        eventNumbertextView.setText(String.valueOf(event));

        if (mLocation != null) {
            eventDTO.setGpsLatStart(mLocation.getLatitude());
            eventDTO.setGpsLongStart(mLocation.getLongitude());
        }
        if (memoVocal!=null && !memoVocal.isEmpty()){
            eventDTO.setVoiceMemo(memoVocal);
        }
        eventDTO.setId(DatabaseManager.getInstance().createNewEvent(tripId, eventDTO));
        if (eventDTO.getId()>0){
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        }

        return eventDTO;


    }

    private void updateEvent(EventDTO eventDTO) {
        if (mLocation != null) {
            eventDTO.setEndTime(System.currentTimeMillis());
            eventDTO.setGpsLatEnd(mLocation.getLatitude());
            eventDTO.setGpsLongEnd(mLocation.getLongitude());
        }
        DatabaseManager.getInstance().updateEvent(eventDTO);
        Snackbar.make(mRootView,getString(R.string.evenement_cree), Snackbar.LENGTH_SHORT).show();

    }

    public void startTrip() {
        ContentValues values = new ContentValues();

        if (mLocation != null) {
            values.put(DatabaseOpenHelper.KEY_LATITUDE,mLocation.getLatitude());
            values.put(DatabaseOpenHelper.KEY_LONGITUDE, mLocation.getLongitude());
        }

        tripId = DatabaseManager.getInstance().updateTrip(CarbonApplicationManager.getInstance().getCurrentTripId(), values);
    }

    public void endTrip() {

        ContentValues values = new ContentValues();

        if (mLocation != null) {
            values.put(DatabaseOpenHelper.KEY_END_DATETIME,System.currentTimeMillis());
            values.put(DatabaseOpenHelper.KEY_END_LATITUDE,mLocation.getLatitude());
            values.put(DatabaseOpenHelper.KEY_END_LONGITUDE, mLocation.getLongitude());
        }
        tripId = DatabaseManager.getInstance().updateTrip(CarbonApplicationManager.getInstance().getCurrentTripId(), values);
        stopService(new Intent(this, LocationService.class));
        showSendDataDialog();
    }



    private void showSendDataDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle("Saisie terminée");
        alertDialogBuilder.setMessage("Fin de saisie pour la ligne, souhaitez vous envoyer les données au serveur ?");
        alertDialogBuilder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showProgressDialog();
                checkServerStatus();
                dialog.cancel();
            }
        });

        alertDialogBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseManager.getInstance().updateStatus(tripId,false);
                returnToSplashScreenActivity(ENVOI_DECLINE);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
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
                        sendTripDto();
                        break;
                    default:
                        //Control afin d'eviter un boucle infini si le serveurs est KO
                        serverCallsKO++;
                        if (serverCallsKO<SERVER_CALLS_LIMITE){
                            checkServerStatus();
                        }else {
                            hideProgressDialog();
                            returnToSplashScreenActivity(ENVOI_KO);
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                checkServerStatus();
            }
        });
    }

    private void sendTripDto() {

        TripDTO tripDto = DatabaseManager.getInstance().getFullTripDTODataToSend(tripId);

        TripService tripService = RetrofitManager.build().create(TripService.class);

        Call<Void> call = tripService.register(tripDto);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 201:
                        hideProgressDialog();
                        setResult(RESULT_OK);
                        DatabaseManager.getInstance().updateStatus(tripId,true);
                        returnToSplashScreenActivity(ENVOI_OK);
                        break;
                    default:
                        hideProgressDialog();
                        Log.e(getString(R.string.send_trip),getString(R.string.response_serveur)+response.code());
                        DatabaseManager.getInstance().updateStatus(tripId,false);
                        returnToSplashScreenActivity(ENVOI_KO);
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Snackbar.make(mRootView, getString(R.string.evoi_echoue), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void getSpeechInput() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, SPEACH_RECOGNITION_TAG);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Ce Méthode permet de gérer les commandes vocales.
     * Si le command retourné par le service est parmi la liste des évènements prédéfinis le traitement va créer un évènement spécifique
     * Sinon, un voice memo sera créé en utilisant un évènement du type générique
     * @param resultCode Retour de l'API
     * @param data L'intent avec le resultat de la conversion vocal vers text
     */
    private void traitementControlVocal(int resultCode, Intent data){
        if (resultCode == RESULT_OK && data != null) {
            String evenement;
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String commandeVocale=result.get(0);
            getLastKnownLocation();
            if (listeProblemesEvenements.contains(commandeVocale.toLowerCase())){
                //si c'est une action prédéfini du type probeleme
                createEvent(commandeVocale,
                        null,
                        getString(R.string.probleme));

            }else if(listeAmenagementEvenements.contains(commandeVocale)){
                //si c'est une action prédéfini du type amenagement
                createEvent(commandeVocale,
                        null,
                        getString(R.string.amenagement));
            }
            else if(listeRessentiEvenements.contains(commandeVocale)){
                //si c'est une action prédéfini du type Ressenti
                createEvent(commandeVocale,
                        null,
                        getString(R.string.ressenti));
            }
            else {
                //Sinon, on cree un evenenemnt generique pour ajouter une voice memo
                evenement=getString(R.string.action_generique);
                createEvent(evenement,
                        commandeVocale,
                        getString(R.string.voice_memo));
            }
        }
        else {
            Snackbar.make(mRootView,getString(R.string.erreur_commande_vocale), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Recuperation de l'ubication
     */
    private void getLastKnownLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askLocationServicesPermission();
        }

        Task<Location> lastLocation = mFusedLocationClient.getLastLocation();
        lastLocation.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                mLocation=location;

            }
        });
    }

    /**
     * Methode pour demander le droit d'acces a l'ubucation
     */
    private void askLocationServicesPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission_group.LOCATION)) {

            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.permission_is_needed))
                    .setMessage(getString(R.string.why_permission_is_needed))
                    .setPositiveButton(getString(R.string.ok_button), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(DrivingActivity.this,
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }

    /**
     * start location Updates
     */
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //Method the manages the location permission
            askLocationServicesPermission();

        } else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        }
    }

    /**
     * Stop the location updates to reduce power consumption
     */
    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    /**
     * Méthode pour revenir à l’activité principale.
     * Code envoi correspond aux valeurs suivants
     * Erreur Transmition
     * Envoi Decline par l’utilisatuer
     * Enovi Correcte
     * @param codeEnvoi int pour notifier quel message afficher a l'utilisateur dans l'activite princiaple
     */
    private void returnToSplashScreenActivity(int codeEnvoi){


        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(CODE_ENVOI_TAG,codeEnvoi);
        returnIntent.putExtras(bundle);

        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }
}
