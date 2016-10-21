package com.estsoft.muvigram.ui.search;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.estsoft.muvigram.R;
import com.estsoft.muvigram.customview.IncreasVideoView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gangGongUi on 2016. 10. 16..
 */
public class SearchRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_VIDEO_HEADER = 0;
    public static final int TYPE_BOARD_HEDER = 1;
    public static final int TYPE_ITEM = 2;

    private List<SearchListItem> mSearchListItems;
    private SearchHeaderVideoItem mHeaderVideoItem;
    private SearchHeaderBoardItem mHeaderBoardItem;

    private SearchListItem mListItem;

    public SearchRecyclerAdapter(SearchHeaderVideoItem headerVideoItem, SearchHeaderBoardItem headerBoardItem, List<SearchListItem> searchListItems) {
        mHeaderVideoItem = headerVideoItem;
        mHeaderBoardItem = headerBoardItem;
        mSearchListItems = searchListItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_VIDEO_HEADER) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.search_fragment_haeder_video, parent, false);
            VideoHeader videoHeader = new VideoHeader(view);
            return videoHeader;
        } else if(viewType == TYPE_BOARD_HEDER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_fragment_header_board, parent, false);
            return new BoardHeader(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_fragment_item, parent, false);
            return new SearchListItemHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        if (holder instanceof SearchListItemHolder) {
            SearchListItemHolder searchListItemHolder = (SearchListItemHolder) holder;
            SearchListItem item = mSearchListItems.get(position-2);
            searchListItemHolder.tag.setText(item.getTitle());

        } else if(holder instanceof VideoHeader) {
            VideoHeader videoHeader = (VideoHeader) holder;
            videoHeader.videoView.setVideoURI(mHeaderVideoItem.getVideoFile());
            videoHeader.videoView.setOnPreparedListener(mp -> videoPlay(videoHeader.videoView, mp));
            videoHeader.videoView.setOnCompletionListener(mp -> videoPlay(videoHeader.videoView, mp));

        } else if(holder instanceof BoardHeader) {
            BoardHeader boardHeader = (BoardHeader) holder;
            boardHeader.title.setText(mHeaderBoardItem.getTitle());

        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionVideoHeader(position)) {
            return TYPE_VIDEO_HEADER;
        } else if(isPositionBoardHeader(position)) {
            return TYPE_BOARD_HEDER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionVideoHeader(int position)
    {
        return position == mSearchListItems.get(position).getId();
    }

    private boolean isPositionBoardHeader(int position)
    {
        return position == 1;
    }

    private void videoPlay(final IncreasVideoView mVideoView ,final MediaPlayer mediaPlayer) {
        if(mVideoView != null) {
            mediaPlayer.setVolume(0, 0);
            mVideoView.seekTo(0);
            mVideoView.start();
        }
    }

    @Override
    public int getItemCount() {
        return mSearchListItems.size() + 2;
    }

    class VideoHeader extends RecyclerView.ViewHolder {

        @BindView(R.id.search_fragment_increasvideoview) IncreasVideoView videoView;
        public VideoHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.setIsRecyclable(false);

        }
    }

    class BoardHeader extends RecyclerView.ViewHolder {

        @BindView(R.id.popular_video_tv) TextView title;
        public BoardHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class SearchListItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_tag) TextView tag;
        public SearchListItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
