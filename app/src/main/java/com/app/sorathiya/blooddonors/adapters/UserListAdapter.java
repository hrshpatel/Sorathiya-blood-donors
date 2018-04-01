package com.app.sorathiya.blooddonors.adapters;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sorathiya.blooddonors.R;
import com.app.sorathiya.blooddonors.model.UserModel;

import java.util.ArrayList;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {


    private ArrayList<UserModel> mUserModelArrayList;
    private Context mContext;

    public UserListAdapter(ArrayList<UserModel> mUserModelArrayList, Context mContext
            , TextView txtNoData, RecyclerView recyclerView) {
        this.mUserModelArrayList = mUserModelArrayList;
        this.mContext = mContext;
        if (mUserModelArrayList.size() == 0) {
            txtNoData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View userView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_userlist, parent, false);
        return new UserViewHolder(userView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, final int position) {

        if (position % 2 == 0) {
            holder.mLinearLayoutContainer.setBackgroundColor(ContextCompat.getColor(mContext,R.color.translucent_white));
        } else {
            holder.mLinearLayoutContainer.setBackgroundColor(ContextCompat.getColor(mContext,R.color.translucent_grey));
        }
        holder.mTextViewAddress.setText("" + mUserModelArrayList.get(position).getUserAddress());
        holder.mTextViewName.setText("" + mUserModelArrayList.get(position).getUserName());
        holder.mTextViewContact.setText("" + mUserModelArrayList.get(position).getUserPhone());
        holder.mTextViewEmail.setText("" + mUserModelArrayList.get(position).getUserEmail());

        holder.mImageViewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Do you want to call " + mUserModelArrayList.get(position).getUserName());

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.fromParts("tel", mUserModelArrayList.get(position).getUserPhone()
                                , null));
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(mContext, "Need to give permission from settings for calling"
                                    , Toast.LENGTH_LONG).show();
                            return;
                        }
                        mContext.startActivity(intent);
                    }
                });

                builder.show();

            }
        });

        holder.mImageViewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:"
                        + Uri.encode(mUserModelArrayList.get(position).getUserPhone())));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserModelArrayList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageViewMessage;
        private ImageView mImageViewCall;
        private LinearLayout mLinearLayoutContainer;
        private TextView mTextViewEmail;
        private TextView mTextViewName;
        private TextView mTextViewContact;
        private TextView mTextViewAddress;

        public UserViewHolder(View itemView) {
            super(itemView);

            mTextViewName = (TextView) itemView.findViewById(R.id.txtUserName);
            mTextViewContact = (TextView) itemView.findViewById(R.id.txtUserContact);
            mTextViewAddress = (TextView) itemView.findViewById(R.id.txtUserAddress);
            mTextViewEmail = (TextView) itemView.findViewById(R.id.txtUserEmail);
            mLinearLayoutContainer = (LinearLayout) itemView.findViewById(R.id.linear_layout_item_container);

            mImageViewCall = (ImageView) itemView.findViewById(R.id.item_container_img_call);
            mImageViewMessage = (ImageView) itemView.findViewById(R.id.item_container_img_msg);
        }
    }
}
