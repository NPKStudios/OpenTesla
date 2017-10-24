package com.opentesla.android.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.opentesla.tesla.requests.ChargeStateRequest;
import com.opentesla.android.R;
import com.opentesla.tesla.response.ChargeState;
import com.opentesla.webtask.GetJsonAsyncTask;
import com.opentesla.webtask.OnTaskDoneListener;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChargeStatusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChargeStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChargeStatusFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String TITLE = "Charge Status";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mOauthToken";
    private static final String ARG_PARAM2 = "mVehicleID";

    // TODO: Rename and change types of parameters
    private String mOauthToken;
    private long mVehicleID;
    private ChargeStateRequest mChargeState;

    private TextView mTvStatus;

    private TextView tv_battery_rated;
    private TextView tv_battery_percent;
    private TextView tv_battery_heater;
    private TextView tv_battery_current;

    private SeekBar sb_battery_percent;

    //private OnFragmentInteractionListener mListener;

    public ChargeStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param oauthToken Parameter 1.
     * @param vehicleID Parameter 2.
     * @return A new instance of fragment ChargeStatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChargeStatusFragment newInstance(String oauthToken, long vehicleID) {
        ChargeStatusFragment fragment = new ChargeStatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, oauthToken);
        args.putLong(ARG_PARAM2, vehicleID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOauthToken = getArguments().getString(ARG_PARAM1);
            mVehicleID = getArguments().getLong(ARG_PARAM2);
            mChargeState = new ChargeStateRequest(mVehicleID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_charge_status, container, false);

        getActivity().setTitle(TITLE);

        mTvStatus = (TextView) v.findViewById(R.id.textView_status);
        tv_battery_rated = (TextView) v.findViewById(R.id.tv_battery_rated);
        tv_battery_percent = (TextView) v.findViewById(R.id.tv_battery_percent);
        tv_battery_heater = (TextView) v.findViewById(R.id.tv_battery_heater);
        tv_battery_current = (TextView) v.findViewById(R.id.tv_battery_current);
        sb_battery_percent = (SeekBar) v.findViewById(R.id.sb_battery_percent);
        UpdateGui();
        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        //if (mListener != null) {
        //    mListener.onFragmentInteraction(uri);
        //}
    }

    public void UpdateGui()
    {
        String urlString = mChargeState.getUrlString();
        GetJsonAsyncTask vehicleTask = new GetJsonAsyncTask(urlString, mOauthToken, new UpdateChargeStateGui());
        vehicleTask.execute();
    }

    public class UpdateChargeStateGui implements OnTaskDoneListener {
        @Override
        public void onTaskDone(JSONObject responseData) {
            if(mChargeState.processJsonResponse(responseData) == true)
            {
                ChargeState state = mChargeState.getChargeState();
                mTvStatus.setText(mChargeState.getChargeState().toString());
                tv_battery_rated.setText(Double.toString(state.getBattery_range()));
                tv_battery_percent.setText(Double.toString(state.getBattery_level()) + '%');
                sb_battery_percent.setProgress((int)state.getBattery_level());
                tv_battery_current.setText(Double.toString(state.getBattery_current()) + " A");

                if(state.isBattery_heater_on())
                {
                    tv_battery_heater.setText("On");
                }
                else
                {
                    tv_battery_heater.setText("Off");
                }
            }
            else
            {
                mTvStatus.setText("Error parsing json for battery status: " + responseData.toString());
            }
        }

        @Override
        public void onError(String error) {

        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
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
