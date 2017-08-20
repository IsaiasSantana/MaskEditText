package isaias.santana.maskedittext;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = (EditText) findViewById(R.id.editText);
        final EditText editText2 = (EditText) findViewById(R.id.editText2);
        final EditText editText3 = (EditText) findViewById(R.id.editText3);

        editText.addTextChangedListener(MaskUtil.insert(MaskUtil.PHONE_MASK,editText));
        editText2.addTextChangedListener(MaskUtil.insert(MaskUtil.CPF_MASK,editText2));
        editText3.addTextChangedListener(MaskUtil.insert(MaskUtil.CEP_MASK,editText3));
    }
}
