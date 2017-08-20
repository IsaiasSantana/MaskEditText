package isaias.santana.maskedittext;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

/**
 * @author Isaías Santana on 20/08/17.
 *         email: isds.santana@gmail.com
 */

public class MaskWatcher implements TextWatcher
{
    private boolean isRunning = false;
    private boolean isDeleting = false;
    private final String mask;

    public MaskWatcher(String mask) {
        this.mask = mask;
    }

    public static MaskWatcher buildCpf() {
        return new MaskWatcher("###.###.###-##");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        isDeleting = count > after;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (isRunning || isDeleting) {
            return;
        }
        isRunning = true;

        if (editable.length() < mask.length()) {
            if (mask.charAt(editable.length()) != '#') {
                editable.append(mask.charAt(editable.length()));
            }
        }

        isRunning = false;
        Log.d("TESTE",editable.toString());
    }
}
