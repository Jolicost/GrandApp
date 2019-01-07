package com.jauxim.grandapp.ui.Activity.Chat;

import android.util.Log;
import android.widget.ListView;

import com.jauxim.grandapp.models.MessageModel;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;
import com.jauxim.grandapp.ui.Activity.BaseActivity;

import java.util.List;
import java.util.Random;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ChatPresenter extends BaseActivity {

    private final Service service;
    private CompositeSubscription subscriptions;

    private List<MessageModel> messageHistorial;
    private UserModel userInfo;
    private UserModel user;
    private MessageAdapter messageAdapter;
    private ListView messagesView;

    public ChatPresenter(Service service, UserModel user, ListView messagesView ,MessageAdapter messageAdapter) {
        this.service = service;
        this.subscriptions = new CompositeSubscription();
        this.user = user;
        this.messagesView = messagesView;
        this.messageAdapter = messageAdapter;
    }


    public void getHistorial(String activityId, final String auth) {

        Log.d("Log", " Inside Historial 1");

        Subscription subscription = service.getHistorial(activityId, new Service.MessageCallback() {
            @Override
            public void onSuccess(List<MessageModel> messageList) {
                messageHistorial.clear();
                messageHistorial.addAll(messageList);

                if(!messageHistorial.isEmpty()) {
                    Log.d("Log", " ERROR  1    Inside Historial 1");

                    for (int i = 0; i < messageHistorial.size(); i++) {

                        MessageModel elementMess = messageHistorial.get(i);

                        Log.d("Log", " Historial 3 " + elementMess.getData());

                        String messUserId[] = elementMess.getData().split(";");

                        getProfileInfo(messUserId[0], messUserId[1], auth);

                        Log.d("Log", " Historial 4");

                    }
                }
            }

            @Override
            public void onError(NetworkError networkError) {
                Log.d("Log", " ERROR  2    Inside Historial 1");
            }
        }, auth);

        subscriptions.add(subscription);
    }

    public void getProfileInfo(String id, final String userMessage, String auth) {
        Subscription subscription = service.getProfileInfo(id, new Service.ProfileInfoCallback() {
            @Override
            public void onSuccess(UserModel userModel) {
                userInfo = userModel;

                Log.d("Log", " Historial 5 " + userInfo.getCompleteName());

                final MemberData data = new MemberData(userInfo.getCompleteName(), getRandomColor());

                boolean belongsToCurrentUser = user.getId().equals(userInfo.getId());
                final Message message = new Message(userMessage, data, belongsToCurrentUser);

                Log.d("Log", " Historial 6 " + message.getText());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageAdapter.add(message);
                        messagesView.setSelection(messagesView.getCount() - 1);
                    }
                });
            }

            @Override
            public void onError(NetworkError networkError) {
                //todo
            }

        }, auth);

        subscriptions.add(subscription);
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
