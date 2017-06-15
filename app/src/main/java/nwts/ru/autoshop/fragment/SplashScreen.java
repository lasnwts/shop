package nwts.ru.autoshop.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import nwts.ru.autoshop.BaseActivity;
import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;

import static nwts.ru.autoshop.R.*;
import static nwts.ru.autoshop.R.anim.*;

/**
 * Created by пользователь on 13.03.2017.
 */

public class SplashScreen extends Fragment {

    SplashTask splashTask;
    private ImageView mImageView, mImageView2, mImageView3, mImageView4;
    private Animation mFadeInAnimation, mFadeOutAnimation;
    private Activity activity_context;

    public SplashScreen() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        activity_context = getActivity();

        View view = inflater.inflate(layout.fragment_splash, container, false);

        mImageView = (ImageView) view.findViewById(id.imageViewSpash);
        mImageView.setBackgroundResource(R.drawable.frames);
        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) mImageView.getBackground();

        SplashTask splashTask = new SplashTask();
        splashTask.execute();
        // Start the animation (looped playback by default).
        frameAnimation.start();
        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    class SplashTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                java.util.concurrent.TimeUnit.SECONDS.sleep(2);
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
