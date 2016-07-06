package com.chatt.demo.db;

public class DBInfo {
	
	public static class DB{

		public static final String DB_NAME="ChattDemo_FriendList.db";
		public static final int VERSION=1;
		
	}
	
	public static class Table{
		public static final String FRDLIST_TABLE ="friendsinfo";

		public static final String _ID="_id";
		public static final String FRD_NAME="frd_name";
		
		
		public static final String CREATE_FRDLIST_TABLE="create table if not exists "
		+FRDLIST_TABLE
		+"("
		+_ID
		+" integer primary key autoincrement,"
		+FRD_NAME
		+" text);";
		
		public static final String DROP_FRDLIST_TABLE="drop talbe "+FRDLIST_TABLE;
		
	}

}
