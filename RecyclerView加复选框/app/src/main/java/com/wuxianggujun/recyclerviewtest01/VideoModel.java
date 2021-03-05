package com.wuxianggujun.recyclerviewtest01;

public class VideoModel {
    private String mVideoName;
	private boolean isChecked;
	
	public VideoModel(){
		
	}

	public VideoModel(String mVideoName) {
		this.mVideoName = mVideoName;
	}

	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setVideoName(String mVideoName) {
		this.mVideoName = mVideoName;
	}

	public String getVideoName() {
		return mVideoName;
	}
	
    
    
}
