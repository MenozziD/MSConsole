package console.ms.com.msconsole;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AscoltatoreEditor implements View.OnClickListener, TextWatcher {


    /*  Flag modifiche per evitare loop infinito
     *   vedi: https://developer.android.com/reference/android/text/TextWatcher#afterTextChanged(android.text.Editable)
     */
    private int oldLen;
    private boolean modify;
    private MainActivity activity;
    private boolean fCommento;
    private boolean fStringa;

    AscoltatoreEditor(MainActivity activity){
        this.activity = activity;
        oldLen = 0;
        modify = false;
        fCommento = false;
        fStringa = false;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            //activity.gettNoteIn().removeTextChangedListener(this);
            String text = activity.gettNoteIn().getText().toString();

            Log.i("IN", Integer.toString(text.length()));
            if (activity.gettNoteIn().getText().length() != oldLen && !modify)
            {
                modify = true;
                StringBuilder HTMLText= new StringBuilder();
                for (int i=0;i<text.length();i++) {

                    if (text.charAt(i)=='#' && !fCommento && !fStringa) {
                        fCommento=true;
                        HTMLText.append("<font color='grey'><i>");
                    }
                    if (text.charAt(i)=='\n' && fCommento) {
                        HTMLText.append("</i></font>");
                        fCommento=false;
                    }
                    if (text.charAt(i)=='\'' && !fCommento && !fStringa){
                        HTMLText.append("<font color='red'>");
                        fStringa=true;
                    } else {
                        if (text.charAt(i) == '\'' && !fCommento && fStringa) {
                            HTMLText.append("</font>");
                            fStringa = false;
                        }
                    }
                    
                    HTMLText.append(text.charAt(i));
                }

                /*
                List<String> myArrayList = Arrays.asList(activity.getResources().getStringArray(R.array.sh_reserved_words));
                if (activity.gettNoteIn().getSelectionStart() != 0)
                    textCurPosition = activity.gettNoteIn().getSelectionStart();
                if (text.length()>0) {
                    Log.i("CHAR", Integer.toString(Character.getNumericValue(text.charAt(textCurPosition - 1))));
                    if (apiceC && activity.gettNoteIn().getText().toString().charAt(textCurPosition - 1) != '\'' )
                    {
                        text = text + "'";
                        textCurPosition=textCurPosition+1;
                    }
                    apiceC=false;


                    if (text.charAt(textCurPosition - 1) == '\'') {
                        if (!apice) {
                            apice = true;
                            inizioStringa = textCurPosition - 1;
                        } else {
                            apice = false;
                            //stringhe.add(text.substring(inizioStringa, textCurPosition).replace("\'","&apos;"));
                            stringhe.add(text.substring(inizioStringa, textCurPosition));
                        }
                    }
                    if (text.charAt(textCurPosition - 1) == '#') {
                        commento = true;
                        inizioCommento = textCurPosition - 1;
                    }
                    if (text.charAt(textCurPosition - 1) == '\n' && commento){
                        commento = false;
                        commenti.add((text.substring(inizioCommento, textCurPosition)));
                    }
                    //text = text.replace("\'","&apos;");
                    for (int i = 0; i < stringhe.size(); i++){
                        text = text.replace(stringhe.get(i),"<font color='red'>" + stringhe.get(i) + "</font>");
                    }
                    //Per i commenti
                    for (int i = 0; i < commenti.size(); i++){
                        text = text.replace(commenti.get(i),"<font color='grey'><i>" + commenti.get(i) + "</i></font>");
                    }
                    //Parole chiave
                    for (int x = 0; x <= myArrayList.size() - 1; x++) {
                        text = text.replace(myArrayList.get(x), "<strong><font color='green'>" + myArrayList.get(x) + "</font></strong>");
                    }
                    //Per il ritorno a capo
                    text = text.replace("\n", "<br>");
                    Log.i("HTML", text);
                    activity.gettNoteIn().setText(Html.fromHtml(text));//setText(testo, TextView.BufferType.SPANNABLE);
                    Log.i("DOPO SET", Integer.toString(activity.gettNoteIn().getText().length()));
                    activity.gettNoteIn().setSelection(textCurPosition);
                    if (oldLineCount != activity.gettNoteIn().getLineCount()){
                        String textNumeri = activity.getlNoteRow().getText().toString();
                        oldLineCount = activity.gettNoteIn().getLineCount();
                        Log.i("CHAR CHANGE", Integer.toString(Character.getNumericValue(activity.gettNoteIn().getText().toString().charAt(textCurPosition - 1))));
                        if (activity.gettNoteIn().getText().toString().charAt(textCurPosition - 1) == '\n') {
                            indiceLine++;
                            textNumeri = textNumeri + Integer.toString(indiceLine) + "\n";
                        } else
                            textNumeri = textNumeri + "\n";
                        activity.getlNoteRow().setText(textNumeri);
                    }
                }
                oldLen = activity.gettNoteIn().getText().length();
                //
                if(activity.gettNoteIn().getText().toString().charAt(textCurPosition - 1) == '\'')
                    apiceC=true;
                */
                modify = false;
                //activity.gettNoteIn().addTextChangedListener(this);
            }
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
}

