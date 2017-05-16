package nwts.ru.autoshop.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.models.CategoryItem;
import nwts.ru.autoshop.models.authority.AccessCreateUser;
import nwts.ru.autoshop.models.authority.UserModel;
import nwts.ru.autoshop.network.ValidateToken;
import nwts.ru.autoshop.network.request.ShopAPI;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_CONTACTS;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private AutoCompleteTextView mUserNameView;
    private View mProgressView;
    private View mLoginFormView;
    private boolean isCreateUser = false; //suggested create user = true or not = false
    private PreferenceHelper mPreferenceHelper;
    private ValidateToken validateToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        PreferenceHelper.getInstance().init(getApplicationContext());
        mPreferenceHelper = PreferenceHelper.getInstance();

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    attemptCreateLogin();
                    return true;
                }
                return false;
            }
        });


        final Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
                attemptCreateLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        mUserNameView = (AutoCompleteTextView) findViewById(R.id.login_username);
        final Button mEmailRegisterButton = (Button) findViewById(R.id.email_register_button);
        mEmailRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isCreateUser = true;
                mEmailRegisterButton.setVisibility(View.INVISIBLE);
                mUserNameView.setVisibility(View.VISIBLE);
                mEmailSignInButton.setText(R.string.action_sign_up);
            }
        });
    }


    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        if (isCreateUser) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password, null);
            mAuthTask.execute((Void) null);
        }
    }

    private void attemptCreateLogin() {

        if (mAuthTask != null) {
            return;
        }

        if (!isCreateUser) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mUserNameView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String userName = mUserNameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        //test UserName
        if (TextUtils.isEmpty(userName)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password, userName);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mUserName;
        private boolean mResult;
        private String mToken;
        private boolean mWait;


        UserLoginTask(String email, String password, String userName) {
            mEmail = email;
            mPassword = password;
            mUserName = userName;
            mResult = false;
            mToken = null;
            mWait = false;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            if (!TextUtils.isEmpty(this.mUserName)) {
                ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
                final Call<AccessCreateUser> call = shopApi.createUser(this.mEmail, this.mPassword, this.mUserName);
                // call.enqueue(new Callback<List<ProductCategory>>() {
                Log.d(BaseConstant.TAG, call.toString());
                call.enqueue(new Callback<AccessCreateUser>() {
                    @Override
                    public void onResponse(Call<AccessCreateUser> call, Response<AccessCreateUser> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 201) {
                                //Toast.makeText(LoginActivity.this, "Success:" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                mResult = true;
                                if (ValidateToken.getInstance().getValidateToken()){
                                    TODOApplication.getInstance().setValidateToken(true);
                                }
                            } else {
                                if (response.body() != null) {
                                    //Toast.makeText(LoginActivity.this, "Error code:" + response.code() + " Message:" + response.body().toString(), Toast.LENGTH_SHORT).show();
                                    mPreferenceHelper.putString(BaseConstant.errorLogin,response.body().getMessage());
                                    Toast.makeText(LoginActivity.this, "Error code:" + response.code() + " Message:" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else if (response.errorBody() != null) {
                                    mPreferenceHelper.putString(BaseConstant.errorLogin,response.errorBody().toString());
                                    Toast.makeText(LoginActivity.this, "Error code:" + response.code() + " Message:" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                                } else if (response.message() != null) {
                                    mPreferenceHelper.putString(BaseConstant.errorLogin,response.message().toString());
                                    Toast.makeText(LoginActivity.this, "Error code:" + response.code() + " Message:" + response.message().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        mWait = true;
                    }


                    @Override
                    public void onFailure(Call<AccessCreateUser> call, Throwable throwable) {
                        Toast.makeText(LoginActivity.this, "Erroe:" + throwable.toString(), Toast.LENGTH_SHORT).show();
                        mPreferenceHelper.putString(BaseConstant.errorLogin,throwable.toString());
                        mWait = true;
                    }
                });

            } else

            {
                // if only login action
                ShopAPI shopApi = ShopAPI.retrofit.create(ShopAPI.class);
                final Call<UserModel> call = shopApi.getUser(this.mEmail, this.mPassword);
                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200 && response.body() != null && !response.body().getError()) {
                                mResult = true;
                                mToken = response.body().getApikey();
                                mPreferenceHelper.putAuthToken(mToken);
                                mPreferenceHelper.putUserId(response.body().getId());
                                mPreferenceHelper.putEmail(response.body().getUsername());
                                mPreferenceHelper.putUserName(response.body().getName());
                                TODOApplication.getInstance().setValidateToken(true);
                                // Toast.makeText(LoginActivity.this, "Success:" + response.body().getApikey(), Toast.LENGTH_SHORT).show();
                            } else {
                                if (response.body() != null) {
                                    mPreferenceHelper.putString(BaseConstant.errorLogin,response.body().getMessage());
                                    Toast.makeText(LoginActivity.this, "Error code:" + response.code() + " Message:" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else if (response.errorBody() != null) {
                                    mPreferenceHelper.putString(BaseConstant.errorLogin,response.errorBody().toString());
                                    Toast.makeText(LoginActivity.this, "Error code:" + response.code() + " Message:" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                                } else if (response.message() != null) {
                                    mPreferenceHelper.putString(BaseConstant.errorLogin,response.message().toString());
                                    Toast.makeText(LoginActivity.this, "Error code:" + response.code() + " Message:" + response.message().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        mWait = true;
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable throwable) {
                        Toast.makeText(LoginActivity.this, "Erroe:" + throwable.toString(), Toast.LENGTH_SHORT).show();
                        mPreferenceHelper.putString(BaseConstant.errorLogin,throwable.toString());
                        mWait = true;
                    }
                });
            }


            for (int i = 0; i < 18; i++) {
                try {
                    // Simulate network access.
                    Thread.sleep(1000);
                    if (mWait) {
                        break;
                    }
                } catch (InterruptedException e) {
                    mResult = false;
                    break;
                }
            }

            /*
            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }*/
            // TODO: register the new account here.
            if (mResult) {
               // PreferenceHelper.getInstance().putAuthToken(mToken);
            }
            return mResult;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {

                finish();
            } else {
                if (!TextUtils.isEmpty(mPreferenceHelper.getString(BaseConstant.errorLogin))) {
                    mPasswordView.setError(mPreferenceHelper.getString(BaseConstant.errorLogin));
                } else {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                }
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

