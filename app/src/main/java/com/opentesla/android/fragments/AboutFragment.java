package com.opentesla.android.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.opentesla.android.MySharedPreferences;
import com.opentesla.android.R;
import com.opentesla.tesla.TeslaApiClient;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AboutFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment {
    private static final String TITLE = "About";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TeslaApiClient mTeslaClient;
    private SharedPreferences mSharedPreferences;
    private EditText et_oauthToken;
    private EditText et_creationDate;
    private EditText et_expireDate;

    private OnFragmentInteractionListener mListener;



    public AboutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about, container, false);

        getActivity().setTitle(TITLE);

        et_oauthToken = (EditText) v.findViewById(R.id.editText_oauthKey);
        et_creationDate = (EditText) v.findViewById(R.id.editText_creationDate);
        et_expireDate = (EditText) v.findViewById(R.id.editText_expireDate);

        mSharedPreferences = MySharedPreferences.getSharedPreferences(getActivity().getApplicationContext());
        mTeslaClient = new TeslaApiClient(mSharedPreferences);

        //Date date = new Date(1406178443 * 1000L);
        //DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        //format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        //String formatted = format.format(date);


        et_oauthToken.setText(mTeslaClient.getOauthTokenString());
        et_creationDate.setText(mTeslaClient.getOauthTokenCreationTime().toString());
        et_expireDate.setText(mTeslaClient.getOauthTokenExpirationTime().toString());
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //if (context instanceof OnFragmentInteractionListener) {
        //    mListener = (OnFragmentInteractionListener) context;
        //} else {
        //    throw new RuntimeException(context.toString()
        //            + " must implement OnFragmentInteractionListener");
        //}
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
