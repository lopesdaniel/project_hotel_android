package danieldesenvolvedor.com.br.projecthotel;

/**
 * Created by User on 06/06/2016.
 */
public class Config {

    //Address of our scripts of the CRUD
    public static final String URL_ADD = "http://www.danieldev.16mb.com/addQuarto.php";
    public static final String URL_GET_ALL = "http://www.danieldev.16mb.com/getAllQuartos.php";
    public static final String URL_GET_QUARTO = "http://www.danieldev.16mb.com/getQuarto.php?id=";
    public static final String URL_UPDATE_EMP = "http://www.danieldev.16mb.com/updateQuarto.php";
    public static final String URL_DELETE_EMP = "http://www.danieldev.16mb.com/deleteQuarto.php?id=";

    //Keys that will be used to send the request to php scripts
    public static final String KEY_QUARTO_ID = "id";
    public static final String KEY_QUARTO_VALOR = "valor";
    public static final String KEY_QUARTO_TIPO = "tipo";
    public static final String KEY_QUARTO_DISP = "disponivel";

    //JSON Tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_ID = "id";
    public static final String TAG_VALOR = "valor";
    public static final String TAG_TIPO = "tipo";
    public static final String TAG_DISP = "disponivel";

    //quarto id to pass with intent
    public static final String QUARTO_ID = "quarto_id";
}
