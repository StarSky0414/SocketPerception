package com.tts.starsky.apperceive.db.bao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.tts.starsky.apperceive.db.bean.UserSelfBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_SELF_BEAN".
*/
public class UserSelfBeanDao extends AbstractDao<UserSelfBean, Long> {

    public static final String TABLENAME = "USER_SELF_BEAN";

    /**
     * Properties of entity UserSelfBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Nickname = new Property(1, String.class, "nickname", false, "NICKNAME");
        public final static Property HeadPhoto = new Property(2, int.class, "headPhoto", false, "HEAD_PHOTO");
    }


    public UserSelfBeanDao(DaoConfig config) {
        super(config);
    }
    
    public UserSelfBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_SELF_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NICKNAME\" TEXT," + // 1: nickname
                "\"HEAD_PHOTO\" INTEGER NOT NULL );"); // 2: headPhoto
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_SELF_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserSelfBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(2, nickname);
        }
        stmt.bindLong(3, entity.getHeadPhoto());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserSelfBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String nickname = entity.getNickname();
        if (nickname != null) {
            stmt.bindString(2, nickname);
        }
        stmt.bindLong(3, entity.getHeadPhoto());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public UserSelfBean readEntity(Cursor cursor, int offset) {
        UserSelfBean entity = new UserSelfBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // nickname
            cursor.getInt(offset + 2) // headPhoto
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserSelfBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNickname(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setHeadPhoto(cursor.getInt(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(UserSelfBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(UserSelfBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(UserSelfBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
