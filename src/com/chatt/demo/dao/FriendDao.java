package com.chatt.demo.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.chatt.demo.db.DBHelper;
import com.chatt.demo.db.DBInfo;
import com.chatt.demo.pojo.Friend;

public class FriendDao {
	
	
	private DBHelper dbHelper =null;
	private SQLiteDatabase db = null;
	private ContentValues values=null;
	
	/*
	 * friendlist表字段集合
	 */
	String[] columns={DBInfo.Table._ID,
			DBInfo.Table.FRD_NAME};
	
	public FriendDao(Context context){	
		dbHelper = new DBHelper(context);
	}
	
	/*
	 * 新增朋友
	 * @param friend
	 * @return
	 */
	public long insertFriend(Friend friend){
		//获得SQLiteDatebase进行数据库操作
		db= dbHelper.getWritableDatabase();
		
		//参数绑定对象
		values= new ContentValues();	
		values.put(DBInfo.Table.FRD_NAME,friend.getFriend_name());
		
		//进行插入操作，返回行号
		long rowId=db.insert(DBInfo.Table.FRDLIST_TABLE, DBInfo.Table.FRD_NAME, values);
		
		//关闭数据库，释放资源
		db.close();
		return rowId;
	}
	
	/*
	 * 更新朋友信息
	 * @param friend
	 * @return
	 */
	public int updateFriend(Friend friend){
		return 1;
	}
	
	/*
	 * 根据朋友id删除对应数据
	 * @param friend_id
	 * @return
	 */
	public int deleteFriend(String friend_name){
		db=dbHelper.getReadableDatabase();
		
		int count=db.delete(DBInfo.Table.FRDLIST_TABLE, DBInfo.Table.FRD_NAME+"='"+friend_name+"'", null);
		db.close();
		
		return count;
	}
	
	/*
	 *根据朋友id获得朋友信息
	 * @param friend_id
	 * @return
	 */
	public Friend findFriendByFrdID(String friend_name){
		
		db=dbHelper.getReadableDatabase();
		Friend friend=null;
		
		Cursor cursor = db.query(DBInfo.Table.FRDLIST_TABLE, columns, DBInfo.Table.FRD_NAME+"='"+friend_name+"'", null, null, null, null, null);
		
		while(cursor.moveToNext()){
			friend=new Friend();
			
			friend.setId(cursor.getLong(cursor.getColumnIndex(DBInfo.Table._ID)));
			friend.setFriend_name(cursor.getString(cursor.getColumnIndex(DBInfo.Table.FRD_NAME)));
		}
		
		cursor.close();
		db.close();
		return friend;
	}
	
	/*
	 * 获得所有的朋友数据
	 * @return
	 */
	public List<Friend> findAllFriends(){
		db=dbHelper.getReadableDatabase();
		List<Friend> friendList=null;
		Friend friend=null;
		
		Cursor cursor = db.query(DBInfo.Table.FRDLIST_TABLE, columns , null, null, null, null, null);
		
		if(cursor!=null&&cursor.getCount()>0){
			friendList=new ArrayList<Friend>(cursor.getCount());
			while(cursor.moveToNext()){
				friend=new Friend();
				
				friend.setId(cursor.getLong(cursor.getColumnIndex(DBInfo.Table._ID)));
				friend.setFriend_name(cursor.getString(cursor.getColumnIndex(DBInfo.Table.FRD_NAME)));
				
				friendList.add(friend);
			}
		}
		 
		cursor.close();
		db.close();
		
		return friendList;
	}
	
	public boolean deleteDatabase(Context context){
		return context.deleteDatabase(DBInfo.DB.DB_NAME);
	}
	
}
