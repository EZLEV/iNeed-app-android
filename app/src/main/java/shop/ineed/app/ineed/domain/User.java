package shop.ineed.app.ineed.domain;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shop.ineed.app.ineed.domain.util.LibraryClass;

/**
 * Created by antonio on 8/16/17.
 * <p>
 * Class domain user
 */

public class User {

    public static String PROVIDER = "shop.ineed.app.ineed.domain.User.USER";

    private String uid;
    private String name;
    private String email;
    @PropertyName("liked-products")
    private List<Product> likedProducts;
    @PropertyName("liked-stores")
    private List<Store> likedStores;
    private String image;
    private String phone;



    @Exclude
    private String password;
    @Exclude
    private String newPassword;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Product> getLikedProducts() {
        return likedProducts;
    }

    public void setLikedProducts(List<Product> likedProducts) {
        this.likedProducts = likedProducts;
    }

    public List<Store> getLikedStores() {
        return likedStores;
    }

    public void setLikedStores(List<Store> likedStores) {
        this.likedStores = likedStores;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    @Exclude
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    private void setEmailInMap(Map<String, Object> map) {
        if (getEmail() != null) {
            map.put("email", getEmail());
        }
    }

    private void setNameInMap(Map<String, Object> map) {
        if (getName() != null) {
            map.put("name", getName());
        }
    }

    private void setUidInMap(Map<String, Object> map){
        if (getUid() != null) {
            map.put("uid", getUid());
        }
    }

    public boolean isSocialNetworkLogged(Context context) {
        String token = getProviderUserLogged(context);
        return (token.contains("facebook") || token.contains("google"));
    }

    public void saveProviderUserLogged(Context context, String token) {
        LibraryClass.saveUserLogged(context, PROVIDER, token);
    }

    public String getProviderUserLogged(Context context) {
        return (LibraryClass.getUserLogged(context, PROVIDER));
    }

    public void saveUserLogged(DatabaseReference.CompletionListener... completionListeners) {
        DatabaseReference firebase = LibraryClass.getFirebase().child("consumers").child(getUid());

        if (completionListeners.length == 0) {
            firebase.setValue(this);
        } else {
            firebase.setValue(this, completionListeners[0]);
        }
    }

    public void updateUserLogged(DatabaseReference.CompletionListener... completionListeners) {
        DatabaseReference firebase = LibraryClass.getFirebase().child("consumers").child(getUid());

        Map<String, Object> map = new HashMap<>();
        setUidInMap(map);
        setEmailInMap(map);
        setNameInMap(map);


        if (map.isEmpty()) {
            return;
        }

        if (completionListeners.length > 0) {
            firebase.updateChildren(map, completionListeners[0]);
        } else {
            firebase.updateChildren(map);
        }
    }

    public void contectDataDB(Context context) {
        DatabaseReference firebase = LibraryClass.getFirebase().child("consumers").child(getUid());

        firebase.addListenerForSingleValueEvent((ValueEventListener) context);
    }
}
