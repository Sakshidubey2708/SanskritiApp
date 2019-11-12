package com.example.awizom.sanskritidecoreapp.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.awizom.sanskritidecoreapp.R;
import com.example.awizom.sanskritidecoreapp.helper.OrderHelper;
import com.example.awizom.sanskritidecoreapp.modelnew.RoomModel;
import com.google.gson.Gson;

import java.util.List;

public class AlertMaterialAdapter extends RecyclerView.Adapter<AlertMaterialAdapter.OrderItemViewHolder> {

    private Context mCtx;
    private List<RoomModel> RoomModels;
    TextView calltext;
    private String result="";
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private CheckBox selectedRadioButton;

    public AlertMaterialAdapter(Context mCtx, List<RoomModel> RoomModels) {
        this.mCtx = mCtx;
        this.RoomModels = RoomModels;
    }

    @NonNull
    @Override
    public AlertMaterialAdapter.OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.material_adapter, null);
        return new AlertMaterialAdapter.OrderItemViewHolder(view, mCtx, RoomModels);
    }

    @Override
    public void onBindViewHolder(@NonNull final AlertMaterialAdapter.OrderItemViewHolder holder, final int position) {
        RoomModel RoomModel = RoomModels.get(position);
        final int pos = position;
        try {
            holder.checkBox.setText(RoomModel.getSubMaterialType());
            holder.material_type.setText(RoomModel.getSubMaterialType());
            holder.materil_id.setText(String.valueOf(RoomModel.getSubMaterialID()));
            holder.deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.app.AlertDialog.Builder alertbox = new android.app.AlertDialog.Builder(mCtx);
                    alertbox.setIcon(R.drawable.ic_warning_black_24dp);
                    alertbox.setTitle("Do You Want To Delete ?");
                    alertbox.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            deteleRoomApi(holder.materil_id.getText().toString());
                        }
                    });

                    alertbox.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            // Nothing will be happened when clicked on no button
                            // of Dialog
                        }
                    });
                    alertbox.show();
                }
            });

         //   holder.radioButton.setChecked(RoomModels.get(position).isSelected());
//            holder.radioButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    for(RoomModel model: RoomModels)
//                        model.setSelected(false);
//
//                    RoomModels.get(position).setSelected(true);
//
//                    // If current view (RadioButton) differs from previous selected radio button, then uncheck selectedRadioButton
//                    if(null != selectedRadioButton && !v.equals(selectedRadioButton))
//                        selectedRadioButton.setChecked(false);
//
//                    // Replace the previous selected radio button with the current (clicked) one, and "check" it
//                    selectedRadioButton = (RadioButton) v;
//                    selectedRadioButton.setChecked(true);
//
////                    Toast.makeText(
////                            v.getContext(),
////                            "Clicked on Checkbox: " + holder.radioButton.getText() + " is "
////                                    + selectedRadioButton.isChecked(), Toast.LENGTH_LONG).show();
//
//                }
//            });
            holder.checkBox.setTag(RoomModels.get(position));


            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    RoomModel pricingView1 = (RoomModel) cb.getTag();

                    pricingView1.setSelected(cb.isChecked());
                    RoomModels.get(pos).setSelected(cb.isChecked());

                    Toast.makeText(
                            v.getContext(),
                            "Clicked on Checkbox: " + cb.getText() + " is "
                                    + cb.isChecked(), Toast.LENGTH_LONG).show();
                }
            });


            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    v.startAnimation(buttonClick);
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                    LayoutInflater inflater = LayoutInflater.from(mCtx);
                    final View dialogView = inflater.inflate(R.layout.add_material, null);
                    alertbox.setView(dialogView);
                    alertbox.setCancelable(false);
                    final EditText addtext;
                    final Button add,cancel;
                    addtext= dialogView.findViewById(R.id.addText);
                    add= dialogView.findViewById(R.id.addBtn);
                    cancel=dialogView.findViewById(R.id.backBtn);
                    alertbox.setTitle("Add Sub Material");
                    addtext.setText(holder.material_type.getText().toString().trim());
                    final AlertDialog b = alertbox.create();
                    b.show();


                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(buttonClick);
                            try {
                                result = new OrderHelper.PostSubMaterialMaster().execute(holder.materil_id.getText().toString().trim(),addtext.getText().toString()).get();
                                if (result.isEmpty()) {

                                } else {
                                    Gson gson = new Gson();
                                    Toast.makeText(mCtx, "Update Successful", Toast.LENGTH_SHORT).show();
                                    addtext.setText(" ");
                                    b.dismiss();

                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(buttonClick);
                            b.dismiss();


                        }
                    });
                }
            });
        } catch (Exception E) {
            E.printStackTrace();
        }
    }
    private void deteleRoomApi(String id) {


    }


    @Override
    public int getItemCount() {
        return RoomModels.size();
    }

    class OrderItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        private Context mCtx;
        private TextView material_type,materil_id;
        private ImageButton edit, deletebtn;

        private List<RoomModel> RoomModels;
        private RoomModel completeOrderModel;
        private long order_id = 0;
        private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
        private CheckBox checkBox;
        private RadioButton radioButton;


        public OrderItemViewHolder(View view, Context mCtx, List<RoomModel> RoomModels) {
            super(view);
            this.mCtx = mCtx;
            this.RoomModels = RoomModels;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            material_type = view.findViewById(R.id.material_Names);
            deletebtn=view.findViewById(R.id.deleteBtn);
            materil_id = view.findViewById(R.id.materialIDs);
            edit = view.findViewById(R.id.editBtn);
            checkBox = view.findViewById(R.id.radioSelectRepair);
        }

        @Override
        public void onClick(final View v) {
            v.startAnimation(buttonClick);

        }

        @Override
        public boolean onLongClick(View v) {

            return true;
        }
    }
    public List<RoomModel> getRoomlist() {
        return RoomModels;
    }
}

