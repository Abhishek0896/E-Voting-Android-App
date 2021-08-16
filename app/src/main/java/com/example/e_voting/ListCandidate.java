package com.example.e_voting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_voting.Util.MyWebApi;
import com.example.e_voting.adapter.CandidateAdapter;
import com.example.e_voting.modl.CandidateResponse;
import com.example.e_voting.modl.LoginResponse;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.e_voting.Util.Constants.AADHAR_ID;
import static com.example.e_voting.Util.Constants.EMAIL;
import static com.example.e_voting.Util.Constants.ISFORVOTE;
import static com.example.e_voting.Util.Constants.MOBILE;
import static com.example.e_voting.Util.Constants.PARTYID;
import static com.example.e_voting.Util.Constants.RESULT;
import static com.example.e_voting.Util.Constants.USERNAME;
import static com.example.e_voting.Util.Constants.USERSDATA;
import static com.example.e_voting.Util.Constants.VOTER_ID;

public class ListCandidate extends AppCompatActivity implements Serializable{

    TextView winnerPartyName,winnerCandidateName,tvMaxCount;
    RecyclerView recyclerView;
    CandidateAdapter adapter;
    Context mcontext;
    CandidateResponse candidateResponse ;
    LoginResponse loginResponse;
    String uname, uemail,umobile,uvoterID,uaadharID;
    boolean result = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_candidate);
        winnerCandidateName = findViewById(R.id.winnerCandidateName);
        winnerPartyName = findViewById(R.id.winnerPartyName);
        tvMaxCount = findViewById(R.id.tvMaxCount);
        recyclerView = findViewById(R.id.candidateRecylerview);
        mcontext = this;
        GetPartiesData();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            uname = extras.getString(USERNAME);
            umobile = extras.getString(MOBILE);
            uemail = extras.getString(EMAIL);
            uvoterID = extras.getString(VOTER_ID);
            uaadharID = extras.getString(AADHAR_ID);
            result = extras.getBoolean(RESULT);
        }

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(mcontext, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        if(!result) {
                            Intent intent = new Intent(mcontext, Register.class);
                            intent.putExtra(ISFORVOTE, true);
                            intent.putExtra(PARTYID, candidateResponse.data.get(position).getId());
                            intent.putExtra(USERNAME, uname);
                            intent.putExtra(VOTER_ID, uvoterID);
                            intent.putExtra(AADHAR_ID, uaadharID);
                            intent.putExtra(MOBILE, umobile);
                            intent.putExtra(EMAIL, uemail);
                            startActivity(intent);
                        }
                    }

                    @Override public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }


    private void initPartiesRecyler()
    {
        try {
            winnerCandidateName.setText(candidateResponse.data.get(0).getCandidateName());
            winnerPartyName.setText(candidateResponse.data.get(0).getPartyName());
            tvMaxCount.setText(Integer.toString(candidateResponse.data.get(0).getTotalCount()));


            adapter = new CandidateAdapter(candidateResponse, mcontext);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(true);

        }catch (Exception e)
        {
            Toast.makeText(mcontext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void GetPartiesData(){
        try {
            MyWebApi myWebApi = MyWebApi.retrofit.create(MyWebApi.class);
            Call<CandidateResponse> call = myWebApi.GetPartiesList();
            call.enqueue(new Callback<CandidateResponse>() {
                @Override
                public void onResponse(Call<CandidateResponse> call, Response<CandidateResponse> response) {
                    if(response.isSuccessful())
                    {
                        candidateResponse = response.body();
                    }else{
                        Toast.makeText(mcontext, "Tunnel not found...!!!", Toast.LENGTH_LONG).show();
                    }
                    initPartiesRecyler();
                }
                @Override
                public void onFailure(Call<CandidateResponse> call, Throwable t) {
                    Toast.makeText(mcontext, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception e)
        {
            Toast.makeText(mcontext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}