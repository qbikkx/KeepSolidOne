package dev.qbikkx.keepsolidone.models;

import java.util.Comparator;
import java.util.UUID;

import dev.qbikkx.keepsolidone.R;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public class User {

    private UUID mId;
    private String mUserName;
    private boolean mIsOnline;
    private String mUserAddress;
    private Category mCategory;

    public User() {
        mId = UUID.randomUUID();
    }

    public User(String userName, String email, boolean isOnline, Category category) {
        this();
        mUserName = userName;
        mUserAddress = email;
        mIsOnline = isOnline;
        mCategory = category;
    }

    public UUID getId() {
        return mId;
    }

    /**
     * Запрещаем сэтить id
     */
    private void setId(UUID mId) {
        this.mId = mId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public boolean isOnline() {
        return mIsOnline;
    }

    public void setIsOnline(boolean mIsOnline) {
        this.mIsOnline = mIsOnline;
    }

    public String getUserAddress() {
        return mUserAddress;
    }

    public void setUserAddress(String mUserAddress) {
        this.mUserAddress = mUserAddress;
    }

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category mCategory) {
        this.mCategory = mCategory;
    }

    /**
     * id - единственно уникальное поле - гарант равенства
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User user = (User) obj;
            return mId.equals(user.getId());
        }
        return false;
    }

    /**
     * Не придумал как обыграть хешкод основываясь на UUID
     * так что использовал его внутренний hashCode
     */
    @Override
    public int hashCode() {
        return mId.hashCode();
    }

    /**
     * Каждая категория, это пара ресурсов (String, Drawable)
     */
    public enum Category {
        FRIEND(R.string.category_friend, R.drawable.friend),
        FAMILY(R.string.category_family, R.drawable.family),
        WORK(R.string.category_work, R.drawable.work),
        OTHER(R.string.category_other, R.drawable.other);

        private final int mStringResourceId;
        private final int mDrawableResourceId;

        Category(int stringId, int drawableId) {
            mStringResourceId = stringId;
            mDrawableResourceId = drawableId;
        }

        public int getStringResourceId() {
            return mStringResourceId;
        }

        public int getDrawableResourceId() {
            return mDrawableResourceId;
        }

    }

    public static class UserNameComparator implements Comparator<User> {

        @Override
        public int compare(User userOne, User userTwo) {
            return userOne.getUserName().compareTo(userTwo.getUserName());
        }
    }
}
