package com.jauxim.grandapp.ui.Activity.Chat;

import android.util.Log;
import android.widget.ListView;

import com.jauxim.grandapp.models.MessageModel;
import com.jauxim.grandapp.models.UserModel;
import com.jauxim.grandapp.networking.NetworkError;
import com.jauxim.grandapp.networking.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static java.util.Collections.reverse;

public class ChatPresenter {

    private final Service service;
    private CompositeSubscription subscriptions;

    private List<MessageModel> messageHistorial = new ArrayList<>();
    private UserModel userInfo;
    private UserModel user;
    private MessageAdapter messageAdapter;
    private ListView messagesView;
    private Chat chatView;

    public ChatPresenter(Chat chatView, Service service, UserModel user, ListView messagesView, MessageAdapter messageAdapter) {
        this.service = service;
        this.chatView = chatView;
        this.subscriptions = new CompositeSubscription();
        this.user = user;
        this.messagesView = messagesView;
        this.messageAdapter = messageAdapter;
    }


    public void getHistorial(String activityId, final String auth) {

        Log.d("chatInfo", " Inside Historial 1");

        Subscription subscription = service.getHistorial(activityId, new Service.MessageCallback() {
            @Override
            public void onSuccess(List<MessageModel> messageList) {
                messageHistorial.addAll(messageList);
                reverse(messageHistorial);
                chatView.hideProgress();

                if (messageHistorial != null) {
                    if (!messageHistorial.isEmpty()) {
                        Log.d("chatInfo", " ERROR  1    Inside Historial 1");

                        for (MessageModel messageModel : messageHistorial) {
                            Log.d("chatInfo", " Historial 3 " + messageModel.getData());
                            String messUserId[] = messageModel.getData().split(";");
                            getProfileInfo(messUserId[0], messUserId[1], auth);
                            Log.d("chatInfo", " Historial 4");
                        }
                    }
                }
            }

            @Override
            public void onError(NetworkError networkError) {
                Log.d("chatInfo", " ERROR  2    Inside Historial 1");
                Log.d("chatInfo", " ERROR  2  " + networkError);
            }
        }, auth);

        subscriptions.add(subscription);
    }

    public void getProfileInfo(String id, final String userMessage, String auth) {
        Log.d("chatInfo", " Historial 7 ");

        Subscription subscription = service.getProfileInfo(id, new Service.ProfileInfoCallback() {
            @Override
            public void onSuccess(UserModel userModel) {
                userInfo = userModel;

                Log.d("chatInfo", " Historial 5 " + userInfo.getCompleteName());

                final MemberData data = new MemberData(userInfo.getCompleteName(), getRandomColor(), userInfo.getProfilePic());

                boolean belongsToCurrentUser = user.getId().equals(userInfo.getId());
                final Message message = new Message(userMessage, data, belongsToCurrentUser);

                Log.d("chatInfo", " Historial 6 " + message.getText());

                chatView.runOnUiThread(new Runnable() {
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

                Log.d("chatInfo", " ERROR  3  " + networkError);
            }

        }, auth);

        subscriptions.add(subscription);
    }

    public void incrementActMessage(String activityId, String auth) {
        Subscription subscription = service.incrementActMessage(activityId, new Service.IncrementCallback() {
            @Override
            public void onSuccess() {

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
        while (sb.length() < 7) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }
}
