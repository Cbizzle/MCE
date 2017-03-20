package example.android.my.project1;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.EventListener;


public class MainActivity extends Activity {

    //SharedPreferences data
    public static final String STATS_PREFS = "StatsPrefs" ;
    public static final String TOTAL_BATTLES = "battles" ;
    public static final String TOTAL_WINS = "wins" ;
    public static final String TOTAL_LOSSES = "losses" ;
    public static final String TOTAL_DRAWS = "draws" ;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    //added
    private ImageView imgView;  //the first ImageView in the view
    private ImageButton m_imgBtn, c_imgBtn, e_imgBtn;  // Mouse, Cat, Elephant images

    private TextView result_tv, count_tv, cmp_choice_tv;  //the textView of result and count

    private TextView winCount_tv, lossCount_tv, drawCount_tv; //textviews for stats counters

    //stats vars
    int count = 0; // initialize the game count
    int winCount = 0;
    int drawCount = 0;
    int lossCount = 0;

    //intialize a listener to monitoring the three buttons
    MCEChoiceOnClickListener mceChoiceOnClickListener = new MCEChoiceOnClickListener();

    //add two MediaPlayer objects
    private MediaPlayer mp_backgBattles;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize buttons
        m_imgBtn = (ImageButton) findViewById(R.id.btnMouse);
        c_imgBtn = (ImageButton) findViewById(R.id.btnCat);
        e_imgBtn = (ImageButton) findViewById(R.id.btnElephant);

        //initialize imgView
        imgView = (ImageView) findViewById(R.id.viewCmp);

        //initialize result and count TextView
        result_tv = (TextView) findViewById(R.id.textResult);
        count_tv = (TextView) findViewById(R.id.textCount);
        cmp_choice_tv = (TextView) findViewById(R.id.cmpChoiceText);
        winCount_tv = (TextView) findViewById(R.id.winCountTextView);
        lossCount_tv = (TextView) findViewById(R.id.lossCountTextView);
        drawCount_tv = (TextView) findViewById(R.id.drawCountTextView);

        m_imgBtn.setOnClickListener(mceChoiceOnClickListener);
        c_imgBtn.setOnClickListener(mceChoiceOnClickListener);
        e_imgBtn.setOnClickListener(mceChoiceOnClickListener);

        //declare the audio resource to these two MediaPlayer objects
        mp_backgBattles = MediaPlayer.create(this, R.raw.main);

        //retrieve old stat data using shared preferences
        sharedPreferences = getSharedPreferences(STATS_PREFS, Context.MODE_PRIVATE);
        if(sharedPreferences.contains(TOTAL_BATTLES)) {
            count = sharedPreferences.getInt(TOTAL_BATTLES, 0);
            count_tv.setText("Battles: " + count);
        }

        if(sharedPreferences.contains(TOTAL_WINS)) {
            winCount = sharedPreferences.getInt(TOTAL_WINS, 0);
            winCount_tv.setText("Wins: " + winCount);
        }

        if(sharedPreferences.contains(TOTAL_LOSSES)) {
            lossCount = sharedPreferences.getInt(TOTAL_LOSSES, 0);
            lossCount_tv.setText("Losses: " + lossCount);
        }

        if(sharedPreferences.contains(TOTAL_DRAWS)) {
            drawCount = sharedPreferences.getInt(TOTAL_DRAWS, 0);
            drawCount_tv.setText("Draws: " + drawCount);
        }

        //play backgBattles music here
        mp_backgBattles.start();
        mp_backgBattles.setLooping(true);
        mp_backgBattles.setVolume(0.75f, 0.75f);

        //end

    }

    private class MCEChoiceOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // get a random number form 1 to 3
            int rand = (int) (Math.random() * 3 + 1);
            count++;
            //store times of game in device
//            storeData(count+"");

            switch (rand) {
                /**
                 * rand = 1 means computer is Mouse,
                 * 2 represents Cat,
                 * 3 represents Elephant
                 */
                case 1:
                    imgView.setImageResource(R.drawable.badmouse);  //computer choose Mouse
                    cmp_choice_tv.setText("MOUSE");
                    switch (v.getId()) {
                        case R.id.btnMouse:   //player choose Mouse
                            result_tv.setText("Result: "
                                    + "Tied!");
                            count_tv.setText("Battles: " + count);
                            drawCount++;
                            drawCount_tv.setText("Draws: " + drawCount);
                            break;
                        case R.id.btnCat:  //player choose Cat
                            result_tv.setText("Result: "
                                    + "Win!");
                            count_tv.setText("Battles: " + count);
                            winCount++;
                            winCount_tv.setText("Wins: " + winCount);
                            break;
                        case R.id.btnElephant:  //player choose Elephant
                            result_tv.setText("Result: "
                                    + "Lose!");
                            count_tv.setText("Battles: " + count);
                            lossCount++;
                            lossCount_tv.setText("Losses: " + lossCount);
                            break;
                    }
                    break;
                case 2:
                    imgView.setImageResource(R.drawable.badcat);  //computer choose Cat
                    cmp_choice_tv.setText("CAT");
                    switch (v.getId()) {
                        case R.id.btnMouse:
                            result_tv.setText("Result: "
                                    + "Lose!");
                            count_tv.setText("Battles: " + count);
                            lossCount++;
                            lossCount_tv.setText("Losses: " + lossCount);
                            break;
                        case R.id.btnCat:
                            result_tv.setText("Result: "
                                    + "Tie!");
                            count_tv.setText("Battles: " + count);
                            drawCount++;
                            drawCount_tv.setText("Draws: " + drawCount);
                            break;
                        case R.id.btnElephant:
                            result_tv.setText("Result: "
                                    + "Win!");
                            count_tv.setText("Battles: " + count);
                            winCount++;
                            winCount_tv.setText("Wins: " + winCount);
                            break;
                    }
                    break;
                case 3:
                    imgView.setImageResource(R.drawable.badelephant);  //computer choose Elephant
                    cmp_choice_tv.setText("ELEPHANT");
                    switch (v.getId()) {
                        case R.id.btnMouse:
                            result_tv.setText("Result: "
                                    + "Win!");
                            count_tv.setText("Battles: " + count);
                            winCount++;
                            winCount_tv.setText("Wins: " + winCount);
                            break;
                        case R.id.btnCat:
                            result_tv.setText("Result: "
                                    + "Lose!");
                            count_tv.setText("Battles: " + count);
                            lossCount++;
                            lossCount_tv.setText("Losses: " + lossCount);
                            break;
                        case R.id.btnElephant:
                            result_tv.setText("Result: "
                                    + "Tie!");
                            count_tv.setText("Battles: " + count);
                            drawCount++;
                            drawCount_tv.setText("Draws: " + drawCount);
                            break;
                    }
                    break;
            }
            editor = sharedPreferences.edit();
            editor.putInt(TOTAL_BATTLES, count);
            editor.putInt(TOTAL_WINS, winCount);
            editor.putInt(TOTAL_LOSSES, lossCount);
            editor.putInt(TOTAL_DRAWS, drawCount);
            editor.apply();
        }
    }

    @Override
    protected void onDestroy() {
        mp_backgBattles.stop();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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


}
