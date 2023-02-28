package com.example.chatgpt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter  extends  RecyclerView.Adapter<MessageAdapter.Messageviewholder>{

    List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public Messageviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatview= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_card,parent,false);
        return new Messageviewholder(chatview);
    }

    @Override
    public void onBindViewHolder(@NonNull Messageviewholder holder, int position) {
        Message message=messageList.get(position);
        if (message.getSendbyme().equals(Message.SENT_BY_ME))
        {
            holder.leftchatview.setVisibility(View.GONE);
            holder.rightchatview.setVisibility(View.VISIBLE);
            holder.rightchattextview.setText(message.getMessage());
        }
        else
        {
            holder.rightchatview.setVisibility(View.GONE);
            holder.leftchatview.setVisibility(View.VISIBLE);
            holder.leftchattextview.setText(message.getMessage());

        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class Messageviewholder extends RecyclerView.ViewHolder{
       LinearLayout leftchatview,rightchatview;
       TextView leftchattextview,rightchattextview;

        public Messageviewholder(@NonNull View itemView) {
            super(itemView);
            leftchatview=itemView.findViewById(R.id.left_chat_view);
            rightchatview=itemView.findViewById(R.id.right_chat_view);

            leftchattextview=itemView.findViewById(R.id.left_chat_text_view);
            rightchattextview=itemView.findViewById(R.id.right_chat_text_view);
        }
    }
}
