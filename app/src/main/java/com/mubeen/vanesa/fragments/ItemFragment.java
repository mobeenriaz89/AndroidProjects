package com.mubeen.vanesa.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.volley.AuthFailureError;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;


import com.android.volley.toolbox.StringRequest;

import com.mubeen.vanesa.helper.ListsHelper;
import com.mubeen.vanesa.Classes.Product;
import com.mubeen.vanesa.R;

import com.mubeen.vanesa.app.AppConfig;
import com.mubeen.vanesa.app.AppController;
import com.mubeen.vanesa.helper.SessionManager;
import com.mubeen.vanesa.model.MyItemRecyclerViewAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment {

    ProgressDialog pDialog;
    RecyclerView recyclerView;
    RecyclerView.Adapter productsAdapter;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    SessionManager session;
    private int mColumnCount;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        session = new SessionManager(getActivity());
        mColumnCount = session.getColumnCount();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading products");

        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                int i = bundle.getInt(AppConfig.KEY_CATEGORY_ID, AppConfig.CATEGORY_ID_ROOT_CATALOG);
                MakePostRequest(String.valueOf(i));
            }

            productsAdapter = new MyItemRecyclerViewAdapter(ListsHelper.productArrayList, mListener,getActivity());

            recyclerView.setAdapter(productsAdapter);
        }
        return view;
    }

    void MakePostRequest(final String categoryID) {
        showpDialog();
        ListsHelper.productArrayList.clear();

        StringRequest request = new StringRequest(Request.Method.POST, AppConfig.URL_All_Products, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("params response" , response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i =0; i<jsonArray.length();i++){
                        try {
                            JSONObject productOBJ = (JSONObject) jsonArray.get(i);
                            String name = productOBJ.getString("name");
                            int id = Integer.parseInt(productOBJ.getString("id"));
                            String price = productOBJ.getString("price");
                            String imgurl = productOBJ.getString("image");
                            String desc = productOBJ.getString("description");

                            Product p = new Product(id,name,price,imgurl,desc);
                            ListsHelper.productArrayList.add(p);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    productsAdapter.notifyDataSetChanged();
                    hidepDialog();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("catid",categoryID);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(request);
    }


    private void hidepDialog() {
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    private void showpDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Product item);
    }



}
