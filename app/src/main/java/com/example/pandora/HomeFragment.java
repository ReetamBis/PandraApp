package com.example.pandora;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.FileReader;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView prepaper;
    RecyclerView notes;
    RecyclerView resources;
    RecyclerView research;
    TextView allprev,allnotes,allresource,allresearch;
    HomePageRecycleAdapter prepaperAdap,notesAdap,resourcesAdap,researchAdap;

    ArrayList<String> prevsubject=new ArrayList<>();
    ArrayList<String> notessubject=new ArrayList<>();
    ArrayList<String> researchsubject=new ArrayList<>();
    ArrayList<String> resourcesubject=new ArrayList<>();

    FirebaseFirestore db= FirebaseFirestore.getInstance();
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        prepaper=view.findViewById(R.id.prepaper);
        notes=view.findViewById(R.id.notes);
        resources=view.findViewById(R.id.resources);
        research=view.findViewById(R.id.research);

        allnotes=view.findViewById(R.id.allnotes);
        allprev=view .findViewById(R.id.allprev);
        allresearch=view.findViewById(R.id.allresearch);
        allresource=view.findViewById(R.id.allresource);

        allprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten=new Intent(getContext(),com.example.pandora.AllOptions.class);
                inten.putExtra("Category","Previous Paper");
                startActivity(inten);
            }
        });
        allnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten=new Intent(getContext(),com.example.pandora.AllOptions.class);
                inten.putExtra("Category","Notes");
                startActivity(inten);
            }
        });
        allresource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten=new Intent(getContext(),com.example.pandora.AllOptions.class);
                inten.putExtra("Category","Resources");
                startActivity(inten);
            }
        });
        allresearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten=new Intent(getContext(),com.example.pandora.AllOptions.class);
                inten.putExtra("Category","Research Paper");
                startActivity(inten);
            }
        });

        prepaper.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        notes.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        research.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));
        resources.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false));


        prepaperAdap=new HomePageRecycleAdapter(this.getActivity(), prevsubject, new HomePageRecycleAdapter.clicklistener() {
            @Override
            public void onclick(int pos) {
                Intent intent=new Intent(getContext(),com.example.pandora.AvailableSubjectResource.class);
                intent.putExtra("Category","Previous Paper");
                intent.putExtra("Subject",prevsubject.get(pos));
                startActivity(intent);
            }
        });


        notesAdap=new HomePageRecycleAdapter(this.getActivity(),notessubject,new HomePageRecycleAdapter.clicklistener() {
            @Override
            public void onclick(int pos) {
                Intent intent=new Intent(getContext(),com.example.pandora.AvailableSubjectResource.class);
                intent.putExtra("Category","Notes");
                intent.putExtra("Subject",notessubject.get(pos));
                startActivity(intent);
            }
        });

        researchAdap=new HomePageRecycleAdapter(this.getActivity(),researchsubject,new HomePageRecycleAdapter.clicklistener() {
            @Override
            public void onclick(int pos) {
                Intent intent=new Intent(getContext(),com.example.pandora.AvailableSubjectResource.class);
                intent.putExtra("Category","Research Paper");
                intent.putExtra("Subject",researchsubject.get(pos));
                startActivity(intent);
            }
        });

        resourcesAdap=new HomePageRecycleAdapter(this.getActivity(),resourcesubject,new HomePageRecycleAdapter.clicklistener() {
            @Override
            public void onclick(int pos) {
                Intent intent=new Intent(getContext(),com.example.pandora.AvailableSubjectResource.class);
                intent.putExtra("Category","Resources");
                intent.putExtra("Subject",resourcesubject.get(pos));
                startActivity(intent);
            }
        });

        prepaper.setAdapter(prepaperAdap);
        notes.setAdapter(prepaperAdap);
        resources.setAdapter(prepaperAdap);
        research.setAdapter(prepaperAdap);

        createprevsubList();
        return view;
    }
    public  void createprevsubList()
    {
        Log.i("Mylog:","Callin query");
        db.collection("PrevPaper").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.i("Mylog:","Success");
                if(queryDocumentSnapshots==null)
                {
                    Log.i("Mylog:","Nothing Found");
                }
                else {
                    Log.i("Mylog:","Is Empty:"+ (queryDocumentSnapshots.isEmpty()));
                    Log.i("Mylog:",queryDocumentSnapshots.toString());
                    for (QueryDocumentSnapshot d : queryDocumentSnapshots) {
                        Log.i("Mylog:", d.getMetadata().getClass().getName());

                        prevsubject.add(d.getId());
                    }
                    prepaperAdap.notifyDataSetChanged();
                }
            }
        });


    }
    public void createnotessublist()
    {

        db.collection("Notes").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot d:queryDocumentSnapshots)
                {
                    notessubject.add(d.getId());
                }
            }
        });
        notesAdap.notifyDataSetChanged();
    }
    public void createresourcesublist()
    {
        resourcesAdap.notifyDataSetChanged();
    }
    public void createresearchsublist()
    {
        researchAdap.notifyDataSetChanged();
    }
}