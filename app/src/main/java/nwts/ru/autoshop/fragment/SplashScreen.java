package nwts.ru.autoshop.fragment;

import android.app.Fragment;
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

/**
 * Created by пользователь on 13.03.2017.
 */

public class SplashScreen extends Fragment {

    SplashTask splashTask;
    private ImageView mImageView, mImageView2, mImageView3, mImageView4;
    private Animation mFadeInAnimation, mFadeOutAnimation;


    public SplashScreen() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        mImageView = (ImageView) view.findViewById(R.id.imageViewSpash);
        mImageView2 = (ImageView) view.findViewById(R.id.imageViewSpash2);
        mImageView3 = (ImageView) view.findViewById(R.id.imageViewSpash3);
        mImageView4 = (ImageView) view.findViewById(R.id.imageViewSpash4);

        SplashTask splashTask = new SplashTask();
  //      mFadeInAnimation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in);
        mFadeInAnimation = AnimationUtils.loadAnimation(getActivity(),  R.anim.fadein);
//        mFadeOutAnimation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out);
        mFadeOutAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fadeout);
        mFadeInAnimation.setAnimationListener(animationFadeInListener);
        mFadeOutAnimation.setAnimationListener(animationFadeOutListener);
        splashTask.execute();
        // при запуске начинаем с анимации исчезновения
        mImageView.startAnimation(mFadeOutAnimation);
   //     mImageView2.startAnimation(mFadeInAnimation);
        // Inflate the layout for this fragment
        return view;
    }

    Animation.AnimationListener animationFadeOutListener = new Animation.AnimationListener() {

        @Override
        public void onAnimationEnd(Animation animation) {
            mImageView2.startAnimation(mFadeInAnimation);
            if (mImageView.getVisibility() ==View.VISIBLE){
                mImageView.setVisibility(View.GONE);
            } else
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub
        }
    };

    Animation.AnimationListener animationFadeInListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            mImageView.startAnimation(mFadeOutAnimation);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onAnimationStart(Animation animation) {
            // TODO Auto-generated method stub
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    class SplashTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                java.util.concurrent.TimeUnit.SECONDS.sleep(19);
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
