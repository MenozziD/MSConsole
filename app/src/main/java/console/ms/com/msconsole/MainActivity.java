package console.ms.com.msconsole;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    /* NOTE */
    private EditText tNoteIn;
    private EditText tNoteName;


    /* NOTE */
    public EditText gettNoteIn() {return tNoteIn; }
    public EditText gettNoteName(){return tNoteName; }

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /* EDITOR */

        final TextView lNoteRow=findViewById(R.id.lNoteRow);
        tNoteIn=findViewById(R.id.tNoteIn);
        String text = "<i>This is <font color='red'>red</font>. This is <font color='blue'>blue</font>.</i>";
        tNoteIn.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        tNoteIn.addTextChangedListener(new TextWatcher() {

            private int oldLen=0;
            /*  Flag modifiche per evitare loop infinito
             *   vedi: https://developer.android.com/reference/android/text/TextWatcher#afterTextChanged(android.text.Editable)
             */
            private boolean modify=false;
            private int textCurPosition=0;
            private int apixCount=0;


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                textCurPosition=tNoteIn.getSelectionStart();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int lines = tNoteIn.getLineCount();
                String tlines = "";
                for( int x= 1; x<= lines; x++ ) { tlines=tlines + x + "\n"; }
                lNoteRow.setText(tlines);
            }

            @Override
            public void afterTextChanged(Editable s) {

                String text=tNoteIn.getText().toString();

                if (tNoteIn.getText().length() != oldLen && modify==false)
                {
                    modify=true;
                    //text=tNoteIn.getText().toString();
                    List<String> myArrayList = Arrays.asList(getResources().getStringArray(R.array.sh_reserved_words));

                    try {
                        if (text.length()>0) {

                            //Per le stringhe
                            apixCount=0;
                            int len=text.length()-1;
                            for (int y=0;y<len;y++){
                                if (text.charAt(y)=='\"') {
                                    String pre=text.substring(0,y);
                                    System.out.println(pre);
                                    String post=text.substring(y);
                                    System.out.println(text.substring(y));
                                    apixCount=apixCount+1;
                                    if (apixCount % 2 != 0)
                                        text=pre+"<font color='red'><u>"+post;
                                    else
                                        text=pre+"</font></u>"+post;
                                }
                            }

                            //Per le parole chiave
                            for (int x = 0; x <= myArrayList.size() - 1; x++) {
                                text = text.replace(myArrayList.get(x).toString(), "<strong>" + "<font color='green'>" + myArrayList.get(x).toString() + "</font>" + "</strong>");
                            }

                            //Per i commenti
                            text = text.replace("#", "<i><font color='grey'>#");

                            //Per il ritorno a capo
                            text = text.replace("\n", "\n</font></i><br>");
                        }

                        tNoteIn.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
                        oldLen = tNoteIn.getText().length();

                        //Rialloco Cursore
                        if (textCurPosition < tNoteIn.length()) {
                            if (tNoteIn.getText().charAt(textCurPosition) == '\n' && tNoteIn.getText().charAt(textCurPosition - 1) != '\n')
                                tNoteIn.setSelection(textCurPosition + 1);
                            else
                                tNoteIn.setSelection(textCurPosition);
                        }
                        else
                            tNoteIn.setSelection(textCurPosition);

                    }catch (Exception exc) {
                        tNoteIn.setSelection(textCurPosition);
                        System.out.println(exc);
                    }

                    modify=false;
                }

            }
        });



    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

