package com.tts.starsky.apperceive.db.bao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.tts.starsky.apperceive.db.bean.UserInfoBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_INFO_BEAN".
*/
public class UserInfoBeanDao extends AbstractDao<UserInfoBean, Long> {

    public static final String TABLENAME = "USER_INFO_BEAN";

    /**
     * Properties of entity UserInfoBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property PhotoUser = new Property(1, String.class, "photoUser", false, "PHOTO_USER");
        public final static Property NickName = new Property(2, String.class, "nickName", false, "NICK_NAME");
        public final static Property Sex = new Property(3, int.class, "sex", false, "SEX");
        public final static Property Constellation = new Property(4, int.class, "constellation", false, "CONSTELLATION");
        public final static Property State = new Property(5, int.class, "state", false, "STATE");
    }


    public UserInfoBeanDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_INFO_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"PHOTO_USER\" TEXT," + // 1: photoUser
                "\"NICK_NAME\" TEXT," + // 2: nickName
                "\"SEX\" INTEGER NOT NULL ," + // 3: sex
                "\"CONSTELLATION\" INTEGER NOT NULL ," + // 4: constellation
                "\"STATE\" INTEGER NOT NULL );"); // 5: state
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_INFO_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserInfoBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String photoUser = entity.getPhotoUser();
        if (photoUser != null) {
            stmt.bindString(2, photoUser);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(3, nickName);
        }
        stmt.bindLong(4, entity.getSex());
        stmt.bindLong(5, entity.getConstellation());
        stmt.bindLong(6, entity.getState());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserInfoBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String photoUser = entity.getPhotoUser();
        if (photoUser != null) {
            stmt.bindString(2, photoUser);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(3, nickName);
        }
        stmt.bindLong(4, entity.getSex());
        stmt.bindLong(5, entity.getConstellation());
        stmt.bindLong(6, entity.getState());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserInfoBean readEntity(Cursor cursor, int offset) {
        UserInfoBean entity = new UserInfoBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // photoUser
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // nickName
            cursor.getInt(offset + 3), // sex
            cursor.getInt(offset + 4), // constellation
            cursor.getInt(offset + 5) // state
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserInfoBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPhotoUser(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNickName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSex(cursor.getInt(offset + 3));
        entity.setConstellation(cursor.getInt(offset + 4));
        entity.setState(cursor.getInt(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserInfoBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserInfoBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserInfoBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
