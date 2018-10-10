package console.ms.com.msconsole;

import android.support.annotation.IntRange;
import android.text.Editable;
import android.text.Html;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class AscoltatoreEditor implements View.OnClickListener, TextWatcher {

    private int oldLen;
    /*  Flag modifiche per evitare loop infinito
     *   vedi: https://developer.android.com/reference/android/text/TextWatcher#afterTextChanged(android.text.Editable)
     */
    private boolean modify;
    private int textCurPosition;
    private int apixCount;
    private MainActivity activity;

    AscoltatoreEditor(MainActivity activity){
        this.activity = activity;
        apixCount = 0;
        textCurPosition = 0;
        modify = false;
        oldLen = 0;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int lines = activity.gettNoteIn().getLineCount();
        StringBuilder tlines = new StringBuilder();
        for(int x = 1; x <= lines; x++) {
            tlines.append(x).append("\n");
        }
        activity.getlNoteRow().setText(tlines.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

        String text = activity.gettNoteIn().getText().toString();

        if (activity.gettNoteIn().getText().length() != oldLen && !modify)
        {
            try {
                modify=true;
                List<String> myArrayList = Arrays.asList(activity.getResources().getStringArray(R.array.sh_reserved_words));
                if (activity.gettNoteIn().getSelectionStart() != 0)
                    textCurPosition = activity.gettNoteIn().getSelectionStart();
                if (text.length()>0) {
                    //Per le stringhe
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
                            else {
                                text = pre + "</u></font>" + post;
                            }
                        }
                    }
                    apixCount=0;

                    //Per le parole chiave
                    for (int x = 0; x <= myArrayList.size() - 1; x++) {
                        text = text.replace(myArrayList.get(x), "<strong>" + "<font color='green'>" + myArrayList.get(x) + "</font>" + "</strong>");
                    }

                    //Per i commenti
                    text = text.replace("#", "<i><font color='grey'>#");

                    //Per il ritorno a capo
                    text = text.replace("\n", "</font></i><br>");
                }
                SpannedString testo = new SpannedString(Html.fromHtml(text));
                Log.d("testo", testo.toString());
                for (int i = 0; i < testo.length(); i++){
                    Log.d("CHAR", Integer.toString(Character.getNumericValue(testo.charAt(i))));
                }
                Log.i("TESTO SIZE", Integer.toString(testo.toString().length()));
                if (testo.toString().length() != 0) {
                    activity.gettNoteIn().setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
                    Log.i("CHAR", Integer.toString(Character.getNumericValue(testo.charAt(textCurPosition - 1))));
                    activity.gettNoteIn().setSelection(textCurPosition);
                }else {
                    Log.w("LIMIT LENGTH", Integer.toString(oldLen));
                }
                oldLen = activity.gettNoteIn().getText().length();
            }catch (Exception exc) {
                exc.printStackTrace(System.out);
            }
            modify=false;
        }
    }
}
