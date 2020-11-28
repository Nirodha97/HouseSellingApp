package com.nirodha.houseapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MyItemTouchHelerCallBack extends ItemTouchHelper.Callback {

    CallBackItemTouch callBackItemTouch;
    public MyItemTouchHelerCallBack(CallBackItemTouch callBackItemTouch) {
        this.callBackItemTouch = callBackItemTouch;
    }


    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
       final int dragFlags =ItemTouchHelper.UP | ItemTouchHelper.DOWN;
       final int swipFlags =ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags,swipFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        callBackItemTouch.itemTouchOnMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
    callBackItemTouch.onSwiped(viewHolder,viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
       if(actionState == ItemTouchHelper.ACTION_STATE_DRAG)
       {
           super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
       }else
       {
//           final View forigroundview= ((Adapter.Holder)viewHolder).viewB;
//           getDefaultUIUtil().onDrawOver(c,recyclerView,forigroundview,dX,dY,actionState,isCurrentlyActive);
       }


    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if(actionState!=ItemTouchHelper.ACTION_STATE_DRAG)
        {
//            final View forigroundview= ((Adapter.Holder)viewHolder).viewF;
//            getDefaultUIUtil().onDrawOver(c,recyclerView,forigroundview,dX,dY,actionState,isCurrentlyActive);
        }

    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
//        final View forigroundview= ((Adapter.Holder)viewHolder).viewF;
//        forigroundview.setBackgroundColor(ContextCompat.getColor(((Adapter.Holder)viewHolder).viewF.getContext(),R.color.colorwhite));
//        getDefaultUIUtil().clearView(forigroundview);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);

        if(viewHolder!=null)
        {
//            final View foregroundView =((Adapter.Holder)viewHolder).viewF;
//            if(actionState==ItemTouchHelper.ACTION_STATE_DRAG){
//                foregroundView.setBackgroundColor(Color.LTGRAY);
//            }
//            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }
}
