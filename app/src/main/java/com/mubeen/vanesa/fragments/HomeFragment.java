package com.mubeen.vanesa.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mubeen.vanesa.Classes.Product;
import com.mubeen.vanesa.R;
import com.mubeen.vanesa.activites.ProductDetails;
import com.mubeen.vanesa.app.AppController;
import com.mubeen.vanesa.model.ProductsCustomList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.mubeen.vanesa.app.AppController.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    //custom instances
    ProgressDialog pDialog;
    ListView productsListView;
    public static ArrayList<Product> productsArrayList;
    private ProductsCustomList productArrayAdapter;
    private static final String URL_ALL_PRODCUTS = "http://production.technology-architects.com/vanesa/testapi.php";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        productsArrayList = new ArrayList<>();
        requestJson(URL_ALL_PRODCUTS);

        //addDummyProducts();
        productsListView = (ListView) view.findViewById(R.id.list);
        productArrayAdapter = new ProductsCustomList(getActivity(),getActivity(),productsArrayList);
        productsListView.setAdapter(productArrayAdapter);
        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pid = productsArrayList.get(position).getProductID();
                Intent i =new Intent(view.getContext(), ProductDetails.class);
                i.putExtra("pid",pid);
                startActivityForResult(i,123);
            }
        });
        return view;
    }



    private void requestJson(String Json_url) {
        productsArrayList.clear();
        showProgressDialog();

        JsonArrayRequest Arrayrequest = new JsonArrayRequest(Json_url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("returned array", response.toString());

                        try {
                        for(int i=0; i<response.length();i++) {

                            JSONObject product = (JSONObject) response.get(i);
                            int productID = Integer.parseInt(product.getString("id"));
                            String productTitle = product.getString("name");
                            double productPrice= Double.parseDouble(product.getString("price"));
                            String productImage = product.getString("image");
                            String productDescription = product.getString("description");
                            Product p = new Product(productID,productTitle,productPrice,productImage,productDescription);
                            productsArrayList.add(p);

                        }
                            } catch (JSONException e) {
                                e.printStackTrace();

                        }
                        productArrayAdapter.notifyDataSetChanged();
                        hidepDialog();
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
                hidepDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(Arrayrequest);


    }

    private void hidepDialog() {
    if(pDialog != null)
        pDialog.dismiss();
        pDialog = null;
    }


    private void showProgressDialog() {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading products");
        pDialog.show();
    }

    /*private void addDummyProducts() {
        productsArrayList.add(new Product(1,"keychain",3000.0,"http://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/sign-check-icon.png"));
        productsArrayList.add(new Product(2,"keychain",3000.0,"http://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/sign-check-icon.png"));
        productsArrayList.add(new Product(3,"keychain",3000.0,"http://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/sign-check-icon.png"));
        productsArrayList.add(new Product(4,"keychain",3000.0,"http://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/sign-check-icon.png"));
        productsArrayList.add(new Product(5,"keychain",3000.0,"http://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/sign-check-icon.png"));
    }*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
