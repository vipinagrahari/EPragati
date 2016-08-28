package io.github.vipinagrahari.epragati.ui.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.github.vipinagrahari.epragati.R;

/**
 * Created by vivek on 21/8/16.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoView> {
    final String videoImageBaseUrl = "http://img.youtube.com/vi/";
    final String videoIndex = "/0.jpg";

    List<Video> videos;
    Context context;


    public VideosAdapter(List<Video> videos) {
        this.videos = videos;
    }



    @Override
    public VideoView onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new VideoView(LayoutInflater.from(context).inflate(R.layout.element_video, parent, false));
    }

    @Override
    public void onBindViewHolder(VideoView holder, int position) {
        Video video = videos.get(position);
        Picasso.with(context).
                load(videoImageBaseUrl + video.getVideoId() + videoIndex)
                .error(context.getResources().getDrawable(R.drawable.video_placeholder))
                .into(holder.videoImage);
        holder.videoTitle.setText(video.getVideoTitle());
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class VideoView extends RecyclerView.ViewHolder implements AdapterView.OnClickListener {

        ImageView videoImage;
        TextView videoTitle;

        public VideoView(View itemView) {
            super(itemView);
            videoImage = (ImageView) itemView.findViewById(R.id.img_video);
            videoTitle = (TextView) itemView.findViewById(R.id.tv_video_title);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videos.get(getAdapterPosition()).videoId));
                context.startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videos.get(getAdapterPosition()).videoId));
                context.startActivity(intent);
            }
        }
    }


    public class Video {
        @SerializedName("video_id")
        @Expose
        String videoId;
        @SerializedName("title")
        @Expose
        private String videoTitle;

        public Video(String videoTitle, String videoId) {
            this.videoTitle = videoTitle;
            this.videoId = videoId;
        }


        public String getVideoId() {
            return videoId;
        }

        public String getVideoTitle() {
            return videoTitle;
        }
    }
}
