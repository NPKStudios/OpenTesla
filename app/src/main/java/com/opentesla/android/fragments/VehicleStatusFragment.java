package com.opentesla.android.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opentesla.tesla.requests.VehicleStateRequest;
import com.opentesla.android.R;
import com.opentesla.webtask.GetJsonAsyncTask;
import com.opentesla.webtask.OnTaskDoneListener;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VehicleStatusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VehicleStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VehicleStatusFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mOauthToken";
    private static final String ARG_PARAM2 = "mVehicleID";
    private static final String TITLE = "Vehicle Status";
    // TODO: Rename and change types of parameters
    private String mOauthToken;
    private long mVehicleID;
    private VehicleStateRequest vehicleStateRequest;
    private TextView mTvStatus;

    //private OnFragmentInteractionListener mListener;

    public VehicleStatusFragment() {
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
    public static VehicleStatusFragment newInstance(String oauthToken, long vehicleID) {
        VehicleStatusFragment fragment = new VehicleStatusFragment();
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
            vehicleStateRequest = new VehicleStateRequest(mVehicleID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_vehicle_status, container, false);
        getActivity().setTitle(TITLE);
        mTvStatus = (TextView) v.findViewById(R.id.textView_status);
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
        String urlString = vehicleStateRequest.getUrlString();
        GetJsonAsyncTask vehicleTask = new GetJsonAsyncTask(urlString, mOauthToken, new UpdateState());
        vehicleTask.execute();
    }

    public class UpdateState implements OnTaskDoneListener {
        @Override
        public void onTaskDone(JSONObject responseData) {
            if(vehicleStateRequest.processJsonResponse(responseData) == true)
            {
                mTvStatus.setText(vehicleStateRequest.getVehicleState().toString().replace(",","\n"));
            }
            else
            {
                mTvStatus.setText("Error parsing json");
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
