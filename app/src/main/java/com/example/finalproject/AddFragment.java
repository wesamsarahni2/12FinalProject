package com.example.finalproject;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
// CHECK GIT
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private EditText etProductName, CompanyName,Description;
    private Button  AddItem;
    private Spinner spinnerOptions;
    private FirebaseFirestore firestore=FireBaseServices.getInstance().getFire();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
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
    public void onStart() {
        super.onStart();
         spinnerOptions=getView().findViewById(R.id.SpinnerAvaliblity);
        AddItem=getView().findViewById(R.id.btnAdd);
        etProductName=getView().findViewById(R.id.etProductNameAdd);
        CompanyName=getView().findViewById(R.id.etCompanyNameAdd);
        Description=getView().findViewById(R.id.etDescriptionAdd);
          ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.Options, android.R.layout.simple_spinner_item);
          adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          spinnerOptions.setAdapter(adapter);
          spinnerOptions.setOnItemSelectedListener(this);
        AddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProduct();


            }


        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    public void AddProduct() {
        Map<String, Object> Product = new HashMap<>();
        Product.put(" Product name:", etProductName.getText().toString().trim());
        Product.put(" Company name:", CompanyName.getText().toString().trim());
        Product.put(" Additional Description about item:", Description.getText().toString().trim());

        firestore.collection("Products")
                .add(Product)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), "Item Succesfully added", Toast.LENGTH_SHORT).show();
                        FragmentTransaction ft =getActivity().getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.LayoutAdd, new HomeFragment());
                        ft.commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Adding item failed Try again", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}