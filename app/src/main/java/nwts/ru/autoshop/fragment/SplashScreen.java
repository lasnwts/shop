package nwts.ru.autoshop.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nwts.ru.autoshop.BaseActivity;
import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;

/**
 * Created by пользователь on 13.03.2017.
 */

public class SplashScreen extends Fragment {

    SplashTask splashTask;


    public SplashScreen() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);

        SplashTask splashTask = new SplashTask();
        splashTask.execute();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    class SplashTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                java.util.concurrent.TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /**
             * Проверка что приложение не закрыто, и что в стэке есть что-либо
             */
            if (getActivity() != null) {
                if (getActivity().getFragmentManager().getBackStackEntryCount() != 0) {
                    getActivity().getFragmentManager().popBackStack();
                }
            }
            TODOApplication.getInstance().setFlag_run_Splash(false);
            return null;
        }
    }
}
