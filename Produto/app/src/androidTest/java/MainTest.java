import android.support.design.widget.FloatingActionButton;
import android.test.ActivityInstrumentationTestCase;
import android.test.UiThreadTest;

import com.example.geoefficace.produto.Activity.MainActivity;
import com.example.geoefficace.produto.R;

/**
 * Created by geoefficace on 04/04/2019.
 */

public class MainTest extends ActivityInstrumentationTestCase<MainActivity> {

    private MainActivity mainActivity;
    private FloatingActionButton floatingActionButton;

    public MainTest(){
        super("teste", MainActivity.class);
    }

    @UiThreadTest
    public void testAddProduto() {
        mainActivity         = getActivity();
        floatingActionButton = mainActivity.findViewById(R.id.button_add);

        floatingActionButton.performClick();
    }

    @Override
    protected void tearDown() throws Exception {
        Thread.sleep(2000);
        super.tearDown();
    }

}
