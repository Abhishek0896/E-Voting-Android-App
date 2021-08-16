package com.example.e_voting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_voting.R;
import com.example.e_voting.modl.Candidate;
import com.example.e_voting.modl.CandidateResponse;
import com.squareup.picasso.Picasso;

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder> {

    CandidateResponse candidateResponse;
    Context context;


    public CandidateAdapter(CandidateResponse candidateResponse, Context context) {
        this.candidateResponse = candidateResponse;
        this.context = context;
    }

    @NonNull
    @Override
    public CandidateAdapter.CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.candidate_item, parent,false);
        return new CandidateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateAdapter.CandidateViewHolder holder, int position) {
        holder.tvCandidateName.setText(candidateResponse.data.get(position).getCandidateName());
        holder.tvPartyName.setText(candidateResponse.data.get(position).getPartyName());
        Picasso.get().load(candidateResponse.data.get(position).getUrl()).into(holder.imgPartyImage);
        holder.count.setText(Integer.toString(candidateResponse.data.get(position).getTotalCount()));
        boolean isExpandable = candidateResponse.data.get(position).isExpandable();
        holder.ExpandableLayout.setVisibility(isExpandable ? View.VISIBLE :View.GONE);
    }

    @Override
    public int getItemCount() {
        return candidateResponse.data.size();
    }

    public class CandidateViewHolder extends RecyclerView.ViewHolder {
        TextView tvCandidateName,tvPartyName,count;
        ImageView imgPartyImage,imgCounts;
        ConstraintLayout ExpandableLayout;
        public CandidateViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCandidateName = (TextView)itemView.findViewById(R.id.tvCandidateName);
            tvPartyName =(TextView)itemView.findViewById(R.id.tvPartyName);
            count = (TextView)itemView.findViewById(R.id.counts);
            imgPartyImage = (ImageView) itemView.findViewById(R.id.imgCandidate);
            imgCounts = (ImageView) itemView.findViewById(R.id.openCount);
            ExpandableLayout = (ConstraintLayout)itemView.findViewById(R.id.expandable_layout_ans);

            imgCounts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Candidate candidate = candidateResponse.data.get(getAdapterPosition());
                    candidate.setExpandable(!candidate.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
