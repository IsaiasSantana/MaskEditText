package isaias.santana.maskedittext;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * @author Isaías Santana on 12/06/17.
 *         email: isds.santana@gmail.com
 *         Adaptado de https://andremrezende.wordpress.com/tag/android-mask-mascara-edittext-java-layout-cpf-cnpj/
 */

public final class MaskUtil
{
    public static final String PHONE_MASK = "(##) # ####-####";
    public static final String CPF_MASK = "###.###.###-##";
    public static final String CEP_MASK = "#####-###";

    private static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "").replace(" ","");
    }

    public static TextWatcher insert(final String mask, final EditText ediTxt)
    {
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(mask.length());
        ediTxt.setFilters(FilterArray);
        return new TextWatcher() {
            boolean isAtualizando;
            String textoSemMascaraAnterior = "";
            int posicaoCaracterApagado = 0;
            boolean isApagando = false;

            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

                Log.d(MaskUtil.class.getSimpleName(),"beforeTextChanged() "+"start: "+start+" after: "+after);
                if(after == 0)
                {
                    posicaoCaracterApagado = start;
                    isApagando = true;
                }
                else
                {
                    posicaoCaracterApagado = start;
                    isApagando = false;
                }

            }

            public void afterTextChanged(Editable s)
            {
                String textoSemMascara = MaskUtil.unmask(s.toString());
                String textoComMascara = "";

                if (isAtualizando)
                {
                    textoSemMascaraAnterior = textoSemMascara;
                    isAtualizando = false;
                    return;
                }


                //Faltam caracteres para preencher a mascara.
                if( textoSemMascara.length() > textoSemMascaraAnterior.length())
                {
                    int i = 0;
                    for (char m : mask.toCharArray())
                    {
                        if ((m != '#') && textoSemMascara.length() > textoSemMascaraAnterior.length())
                        {
                            textoComMascara += m;
                            continue;
                        }
                        try {
                            textoComMascara += textoSemMascara.charAt(i);
                        } catch (Exception e) {
                            break;
                        }
                        i++;
                    }
                }
                else {
                    //Algum caractere foi apagado, então mantém a forma da máscara anterior.
                    textoComMascara = s.toString();
                }

                isAtualizando = true;
                textoSemMascaraAnterior = textoSemMascara;

                ediTxt.removeTextChangedListener(this);
                ediTxt.setText(textoComMascara);
                if(isApagando)
                    ediTxt.setSelection(posicaoCaracterApagado);
                else
                    ediTxt.setSelection(textoComMascara.length());
                ediTxt.addTextChangedListener(this);

            } //[End-afterTextChanged()]
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

        }; //[End-TextWatcher()]
    }

}
