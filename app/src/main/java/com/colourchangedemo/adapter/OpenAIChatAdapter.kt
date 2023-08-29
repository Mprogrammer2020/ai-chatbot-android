package com.colourchangedemo.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.colourchangedemo.databinding.RowOpenAiChatBinding
import com.colourchangedemo.model.CommonChatModel
import com.colourchangedemo.util.AppConstants.IMAGE_MEDIA_TYPE
import com.colourchangedemo.util.AppConstants.TEXT_MEDIA_TYPE
import com.colourchangedemo.util.Utility.fullImageShow
import com.colourchangedemo.util.gone
import com.colourchangedemo.util.visible


class OpenAIChatAdapter(val mContext: Context, var list: ArrayList<CommonChatModel>) :
    RecyclerView.Adapter<OpenAIChatAdapter.ViewHolder>() {


    class ViewHolder(var binding: RowOpenAiChatBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowOpenAiChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    private var lastPosition = -1

    private fun setAnimationForMe(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation: Animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    private fun setAnimationForOther(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation: Animation = AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val model = list[position]

        if (model.mediaType == TEXT_MEDIA_TYPE) {

            if (model.isSendMe) {
                holder.binding.clMy.visible()
                holder.binding.tvMyMessage.visible()
                holder.binding.tvMyMessage.text = model.message

                holder.binding.clOther.gone()
                holder.binding.tvOtherMessage.gone()
                holder.binding.rlImage.gone()


            } else {

                holder.binding.clOther.visible()
                holder.binding.tvOtherMessage.visible()
                holder.binding.tvOtherMessage.text = model.message
                holder.binding.clMy.gone()
                holder.binding.tvMyMessage.gone()
                holder.binding.rlImage.gone()

            }

        } else if (model.mediaType == IMAGE_MEDIA_TYPE) {

            holder.binding.clMy.gone()
            holder.binding.tvMyMessage.gone()
            holder.binding.rlImage.gone()
            holder.binding.tvOtherMessage.gone()

            Glide.with(mContext).load(model.message).into(holder.binding.ivItem)
            holder.binding.clOther.visible()
            holder.binding.rlImage.visible()

            holder.binding.ivItem.setOnClickListener {
                fullImageShow(mContext,model.message)
            }
        }

//        holder.binding.tvOtherMessage.setOnClickListener {
//            val clipboard: ClipboardManager =
//                mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//            val clip: ClipData = ClipData.newPlainText("label", holder.binding.tvOtherMessage.text.toString().trim())
//            clipboard.setPrimaryClip(clip)
//        }


        setAnimationForOther(holder.itemView, position)


    }
}