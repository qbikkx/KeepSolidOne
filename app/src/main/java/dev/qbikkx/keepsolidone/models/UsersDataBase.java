package dev.qbikkx.keepsolidone.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Содержит самогенерируемый список пользователей в отсортированном порядке
 *
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class UsersDataBase {
    private static UsersDataBase instance;

    private List<User> mUsersList;

    public static UsersDataBase getInstance() {
        if (instance == null) {
            instance = new UsersDataBase();
        }
        return instance;
    }

    public List<User> getUsersList() {
        return mUsersList;
    }
    private UsersDataBase() {
        mUsersList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int num = i % 4;
            User.Category category;
            switch (num) {
                case 0:
                    category = User.Category.FRIEND;
                    break;
                case 1:
                    category = User.Category.FAMILY;
                    break;
                case 2:
                    category = User.Category.WORK;
                    break;
                default:
                    category = User.Category.OTHER;
                    break;
            }
            mUsersList.add(new User("userName"+i, "email"+i+"@gg.com", i%2 == 0, category));
        }
        Collections.sort(mUsersList, new User.UserNameComparator());
    }

    public User getUser(UUID id) {
        for (User user : mUsersList) {
           if (user.getId().equals(id)) {
               return user;
           }
        }
        return null;
    }
}