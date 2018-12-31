package com.jauxim.grandapp.ui.Activity.Chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jauxim.grandapp.Constants;
import com.jauxim.grandapp.R;
import com.jauxim.grandapp.Utils.DataUtils;
import com.jauxim.grandapp.models.UserModel;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Member;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;

import java.util.Random;

public class Chat extends AppCompatActivity implements RoomListener {

    // replace this with a real channelID from Scaledrone dashboard
    private String channelID = "p0gOL0KDsTT7G0Pd";
    private String roomName;

    private Scaledrone scaledrone;
    private MessageAdapter messageAdapter;

    private EditText editText;
    private ListView messagesView;

    private UserModel user;
    private String activityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                activityId = null;
            } else {
                activityId = extras.getString(Constants.ACTIVITY_ID);
            }
        } else {
            activityId = (String) savedInstanceState.getSerializable(Constants.ACTIVITY_ID);
        }

        Log.d("Log", " Room Id = " + activityId);

        roomName = "observable-" + activityId;

        editText = (EditText) findViewById(R.id.editText);

        messageAdapter = new MessageAdapter(this);
        messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);

        user = DataUtils.getUserInfo(this);

        Log.d("Log", " User Id = " + user.getId());
        Log.d("Log", " User Name = " + user.getCompleteName());

        MemberData data = new MemberData(user.getCompleteName(), getRandomColor());

        Log.d("Log", " Data Name = " + data.toString());

        scaledrone = new Scaledrone(channelID, data);
        scaledrone.connect(new Listener() {
            @Override
            public void onOpen() {
                Log.d("Log","Scaledrone connection open");
                scaledrone.subscribe(roomName, Chat.this);
            }

            @Override
            public void onOpenFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onClosed(String reason) {
                System.err.println(reason);
            }
        });
    }

    public void sendMessage(View view) {
        String message = editText.getText().toString();
        if (message.length() > 0) {

            Log.d("Log", " Room message Name = " + roomName);

            scaledrone.publish(roomName, message);
            editText.getText().clear();
        }
    }

    @Override
    public void onOpen(Room room) {
        Log.d("Log", " Conneted to room");
        System.out.println("Conneted to room");
    }

    @Override
    public void onOpenFailure(Room room, Exception ex) {
        Log.d("Log", " Conneted to room FAILURE  " + ex.toString());
        System.err.println(ex);
    }

    @Override
    public void onMessage(Room room, final JsonNode json, final Member member) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            Log.d("Log", " Member Data " + member.getClientData());
            Log.d("Log", " Message " + json.asText());

            final MemberData data = mapper.treeToValue(member.getClientData(), MemberData.class);
            Log.d("Log", " Member Data " + data.toString());
            boolean belongsToCurrentUser = member.getId().equals(scaledrone.getClientID());
            final Message message = new Message(json.asText(), data, belongsToCurrentUser);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    messageAdapter.add(message);
                    messagesView.setSelection(messagesView.getCount() - 1);
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String getRandomName() {
        String[] adjs = {"autumn", "hidden", "bitter", "misty", "silent", "empty", "dry", "dark", "summer", "icy", "delicate", "quiet", "white", "cool", "spring", "winter", "patient", "twilight", "dawn", "crimson", "wispy", "weathered", "blue", "billowing", "broken", "cold", "damp", "falling", "frosty", "green", "long", "late", "lingering", "bold", "little", "morning", "muddy", "old", "red", "rough", "still", "small", "sparkling", "throbbing", "shy", "wandering", "withered", "wild", "black", "young", "holy", "solitary", "fragrant", "aged", "snowy", "proud", "floral", "restless", "divine", "polished", "ancient", "purple", "lively", "nameless"};
        String[] nouns = {"waterfall", "river", "breeze", "moon", "rain", "wind", "sea", "morning", "snow", "lake", "sunset", "pine", "shadow", "leaf", "dawn", "glitter", "forest", "hill", "cloud", "meadow", "sun", "glade", "bird", "brook", "butterfly", "bush", "dew", "dust", "field", "fire", "flower", "firefly", "feather", "grass", "haze", "mountain", "night", "pond", "darkness", "snowflake", "silence", "sound", "sky", "shape", "surf", "thunder", "violet", "water", "wildflower", "wave", "water", "resonance", "sun", "wood", "dream", "cherry", "tree", "fog", "frost", "voice", "paper", "frog", "smoke", "star"};
        return (
            adjs[(int) Math.floor(Math.random() * adjs.length)] +
            "_" +
            nouns[(int) Math.floor(Math.random() * nouns.length)]
        );
    }

    private String getRandomColor() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer("#");
        while(sb.length() < 7){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }
}