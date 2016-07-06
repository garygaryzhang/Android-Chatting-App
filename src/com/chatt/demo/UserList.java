package com.chatt.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chatt.demo.custom.CustomActivity;
import com.chatt.demo.dao.FriendDao;
import com.chatt.demo.pojo.Friend;
import com.chatt.demo.utils.Const;
import com.chatt.demo.utils.Utils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * The Class UserList is the Activity class. It shows a list of all users of
 * this app. It also shows the Offline/Online status of users.
 */
public class UserList extends CustomActivity
{

	/** The Chat list. */
	private ArrayList<ParseUser> uList;
	private ArrayList<ParseUser> AllUserList;

	/** The user. */
	public static ParseUser user;
	
	
	private Spinner spinner;
    private Spinner spinner_dltFd;
	
	private ListView list;
	
	private String[] friendsNameList={};
	private String[] fNList={};
	private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter_dltFd;
	
	private UserAdapter userAdapter;
	
	
	/** The Chat list. */
	private ArrayList<ParseUser> uTempList;
	
	private View dialogView=null;
    private View DltDialogView=null;

	private int Selected=0;
	/** The user. */
	
	public  FriendDao dao;///锟铰硷拷///
	
	
	
	//锟铰加的凤拷锟斤拷
	
	private static String[] insert(String[] arr, String str)
    {
        int size = arr.length;
        
        String[] tmp = new String[size + 1];
        
        System.arraycopy(arr, 0, tmp, 0, size);
        
        tmp[size] = str;
        
        return tmp;
    }
		@Override  
		public boolean onCreateOptionsMenu(Menu menu) {  
		    MenuInflater inflater = getMenuInflater();  
		    inflater.inflate(R.layout.menu, menu);  
		    return super.onCreateOptionsMenu(menu);  
		}  
		
		@Override  
		public boolean onOptionsItemSelected(MenuItem item) {  
		    switch (item.getItemId()) {  
		    case R.id.add_friend:
		         
		    	
		    	onClickAddFD();
		        return true;
                case R.id.delete_friend:
                    onClickRemoveFD();
                   return true;
		    
		    default:  
		        return super.onOptionsItemSelected(item);  
		    }  
		}

        public void onClickRemoveFD(){
            final List<Friend> fNames=dao.findAllFriends();
           
            String[] fNListTemp={};
            if(fNames!=null){
	            for(int i=0; i<fNames.size();i++){
	            	fNListTemp = insert(fNListTemp, fNames.get(i).getFriend_name());
	            }
            }
            fNList=null;
            fNList=fNListTemp;
            
            if(fNList.length==0||fNList==null){
            	String [] temp={" "};
            	fNList=temp;
            }

            DltDialogView = View.inflate(this, R.layout.removefriend_dialog, null);
            
            
            adapter_dltFd = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,fNList );
            adapter_dltFd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            

           
            	spinner_dltFd = (Spinner) DltDialogView.findViewById(R.id.Spinner2);
            	
	            spinner_dltFd.setAdapter(adapter_dltFd);
	            spinner_dltFd.setOnItemSelectedListener(new SpinnerSelectedListener());
	      
	            spinner_dltFd.setVisibility(View.VISIBLE);
	            
            

            final Dialog dialog_dltFd =new Dialog(this);
            dialog_dltFd.setContentView(DltDialogView);
            dialog_dltFd.show();

            Button add=(Button)DltDialogView.findViewById(R.id.btnRemove);


            add.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    String name=fNList[Selected];
 
                    if(fNames!=null)
                    {
                        dao.deleteFriend(name);


                        uTempList=new ArrayList<ParseUser>();

                        for(int i=0;i<uList.size();i++){
                            
                                    if(!uList.get(i).getUsername().equals(name)){

                                        uTempList.add(uList.get(i));
                                        System.out.println("here");
                                    }
                        }
                        if(uList!=null){
                            uList.clear();
                            uList=uTempList;
                        }else{
                            uList=new ArrayList<ParseUser>();
                        }

                        userAdapter.notifyDataSetChanged();
                    }
                    //锟斤拷锟斤拷锟叫憋拷

                    dialog_dltFd.dismiss();



                }

            });

            Button cancel=(Button)DltDialogView.findViewById(R.id.btnCancel);
            cancel.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog_dltFd.dismiss();

                }

            });


        }

		
		public void onClickAddFD(){
			
			 
			
			//锟斤拷锟斤拷锟斤拷碌锟斤拷没锟斤拷锟斤拷锟� 锟斤拷锟皆得碉拷锟斤拷锟斤拷锟斤拷锟斤拷
			ParseUser.getQuery().whereNotEqualTo("username", user.getUsername())
			.findInBackground(new FindCallback<ParseUser>() {

				String[] fNListTemp={};
				@Override
				public void done(List<ParseUser> li, ParseException e)
				{
					
					if (li != null)
					{
						if (li.size() == 0)
							Toast.makeText(UserList.this,
									R.string.msg_no_user_found,
									Toast.LENGTH_SHORT).show();

						AllUserList = new ArrayList<ParseUser>(li);
							///////
							for(int i=0;i<AllUserList.size();i++){
								
								fNListTemp = insert(fNListTemp, AllUserList.get(i).getUsername());
										
							}
							friendsNameList=null;
							friendsNameList=fNListTemp;
					}
				}
			});
		
			
			
			
			
			dialogView = View.inflate(this, R.layout.addfriend_dialog, null);
			adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, friendsNameList);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
	        
	        
	        
			
			spinner = (Spinner) dialogView.findViewById(R.id.Spinner1);
			spinner.setAdapter(adapter);  
			spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
			
			  
	        //锟斤拷锟斤拷默锟斤拷值  
	        spinner.setVisibility(View.VISIBLE);
			
			final Dialog dialog =new Dialog(this);
			dialog.setContentView(dialogView);
			dialog.show();
			
			Button add=(Button)dialogView.findViewById(R.id.btnAdd);
			
			
			add.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					String name=friendsNameList[Selected];
					boolean key=true;
					
					List<Friend> temp=dao.findAllFriends();
					if(temp!=null){
						for(int i=0;i<temp.size();i++){
							if(name.equals(temp.get(i).getFriend_name())){
								key=false;
							}
						}
					}
					if(key)
					{
						dao.insertFriend(new Friend(name));
					

					//System.out.println(dao.findFriendByFrdID(friendsNameList[Selected]).getFriend_name());
						
					
					uTempList=new ArrayList<ParseUser>();
					
						List<Friend> fli=dao.findAllFriends();
						//System.out.println("fli_size="+fli.size());
						
						for(int i=0;i<AllUserList.size();i++){
							if(fli!=null){
							for(int j=0;j<fli.size();j++){
								//System.out.println("fli_name="+fli.get(j).getFriend_name()+" uname=");
								if(AllUserList.get(i).getUsername().equals(fli.get(j).getFriend_name())){
									
									uTempList.add(AllUserList.get(i));
									//System.out.println("here");
								}
							}
							}
						}
						if(fli!=null){
							uList.clear();
							uList=uTempList;
						}else{
							uList=new ArrayList<ParseUser>();
						}
						
						userAdapter.notifyDataSetChanged();
					}
			          //锟斤拷锟斤拷锟叫憋拷
						
						dialog.dismiss();
						
						
					
				}
				
			});
			
			Button cancel=(Button)dialogView.findViewById(R.id.btnCancel);
			cancel.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
						dialog.dismiss();
					
				}
				
			});
			
		}
		
		
		class SpinnerSelectedListener implements OnItemSelectedListener{  
			  
	        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,  
	                long arg3) { 
	        	Selected=arg2;
	          
	        }  
	  
	        public void onNothingSelected(AdapterView<?> arg0) {  
	        }  
	    }  

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		///锟铰硷拷///
				dao=new FriendDao(this);
				
				//dao.deleteDatabase(this);
		setContentView(R.layout.user_list);

		getActionBar().setDisplayHomeAsUpEnabled(false);

		updateUserStatus(true);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		updateUserStatus(false);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume()
	{
		super.onResume();
		loadUserList();

	}

	/**
	 * Update user status.
	 * 
	 * @param online
	 *            true if user is online
	 */
	private void updateUserStatus(boolean online)
	{
		user.put("online", online);
		user.saveEventually();
	}

	/**
	 * Load list of users.
	 */
	private void loadUserList()
	{
		final ProgressDialog dia = ProgressDialog.show(this, null,
				getString(R.string.alert_loading));
		ParseUser.getQuery().whereNotEqualTo("username", user.getUsername())
				.findInBackground(new FindCallback<ParseUser>() {

					@Override
					public void done(List<ParseUser> li, ParseException e)
					{
						dia.dismiss();
						if (li != null)
						{
							if (li.size() == 0)
								Toast.makeText(UserList.this,
										R.string.msg_no_user_found,
										Toast.LENGTH_SHORT).show();

							AllUserList=uList = new ArrayList<ParseUser>(li);
							
							uTempList=new ArrayList<ParseUser>();
							
								List<Friend> fli=dao.findAllFriends();
								//System.out.println("fli_size="+fli.size());
								
								for(int i=0;i<uList.size();i++){
									friendsNameList = insert(friendsNameList, uList.get(i).getUsername());
									//System.out.println(uList.size()+" "+uList.get(i).getUsername());
									
									if(fli!=null){
									for(int j=0;j<fli.size();j++){
										//System.out.println("fli_name="+fli.get(j).getFriend_name()+" uname=");
										if(uList.get(i).getUsername().equals(fli.get(j).getFriend_name())){
											
											uTempList.add(uList.get(i));
											//System.out.println("here");
										}
									}
									}
								}
								if(fli!=null){
									uList.clear();
									uList=uTempList;
								}else{
									uList=new ArrayList<ParseUser>();
								}
								
							
							
							list = (ListView) findViewById(R.id.list);
							userAdapter=new UserAdapter();
							list.setAdapter(userAdapter);
							list.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int pos, long arg3)
								{
									startActivity(new Intent(UserList.this,
											Chat.class).putExtra(
											Const.EXTRA_DATA, uList.get(pos)
													.getUsername()));
								}
							});
							
						}
						else
						{
							Utils.showDialog(
									UserList.this,
									getString(R.string.err_users) + " "
											+ e.getMessage());
							e.printStackTrace();
						}
					}
				});
	}

	/**
	 * The Class UserAdapter is the adapter class for User ListView. This
	 * adapter shows the user name and it's only online status for each item.
	 */
	private class UserAdapter extends BaseAdapter
	{

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount()
		{
			return uList.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public ParseUser getItem(int arg0)
		{
			return uList.get(arg0);
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int arg0)
		{
			return arg0;
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int pos, View v, ViewGroup arg2)
		{
			if (v == null)
				v = getLayoutInflater().inflate(R.layout.chat_item, null);

			ParseUser c = getItem(pos);
			TextView lbl = (TextView) v;
			lbl.setText(c.getUsername());
			lbl.setCompoundDrawablesWithIntrinsicBounds(
					c.getBoolean("online") ? R.drawable.ic_online
							: R.drawable.ic_offline, 0, R.drawable.arrow, 0);

			return v;
		}

	}
}
