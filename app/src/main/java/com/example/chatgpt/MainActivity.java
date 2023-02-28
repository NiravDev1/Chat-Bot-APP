package com.example.chatgpt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView welcometext;
    EditText messageEditetext;
    ImageButton sendbtn;

    List<Message> messageList;
    MessageAdapter messageAdapter;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        welcometext = findViewById(R.id.welcome_text);
        messageEditetext = findViewById(R.id.message_edit_text);
        sendbtn = findViewById(R.id.send_btn);
        messageList = new ArrayList<>();

        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = messageEditetext.getText().toString();
                addTochat(question, Message.SENT_BY_ME);
                messageEditetext.setText(null);
                getResponce(question);
                welcometext.setVisibility(View.GONE);
            }
        });

    }

    void addTochat(String message, String sendbyme) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message, sendbyme));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });

    }

    void addResponse(String response) {
        messageList.remove(messageList.size() - 1);
        messageList.add(new Message(response,Message.SENT_BY_BOT));
        messageAdapter.notifyDataSetChanged();

    }

    public void getResponce(String responce) {
        messageList.add(new Message("Typing... ", Message.SENT_BY_BOT));
        String url = "http://api.brainshop.ai/get?bid=173152&key=kqt9LPzuyF0Su40q&uid=[uid]&msg=" + responce;
        String Baseurl = "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<MsgModel> call = retrofitAPI.getmessage(url);
        call.enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {

                if (response.isSuccessful()) {
                    MsgModel msgModel = response.body();
                    String re=msgModel.getCnt().toString();
//                    messageList.add(new Message(re,Message.SENT_BY_BOT));
                    addResponse(re);
                    messageAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {
                addResponse(t.getMessage());
                System.out.println(t.getMessage());
                Toast.makeText(MainActivity.this, "error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

//    void callAPI(String question) {
//        messageList.add(new Message("Typing... ", Message.SENT_BY_BOT));
//        JSONObject jsonBody = new JSONObject();
//        try {
//            jsonBody.put("model","text-davinci-003");
//            jsonBody.put("prompt",question);
//            jsonBody.put("max_tokens",4000);
//            jsonBody.put("temperature",0);
//            jsonBody.put("top_p",1);
//            jsonBody.put("n",1);
//            jsonBody.put("stream",false);
//            jsonBody.put("logprobs",null);
//            jsonBody.put("stop","\n");
//
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//        RequestBody requestBody = RequestBody.create(jsonBody.toString(), JSON);
//        Request request = new Request.Builder()
//                .url("https://api.openai.com/v1/completions")
//                .header("Content-Type","application/json")
//                .header("Authorization", "Bearer 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")
//                .post(requestBody)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                addResponse("Failed to load response due to "+e.getMessage());
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if(response.isSuccessful()) {
//                    JSONObject jsonObject = null;
//                    try {
//                        jsonObject = new JSONObject(response.body().string());
//                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
//                        String result = jsonArray.getJSONObject(0).getString("text");
//                        addResponse(result.trim());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                else
//                {
//                    addResponse("Failed to load response due to "+response.body().toString());
//                }
//            }
//        });
//
//    }

}