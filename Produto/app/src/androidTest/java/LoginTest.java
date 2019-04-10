import android.test.ActivityInstrumentationTestCase;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;

import com.example.geoefficace.produto.Activity.LoginActivity;
import com.example.geoefficace.produto.Activity.MainActivity;
import com.example.geoefficace.produto.R;

/**
 * Created by geoefficace on 04/04/2019.
 */

public class LoginTest extends ActivityInstrumentationTestCase<LoginActivity> {


    private LoginActivity loginActivity;
    private Button button;
    private EditText email;
    private EditText password;

    public LoginTest(){
        super("teste", LoginActivity.class);
    }

    @UiThreadTest
    public void testLogin() throws InterruptedException {
        loginActivity = getActivity();
        button        = loginActivity.findViewById(R.id.button_confirmar);
        email         = loginActivity.findViewById(R.id.email);
        password      = loginActivity.findViewById(R.id.password);

        button.performClick();

        email.setText("admin@upf.br");
        password.setText("admin1239");

        button.performClick();
    }

    @Override
    protected void tearDown() throws Exception {
        Thread.sleep(2000);
        super.tearDown();
    }
}
