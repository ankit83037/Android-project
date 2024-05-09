package com.example.note_takingapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Note> arrayList;
    public CustomAdapter(Context context, ArrayList<Note> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.textView = convertView.findViewById(R.id.list_item_title_tv);
            viewHolder.threeDotButton = convertView.findViewById(R.id.three_dot);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(arrayList.get(position).getTitle());

        viewHolder.threeDotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("position " + (position+1));
                int count = deleteNote(arrayList.get(position).getId());
                if (count>0){
                    arrayList.remove(position);
                    notifyDataSetChanged();
                }

            }
        });
        return convertView;
    }

    private int deleteNote(String id) {
        int count = 0;
        count = context.getContentResolver().delete(MyContentProvider.CONTENT_URI,MyContentProvider.id_Col+"=?",new String[]{id});

        if (count>0){
            Toast.makeText(context,""+count+" note deleted",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"note not deleted",Toast.LENGTH_LONG).show();
        }
        return count;

    }

    private static class ViewHolder{
        TextView textView;
        ImageView threeDotButton;
    }
}

