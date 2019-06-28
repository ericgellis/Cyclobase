package com.mobithink.cyclobase.preparation;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mobithink.velo.carbon.R;
import com.mobithink.cyclobase.managers.PreferenceManager;

import java.text.DecimalFormat;

/**
 * Created by mplaton on 31/01/2017.
 */

public class ParametersActivity extends Activity {


    private static final int MINIMUM_FREQUENCY_VALUE = 1;
    private static final int FREQUENCY_STEP = 10;

    private static final int MINIMUM_RADIUS_VALUE = 50;
    private static final int RADIUS_STEP = 50;

    private static final int MINIMUM_COST_VALUE = 0;

    SeekBar mFrequencySeekBar;
    SeekBar mRadiusSeekBar;
    SeekBar mCostbyMinutSeekBar;
    SeekBar mCostbyKilometerSeekBar;

    TextView mFrequencyNumberTextView;
    TextView mRadiusNumberTextView;
    TextView mCostByMinutNumberTextView;
    TextView mCostByKilometerNumberTextView;

    Button mValidationButton;
    Toolbar mParametersToolBar;

    private int mRadiusValue;
    private int mFrequencyValue;
    private int mCostByMinutValue;
    private int mCostByKilometerValue;

    private boolean hasFrequencyChanged = false;
    private boolean hasRadiusChanged = false;
    private boolean hasCostChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_parameters);

        mParametersToolBar = findViewById(R.id.parametersToolBar);
        mParametersToolBar.setTitle("Paramètres");
        mParametersToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mFrequencyValue = (PreferenceManager.getInstance().getTimeFrequency() - MINIMUM_FREQUENCY_VALUE);
        mRadiusValue = (PreferenceManager.getInstance().getStationRadius() - MINIMUM_RADIUS_VALUE);
        mCostByMinutValue = (PreferenceManager.getInstance().getCostOfProductionByMinute() - MINIMUM_COST_VALUE);
        mCostByKilometerValue = (PreferenceManager.getInstance().getCostOfProductionByKilometer() - MINIMUM_COST_VALUE);

        mFrequencySeekBar = findViewById(R.id.frequency_seekbar);
        mFrequencySeekBar.setProgress(mFrequencyValue);

        mRadiusSeekBar = findViewById(R.id.radius_seekbar);
        mRadiusSeekBar.setProgress(mRadiusValue);

        mCostbyMinutSeekBar = findViewById(R.id.cost_seekbar);
        mCostbyMinutSeekBar.setMax(100);

        mCostbyKilometerSeekBar = findViewById(R.id.kilometer_cost_seekbar);
        mCostbyKilometerSeekBar.setMax(100);

        mFrequencyNumberTextView = findViewById(R.id.frequencyNumber);
        mFrequencyNumberTextView.setText(String.valueOf(PreferenceManager.getInstance().getTimeFrequency()));


        mRadiusNumberTextView = findViewById(R.id.radiusNumber);
        mRadiusNumberTextView.setText(String.valueOf(PreferenceManager.getInstance().getStationRadius()));

        mCostByMinutNumberTextView = findViewById(R.id.costNumber);
        mCostByMinutNumberTextView.setText(String.valueOf(PreferenceManager.getInstance().getCostOfProductionByMinute()));

        mCostByKilometerNumberTextView = findViewById(R.id.kilometerCostNumber);
        mCostByKilometerNumberTextView.setText(String.valueOf(PreferenceManager.getInstance().getCostOfProductionByKilometer()));

        mFrequencySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hasFrequencyChanged = true;
                mFrequencyValue = (Math.round(progress/FREQUENCY_STEP))*FREQUENCY_STEP + MINIMUM_FREQUENCY_VALUE;
                mFrequencyNumberTextView.setText(String.valueOf(mFrequencyValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mRadiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hasRadiusChanged = true;
                mRadiusValue = (Math.round(progress/RADIUS_STEP))*RADIUS_STEP + MINIMUM_RADIUS_VALUE;
                mRadiusNumberTextView.setText(String.valueOf(mRadiusValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mCostbyMinutSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hasCostChanged = true;

                double mCostValue = progress*0.1f;
                DecimalFormat df = new DecimalFormat("#.##");
                mCostByMinutNumberTextView.setText(String.valueOf(df.format(mCostValue)));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        mCostbyKilometerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hasCostChanged = true;

                double mCostValue = progress*0.1f;
                DecimalFormat df = new DecimalFormat("#.##");
                mCostByKilometerNumberTextView.setText(String.valueOf(df.format(mCostValue)));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        mValidationButton = findViewById(R.id.register_parameter_button);
        mValidationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPreference();
            }
        });

    }

    private void registerPreference() {
        if (hasFrequencyChanged) {
            PreferenceManager.getInstance().setTimeFrequency(mFrequencyValue);
        }
        if (hasRadiusChanged) {
            PreferenceManager.getInstance().setStationRadius(mRadiusValue);
        }

        if (hasCostChanged){
            PreferenceManager.getInstance().setCostOfProductionByMinute(mCostByMinutValue);
            PreferenceManager.getInstance().setCostOfProductionByKilometer(mCostByKilometerValue);
        }

        if (hasRadiusChanged || hasFrequencyChanged || hasCostChanged) {
            setResult(RESULT_OK);
        }
        this.finish();

    }
}
