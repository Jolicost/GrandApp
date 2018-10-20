package com.jauxim.grandapp.ActivityList;

import com.jauxim.grandapp.models.ActivityListItemModel;
import com.jauxim.grandapp.models.ActivityModel;

import java.util.List;

/**
 * Created by ennur on 6/25/16.
 */
public interface ActivityListView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getActivityListSuccess(List<ActivityListItemModel> activities);

}
