package com.testography.amgradle.data.managers;

import com.testography.amgradle.data.network.res.CommentRes;
import com.testography.amgradle.data.network.res.ProductRes;
import com.testography.amgradle.data.storage.dto.UserAddressDto;
import com.testography.amgradle.data.storage.realm.CommentRealm;
import com.testography.amgradle.data.storage.realm.ProductRealm;
import com.testography.amgradle.data.storage.realm.UserAddressRealm;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;

public class RealmManager {
    private Realm mRealmInstance;

    public void saveNewAddressToRealm(UserAddressDto userAddressDto) {
        if (userAddressDto.getId() == null) {
            userAddressDto.setId(UUID.randomUUID().toString());
        }

        Realm realm = Realm.getDefaultInstance();

        UserAddressRealm userAddressRealm = new UserAddressRealm(userAddressDto);

        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(userAddressRealm));
        realm.close();
    }

    public RealmResults<UserAddressRealm> getAllAddressesFromRealm() {

        RealmResults<UserAddressRealm> addresses = getQueryRealmInstance()
                .where(UserAddressRealm.class).findAll();

        return addresses;
    }

    public void saveNewCommentToRealm(String productId, CommentRes
            commentRes) {
        Realm realm = Realm.getDefaultInstance();
        ProductRealm product = realm.where(ProductRealm.class)
                .equalTo("id", productId)
                .findFirst();
        CommentRealm commentRealm = new CommentRealm(commentRes);
        realm.executeTransaction(realm1 -> product.addComment(commentRealm));
        realm.close();
    }

    public void saveProductResponseToRealm(ProductRes productRes) {
        Realm realm = Realm.getDefaultInstance();

        ProductRealm productRealm = new ProductRealm(productRes);
        if (!productRes.getComments().isEmpty()) {
            Observable.from(productRes.getComments())
                    .doOnNext(commentRes -> {
                        if (!commentRes.isActive()) {
                            deleteFromRealm(CommentRealm.class, commentRes.getId());
                        }
                    })
                    .filter(CommentRes::isActive)
                    .map(CommentRealm::new) // преобразовываем в RealmObject
                    .subscribe(commentRealm -> productRealm.getCommentsRealm()
                            .add(commentRealm)); // добавляем в ProductRealm

        }
        // добавляет или обновляем продукт в транзакции
        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(productRealm));
        realm.close();
    }

    public void deleteFromRealm(Class<? extends RealmObject> entityRealmClass,
                                String id) {
        Realm realm = Realm.getDefaultInstance();
        RealmObject entity = realm
                .where(entityRealmClass).equalTo("id", id).findFirst();
        if (entity != null) {
            realm.executeTransaction(realm1 -> entity.deleteFromRealm());
            realm.close();
        }
    }

    public Observable<ProductRealm> getAllProductsFromRealm() {
        RealmResults<ProductRealm> managedProduct = getQueryRealmInstance()
                .where(ProductRealm.class).findAllAsync();

        return managedProduct
                .asObservable() // получаем RealmResult как Observable
                .filter(RealmResults::isLoaded) // получаем только загруженные результаты (hot Observable)
                //.first() // convert a hot observable into a cold one
                .flatMap(Observable::from); // преобразуем в Observable<ProductRealm>
    }

    private Realm getQueryRealmInstance() {
        if (mRealmInstance == null || mRealmInstance.isClosed()) {
            mRealmInstance = Realm.getDefaultInstance();
        }
        return mRealmInstance;
    }
}
